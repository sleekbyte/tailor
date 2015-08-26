package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.List;
import java.util.Set;

public class ConstructListener extends SwiftBaseListener {

    private static final String LET = "let";
    private static final String VAR = "var";
    public Set<Rules> enabledRules;
    public Printer printer;
    public MaxLengths maxLengths;

    public ConstructListener(Printer printer, Set<Rules> enabledRules, MaxLengths maxLengths) {
        this.printer = printer;
        this.enabledRules = enabledRules;
        this.maxLengths = maxLengths;
    }

    @Override
    public void enterConstantDeclaration(SwiftParser.ConstantDeclarationContext ctx) {
        evaluatePatternInitializerList(ctx.patternInitializerList(), new ConstantDecListener(this));
    }

    @Override
    public void enterVariableDeclaration(SwiftParser.VariableDeclarationContext ctx) {
        if (ctx.patternInitializerList() == null) {
            return;
        }
        evaluatePatternInitializerList(ctx.patternInitializerList(), new VariableDecListener());
    }

    @Override
    public void enterValueBindingPattern(SwiftParser.ValueBindingPatternContext ctx) {
        ParseTreeWalker walker = new ParseTreeWalker();
        if (ctx.getStart().getText().equals(LET)) {
            evaluatePattern(ctx.pattern(), walker, new ConstantDecListener(this));
        } else if (ctx.getStart().getText().equals(VAR)) {
            evaluatePattern(ctx.pattern(), walker, new VariableDecListener());
        }
    }

    @Override
    public void enterParameter(SwiftParser.ParameterContext ctx) {
        SwiftBaseListener listener = null;
        for (ParseTree child : ctx.children) {
            if (child.getText().equals(VAR)) {
                listener = new VariableDecListener();
                break;
            }
        }
        if (listener == null) {
            listener = new ConstantDecListener(this);
        }
        ParseTreeWalker walker = new ParseTreeWalker();
        if (ctx.externalParameterName() != null) {
            walkListener(walker, ctx.externalParameterName(), listener);
        }
        walkListener(walker, ctx.localParameterName(), listener);
    }

    @Override
    public void enterOptionalBindingCondition(SwiftParser.OptionalBindingConditionContext ctx) {
        /*  Optional Binding is tricky. Consider the following Swift code:
            if let const1 = foo?.bar, const2 = foo?.bar, var var1 = foo?.bar, var2 = foo?.bar {
                ...
            }
            const1 and const2 are both constants even though only const1 has 'let' before it.
            var1 and var2 are both variables as there is a 'var' before var1 and no 'let' before var2.

            The code below iterates through each declared variable and applies rules based on the last seen binding,
            'let' or 'var'.
         */
        String currentBinding = letOrVar(ctx.optionalBindingHead());
        if (currentBinding.equals(LET)) {
            evaluateOptionalBindingHead(ctx.optionalBindingHead(), new ConstantDecListener(this));
        } else if (currentBinding.equals(VAR)) {
            evaluateOptionalBindingHead(ctx.optionalBindingHead(), new VariableDecListener());
        }
        SwiftParser.OptionalBindingContinuationListContext continuationList = ctx.optionalBindingContinuationList();
        if (continuationList != null) {
            for (SwiftParser.OptionalBindingContinuationContext continuation :
                continuationList.optionalBindingContinuation()) {
                if (continuation.optionalBindingHead() != null) {
                    currentBinding = letOrVar(continuation.optionalBindingHead());
                }
                if (currentBinding.equals(LET)) {
                    evaluateOptionalBindingContinuation(continuation, new ConstantDecListener(this));
                } else if (currentBinding.equals(VAR)) {
                    evaluateOptionalBindingContinuation(continuation, new VariableDecListener());
                }
            }
        }
    }

    public boolean ruleEnabled(Rules rule) {
        return enabledRules.contains(rule);
    }

    private void evaluateOptionalBindingHead(SwiftParser.OptionalBindingHeadContext ctx, SwiftBaseListener listener) {
        ParseTreeWalker walker = new ParseTreeWalker();
        evaluatePattern(ctx.pattern(), walker, listener);
    }

    private void evaluateOptionalBindingContinuation(SwiftParser.OptionalBindingContinuationContext ctx,
                                             SwiftBaseListener listener) {
        if (ctx.optionalBindingHead() != null) {
            evaluateOptionalBindingHead(ctx.optionalBindingHead(), listener);
        } else {
            ParseTreeWalker walker = new ParseTreeWalker();
            evaluatePattern(ctx.pattern(), walker, listener);
        }
    }

    private String letOrVar(SwiftParser.OptionalBindingHeadContext ctx) {
        return ctx.getChild(0).getText();
    }

    private void walkListener(ParseTreeWalker walker, ParserRuleContext tree, SwiftBaseListener listener) {
        walker.walk(listener, tree);
    }

    private void evaluatePatternInitializerList(SwiftParser.PatternInitializerListContext ctx,
                                                SwiftBaseListener listener) {
        ParseTreeWalker walker = new ParseTreeWalker();
        for (SwiftParser.PatternInitializerContext context : ctx.patternInitializer()) {
            SwiftParser.PatternContext pattern = context.pattern();
            evaluatePattern(pattern, walker, listener);
        }
    }

    private void evaluatePattern(SwiftParser.PatternContext pattern, ParseTreeWalker walker,
                                 SwiftBaseListener listener) {
        if (pattern.identifierPattern() != null) {
            walkListener(walker, pattern.identifierPattern(), listener);

        } else if (pattern.tuplePattern() != null && pattern.tuplePattern().tuplePatternElementList() != null) {
            evaluateTuplePattern(pattern.tuplePattern(), walker, listener);

        } else if (pattern.enumCasePattern() != null && pattern.enumCasePattern().tuplePattern() != null) {
            evaluateTuplePattern(pattern.enumCasePattern().tuplePattern(), walker, listener);

        } else if (pattern.pattern() != null) {
            evaluatePattern(pattern.pattern(), walker, listener);

        } else if (pattern.expressionPattern() != null) {
            walkListener(walker, pattern.expressionPattern().expression().prefixExpression(), listener);
        }
    }

    private void evaluateTuplePattern(SwiftParser.TuplePatternContext tuplePatternContext, ParseTreeWalker walker,
                                      SwiftBaseListener listener) {
        List<SwiftParser.TuplePatternElementContext> tuplePatternElementContexts =
            tuplePatternContext.tuplePatternElementList().tuplePatternElement();

        for (SwiftParser.TuplePatternElementContext tuplePatternElement : tuplePatternElementContexts) {
            evaluatePattern(tuplePatternElement.pattern(), walker, listener);
        }
    }

}
