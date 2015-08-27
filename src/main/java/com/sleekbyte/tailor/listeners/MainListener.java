package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import org.antlr.v4.runtime.BufferedTokenStream;

/**
 * Parse tree listener for verifying Swift constructs.
 */
public class MainListener extends SwiftBaseListener {

    private ParseTreeVerifier verifier;

    /**
     * Creates a MainListener object and a new parse tree verifier.
     *
     * @param printer    {@link Printer} used for outputting messages to user
     * @param maxLengths {@link MaxLengths} stores numbers for max length restrictions
     */
    public MainListener(Printer printer, MaxLengths maxLengths, BufferedTokenStream tokenStream) {
        this.verifier = ParseTreeVerifier.INSTANCE;
        verifier.reset();
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
    public void enterConditionClause(SwiftParser.ConditionClauseContext ctx) {
        verifier.verifyRedundantExpressionParenthesis(Messages.CONDITIONAL_CLAUSE, ctx.expression());
    }

    @Override
    public void enterSwitchStatement(SwiftParser.SwitchStatementContext ctx) {
        verifier.verifyRedundantExpressionParenthesis(Messages.SWITCH_EXPRESSION, ctx.expression());
        verifier.verifySwitchStatementBraceStyle(ctx);
    }

    @Override
    public void enterForStatement(SwiftParser.ForStatementContext ctx) {
        verifier.verifyRedundantForLoopParenthesis(ctx);
        verifier.verifyForLoopBraceStyle(ctx);
    }

    @Override
    public void enterThrowStatement(SwiftParser.ThrowStatementContext ctx) {
        verifier.verifyRedundantExpressionParenthesis(Messages.THROW_STATEMENT, ctx.expression());
    }

    @Override
    public void enterCatchClause(SwiftParser.CatchClauseContext ctx) {
        verifier.verifyRedundantCatchParentheses(ctx.pattern());
    }

    @Override
    public void enterInitializer(SwiftParser.InitializerContext ctx) {
        verifier.verifyRedundantExpressionParenthesis(Messages.INITIALIZER_EXPRESSION, ctx.expression());
    }

    @Override
    public void enterArrayLiteralItem(SwiftParser.ArrayLiteralItemContext ctx) {
        verifier.verifyRedundantExpressionParenthesis(Messages.ARRAY_LITERAL, ctx.expression());
    }

    @Override
    public void enterDictionaryLiteralItem(SwiftParser.DictionaryLiteralItemContext ctx) {
        for (SwiftParser.ExpressionContext expressionContext : ctx.expression()) {
            verifier.verifyRedundantExpressionParenthesis(Messages.DICTIONARY_LITERAL, expressionContext);
        }
        verifier.checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterImportDeclaration(SwiftParser.ImportDeclarationContext ctx) {
        verifier.verifyMultipleImports(ctx);
    }

    @Override
    public void enterFunctionDeclaration(SwiftParser.FunctionDeclarationContext ctx) {
        verifier.verifyFunctionBraceStyle(ctx);
        verifier.verifyBlankLinesAroundFunction(ctx);
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
    public void enterOperatorDeclaration(SwiftParser.OperatorDeclarationContext ctx) {
        verifier.checkWhitespaceAroundOperator(ctx);
    }

    @Override
    public void enterTypeAnnotation(SwiftParser.TypeAnnotationContext ctx) {
        verifier.checkWhitespaceAroundColon(ctx);
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
    public void enterDictionaryType(SwiftParser.DictionaryTypeContext ctx) {
        verifier.checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterSwitchCase(SwiftParser.SwitchCaseContext ctx) {
        verifier.checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterTypeInheritanceClause(SwiftParser.TypeInheritanceClauseContext ctx) {
        verifier.checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterConditionalOperator(SwiftParser.ConditionalOperatorContext ctx) {
        verifier.checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterExpressionElement(SwiftParser.ExpressionElementContext ctx) {
        verifier.checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterGenericParameter(SwiftParser.GenericParameterContext ctx) {
        verifier.checkWhitespaceAroundColon(ctx);
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

    @Override
    public void enterFunctionResult(SwiftParser.FunctionResultContext ctx) {
        verifier.checkWhitespaceAroundArrow(ctx);
    }

    @Override
    public void enterSType(SwiftParser.STypeContext ctx) {
        verifier.checkWhitespaceAroundArrow(ctx);
    }

    @Override
    public void enterSubscriptResult(SwiftParser.SubscriptResultContext ctx) {
        verifier.checkWhitespaceAroundArrow(ctx);
    }
}
