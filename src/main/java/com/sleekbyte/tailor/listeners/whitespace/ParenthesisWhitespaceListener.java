package com.sleekbyte.tailor.listeners.whitespace;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.WhitespaceVerifier;

/**
 * Parse tree listener to flag illegal whitespace in parentheses.
 */
public final class ParenthesisWhitespaceListener extends SwiftBaseListener {
    private WhitespaceVerifier verifier;

    public ParenthesisWhitespaceListener(Printer printer) {
        this.verifier = new WhitespaceVerifier(printer, Rules.PARENTHESIS_WHITESPACE);
    }

    @Override
    public void enterParameterClause(SwiftParser.ParameterClauseContext ctx) {
        verifier.verifyBracketContentWhitespace(ctx, Messages.PARENTHESES);
    }

    @Override
    public void enterTupleType(SwiftParser.TupleTypeContext ctx) {
        verifier.verifyBracketContentWhitespace(ctx, Messages.PARENTHESES);
    }

    @Override
    public void enterParenthesizedExpression(SwiftParser.ParenthesizedExpressionContext ctx) {
        verifier.verifyBracketContentWhitespace(ctx, Messages.PARENTHESES);
    }

    @Override
    public void enterParameterClauses(SwiftParser.ParameterClausesContext ctx) {
        verifier.verifyBracketSurroundingWhitespace(ctx, Messages.PARENTHESES);
    }

    @Override
    public void enterInitializerDeclaration(SwiftParser.InitializerDeclarationContext ctx) {
        verifier.verifyBracketSurroundingWhitespace(ctx.parameterClause(), Messages.PARENTHESES);
    }

}
