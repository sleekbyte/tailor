package com.sleekbyte.tailor.listeners.whitespace;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.WhitespaceVerifier;

/**
 * Parse tree listener for illegal whitespace.
 */
public final class ParenthesisWhitespaceListener extends SwiftBaseListener {
    private WhitespaceVerifier verifier;

    public ParenthesisWhitespaceListener(Printer printer) {
        this.verifier = new WhitespaceVerifier(printer, Rules.PARENTHESIS_WHITESPACE);
    }

    @Override
    public void enterGenericParameterClause(SwiftParser.GenericParameterClauseContext ctx) {
        verifier.verifyParenthesisWhitespace(ctx);
    }

    @Override
    public void enterParameterClause(SwiftParser.ParameterClauseContext ctx) {
        verifier.verifyParenthesisWhitespace(ctx);
    }

    @Override
    public void enterTupleType(SwiftParser.TupleTypeContext ctx) {
        verifier.verifyParenthesisWhitespace(ctx);
    }

    @Override
    public void enterParenthesizedExpression(SwiftParser.ParenthesizedExpressionContext ctx) {
        verifier.verifyParenthesisWhitespace(ctx);
    }
}
