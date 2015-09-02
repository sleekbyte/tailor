package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * Parse tree listener for verifying Swift constructs.
 */
public class MainListener extends SwiftBaseListener {

    private static final String LET = "let";
    private static final String VAR = "var";
    private ParseTreeVerifier verifier;

    /**
     * Creates a MainListener object and a new parse tree verifier.
     *
     * @param printer    {@link Printer} used for outputting messages to user
     * @param maxLengths {@link MaxLengths} stores numbers for max length restrictions
     */
    public MainListener(Printer printer, MaxLengths maxLengths, BufferedTokenStream tokenStream) {
        this.verifier = ParseTreeVerifier.INSTANCE;
        verifier.printer = printer;
        verifier.maxLengths = maxLengths;
        verifier.tokenStream = tokenStream;
    }

    @Override
    public void enterClassBody(SwiftParser.ClassBodyContext ctx) {
        verifier.verifyClassBraceStyle(ctx);
    }

    @Override
    public void enterClosureExpression(SwiftParser.ClosureExpressionContext ctx) {
        verifier.verifyClosureExpressionBraceStyle(ctx);
    }

    @Override
    public void enterStructBody(SwiftParser.StructBodyContext ctx) {
        verifier.verifyStructBraceStyle(ctx);
    }

    @Override
    public void enterFunctionName(SwiftParser.FunctionNameContext ctx) {
        verifier.verifyLowerCamelCase(Messages.FUNCTION + Messages.NAMES, ctx);
    }

    @Override
    public void enterVariableName(SwiftParser.VariableNameContext ctx) {
        verifier.verifyLowerCamelCase(Messages.VARIABLE + Messages.NAMES, ctx);
    }

    @Override
    public void enterConstantDeclaration(SwiftParser.ConstantDeclarationContext ctx) {
        verifier.evaluatePatternInitializerList(ctx.patternInitializerList(), new ConstantDecListener());
    }

    @Override
    public void enterVariableDeclaration(SwiftParser.VariableDeclarationContext ctx) {
        if (ctx.patternInitializerList() == null) {
            return;
        }
        verifier.evaluatePatternInitializerList(ctx.patternInitializerList(), new VariableDecListener());
    }

    @Override
    public void enterValueBindingPattern(SwiftParser.ValueBindingPatternContext ctx) {
        ParseTreeWalker walker = new ParseTreeWalker();
        if (ctx.getStart().getText().equals(LET)) {
            verifier.evaluatePattern(ctx.pattern(), walker, new ConstantDecListener());
        } else if (ctx.getStart().getText().equals(VAR)) {
            verifier.evaluatePattern(ctx.pattern(), walker, new VariableDecListener());
        }
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
        String currentBinding = verifier.letOrVar(ctx.optionalBindingHead());
        if (currentBinding.equals(LET)) {
            verifier.evaluateOptionalBindingHead(ctx.optionalBindingHead(), new ConstantDecListener());
        } else if (currentBinding.equals(VAR)) {
            verifier.evaluateOptionalBindingHead(ctx.optionalBindingHead(), new VariableDecListener());
        }
        SwiftParser.OptionalBindingContinuationListContext continuationList = ctx.optionalBindingContinuationList();
        if (continuationList != null) {
            for (SwiftParser.OptionalBindingContinuationContext continuation :
                    continuationList.optionalBindingContinuation()) {
                if (continuation.optionalBindingHead() != null) {
                    currentBinding = verifier.letOrVar(continuation.optionalBindingHead());
                }
                if (currentBinding.equals(LET)) {
                    verifier.evaluateOptionalBindingContinuation(continuation, new ConstantDecListener());
                } else if (currentBinding.equals(VAR)) {
                    verifier.evaluateOptionalBindingContinuation(continuation, new VariableDecListener());
                }
            }
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
            listener = new ConstantDecListener();
        }
        ParseTreeWalker walker = new ParseTreeWalker();
        if (ctx.externalParameterName() != null) {
            verifier.walkListener(walker, ctx.externalParameterName(), listener);
        }
        verifier.walkListener(walker, ctx.localParameterName(), listener);
    }

    @Override
    public void enterSwitchStatement(SwiftParser.SwitchStatementContext ctx) {
        verifier.verifySwitchStatementBraceStyle(ctx);
    }

    @Override
    public void enterForStatement(SwiftParser.ForStatementContext ctx) {
        verifier.verifyForLoopBraceStyle(ctx);
    }

    @Override
    public void enterFunctionDeclaration(SwiftParser.FunctionDeclarationContext ctx) {
        verifier.verifyFunctionBraceStyle(ctx);
    }

    @Override
    public void enterElseClause(SwiftParser.ElseClauseContext ctx) {
        verifier.verifyElseClauseBraceStyle(ctx);
    }

    @Override
    public void enterIfStatement(SwiftParser.IfStatementContext ctx) {
        verifier.verifyIfStatementBraceStyle(ctx);
    }

    @Override
    public void enterWhileStatement(SwiftParser.WhileStatementContext ctx) {
        verifier.verifyWhileLoopBraceStyle(ctx);
    }

    @Override
    public void enterRepeatWhileStatement(SwiftParser.RepeatWhileStatementContext ctx) {
        verifier.verifyRepeatWhileLoopBraceStyle(ctx);
    }

    @Override
    public void enterInitializerDeclaration(SwiftParser.InitializerDeclarationContext ctx) {
        verifier.verifyInitializerBraceStyle(ctx);
    }

    @Override
    public void enterForInStatement(SwiftParser.ForInStatementContext ctx) {
        verifier.verifyForInStatementBraceStyle(ctx);
    }

    @Override
    public void enterProtocolBody(SwiftParser.ProtocolBodyContext ctx) {
        verifier.verifyProtocolBraceStyle(ctx);
    }

    @Override
    public void enterUnionStyleEnum(SwiftParser.UnionStyleEnumContext ctx) {
        verifier.verifyEnumBraceStyle(ctx);
    }

    @Override
    public void enterRawValueStyleEnum(SwiftParser.RawValueStyleEnumContext ctx) {
        verifier.verifyEnumBraceStyle(ctx);
    }

    @Override
    public void enterExtensionBody(SwiftParser.ExtensionBodyContext ctx) {
        verifier.verifyExtensionBraceStyle(ctx);
    }

    @Override
    public void enterGetterClause(SwiftParser.GetterClauseContext ctx) {
        verifier.verifyGetterBraceStyle(ctx);
    }

    @Override
    public void enterSetterClause(SwiftParser.SetterClauseContext ctx) {
        verifier.verifySetterBraceStyle(ctx);
    }

    @Override
    public void enterTypeCastingOperator(SwiftParser.TypeCastingOperatorContext ctx) {
        verifier.verifyForceTypeCasting(ctx);
    }

}
