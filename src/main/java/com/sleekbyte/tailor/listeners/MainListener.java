package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.MaxLengths;
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
