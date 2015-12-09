package com.sleekbyte.tailor.listeners.whitespace;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.WhitespaceVerifier;

/**
 * Parse tree listener to flag illegal whitespace in angle brackets.
 */
public final class AngleBracketWhitespaceListener extends SwiftBaseListener {
    private WhitespaceVerifier verifier;

    public AngleBracketWhitespaceListener(Printer printer) {
        this.verifier = new WhitespaceVerifier(printer, Rules.ANGLE_BRACKET_WHITESPACE);
    }

    @Override
    public void enterGenericParameterClause(SwiftParser.GenericParameterClauseContext ctx) {
        verifier.verifyBracketContentWhitespace(ctx, Messages.CHEVRONS);
        verifier.verifyBracketSurroundingWhitespace(ctx, Messages.CHEVRONS);
    }

}
