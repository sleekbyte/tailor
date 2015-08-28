package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.List;

/**
 * Parse tree listener for different constructs.
 */
public class DeclarationListener extends SwiftBaseListener {

    /**
     * Declaration types handled by this listener.
     */
    private enum Declaration { VARIABLE_DEC, CONSTANT_DEC }

    private static final String LET = "let";
    private static final String VAR = "var";
    private List<SwiftBaseListener> enabledListeners;

    /**
     * Creates a DeclarationListener object.
     *
     * @param listeners list of all rule listeners that are enabled
     */
    public DeclarationListener(List<SwiftBaseListener> listeners) {
        this.enabledListeners = listeners;
    }

    @Override
    public void enterConstantDeclaration(SwiftParser.ConstantDeclarationContext ctx) {
        evaluatePatternInitializerList(ctx.patternInitializerList(), Declaration.CONSTANT_DEC);
    }

    @Override
    public void enterVariableDeclaration(SwiftParser.VariableDeclarationContext ctx) {
        if (ctx.patternInitializerList() == null) {
            return;
        }
        evaluatePatternInitializerList(ctx.patternInitializerList(), Declaration.VARIABLE_DEC);
    }

    @Override
    public void enterValueBindingPattern(SwiftParser.ValueBindingPatternContext ctx) {
        ParseTreeWalker walker = new ParseTreeWalker();
        if (ctx.getStart().getText().equals(LET)) {
            evaluatePattern(ctx.pattern(), walker, Declaration.CONSTANT_DEC);
        } else if (ctx.getStart().getText().equals(VAR)) {
            evaluatePattern(ctx.pattern(), walker, Declaration.VARIABLE_DEC);
        }
    }

    @Override
    public void enterParameter(SwiftParser.ParameterContext ctx) {
        Declaration listenerType = null;
        for (ParseTree child : ctx.children) {
            if (child.getText().equals(VAR)) {
                listenerType = Declaration.VARIABLE_DEC;
                break;
            }
        }
        if (listenerType == null) {
            listenerType = Declaration.CONSTANT_DEC;
        }
        ParseTreeWalker walker = new ParseTreeWalker();
        if (ctx.externalParameterName() != null) {
            walkListener(walker, ctx.externalParameterName(), listenerType);
        }
        walkListener(walker, ctx.localParameterName(), listenerType);
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
            evaluateOptionalBindingHead(ctx.optionalBindingHead(), Declaration.CONSTANT_DEC);
        } else if (currentBinding.equals(VAR)) {
            evaluateOptionalBindingHead(ctx.optionalBindingHead(), Declaration.VARIABLE_DEC);
        }
        SwiftParser.OptionalBindingContinuationListContext continuationList = ctx.optionalBindingContinuationList();
        if (continuationList != null) {
            for (SwiftParser.OptionalBindingContinuationContext continuation :
                continuationList.optionalBindingContinuation()) {
                if (continuation.optionalBindingHead() != null) {
                    currentBinding = letOrVar(continuation.optionalBindingHead());
                }
                if (currentBinding.equals(LET)) {
                    evaluateOptionalBindingContinuation(continuation, Declaration.CONSTANT_DEC);
                } else if (currentBinding.equals(VAR)) {
                    evaluateOptionalBindingContinuation(continuation, Declaration.VARIABLE_DEC);
                }
            }
        }
    }

    private void evaluateOptionalBindingHead(SwiftParser.OptionalBindingHeadContext ctx, Declaration listenerType) {
        ParseTreeWalker walker = new ParseTreeWalker();
        evaluatePattern(ctx.pattern(), walker, listenerType);
    }

    private void evaluateOptionalBindingContinuation(SwiftParser.OptionalBindingContinuationContext ctx,
                                                     Declaration listenerType) {
        if (ctx.optionalBindingHead() != null) {
            evaluateOptionalBindingHead(ctx.optionalBindingHead(), listenerType);
        } else {
            ParseTreeWalker walker = new ParseTreeWalker();
            evaluatePattern(ctx.pattern(), walker, listenerType);
        }
    }

    private String letOrVar(SwiftParser.OptionalBindingHeadContext ctx) {
        return ctx.getChild(0).getText();
    }

    private void walkListener(ParseTreeWalker walker, ParserRuleContext tree, Declaration listenerType) {
        for (SwiftBaseListener listener : enabledListeners) {
            switch (listenerType) {
                case CONSTANT_DEC:
                    if (listener instanceof MaxLengthListener) {
                        ((MaxLengthListener) listener).setTraversedTreeForConstantDeclaration(true);
                        walker.walk(listener, tree);
                    } else if (listener instanceof ConstantNamingListener || listener instanceof KPrefixListener) {
                        walker.walk(listener, tree);
                    }
                    break;
                case VARIABLE_DEC:
                    if (listener instanceof LowerCamelCaseListener) {
                        ((LowerCamelCaseListener) listener).setTraversedTreeForVarDeclaration(true);
                        walker.walk(listener, tree);
                    } else if (listener instanceof MaxLengthListener) {
                        ((MaxLengthListener) listener).setTraversedTreeForVarDeclaration(true);
                        walker.walk(listener, tree);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void evaluatePatternInitializerList(SwiftParser.PatternInitializerListContext ctx,
                                                Declaration listenerType) {
        ParseTreeWalker walker = new ParseTreeWalker();
        for (SwiftParser.PatternInitializerContext context : ctx.patternInitializer()) {
            SwiftParser.PatternContext pattern = context.pattern();
            evaluatePattern(pattern, walker, listenerType);
        }
    }

    private void evaluatePattern(SwiftParser.PatternContext pattern, ParseTreeWalker walker,
                                 Declaration listenerType) {
        if (pattern.identifierPattern() != null) {
            walkListener(walker, pattern.identifierPattern(), listenerType);

        } else if (pattern.tuplePattern() != null && pattern.tuplePattern().tuplePatternElementList() != null) {
            evaluateTuplePattern(pattern.tuplePattern(), walker, listenerType);

        } else if (pattern.enumCasePattern() != null && pattern.enumCasePattern().tuplePattern() != null) {
            evaluateTuplePattern(pattern.enumCasePattern().tuplePattern(), walker, listenerType);

        } else if (pattern.pattern() != null) {
            evaluatePattern(pattern.pattern(), walker, listenerType);

        } else if (pattern.expressionPattern() != null) {
            walkListener(walker, pattern.expressionPattern().expression().prefixExpression(), listenerType);
        }
    }

    private void evaluateTuplePattern(SwiftParser.TuplePatternContext tuplePatternContext, ParseTreeWalker walker,
                                      Declaration listenerType) {
        List<SwiftParser.TuplePatternElementContext> tuplePatternElementContexts =
            tuplePatternContext.tuplePatternElementList().tuplePatternElement();

        for (SwiftParser.TuplePatternElementContext tuplePatternElement : tuplePatternElementContexts) {
            evaluatePattern(tuplePatternElement.pattern(), walker, listenerType);
        }
    }
}
