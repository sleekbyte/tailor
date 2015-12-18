package com.sleekbyte.tailor.listeners.whitespace;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.antlr.SwiftParser.FunctionNameContext;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ParseTreeUtil;
import com.sleekbyte.tailor.utils.WhitespaceVerifier;
import org.antlr.v4.runtime.tree.ParseTree;

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

        ParseTree leftSibling = ParseTreeUtil.getLeftSibling(ctx);
        // Check for operator overloaded methods
        if (leftSibling instanceof FunctionNameContext && ((FunctionNameContext)leftSibling).operator() != null) {
            verifier.verifyLeadingWhitespaceBeforeBracket(ctx, Messages.OPERATOR_OVERLOADING_ONE_SPACE, 1);
        } else {
            verifier.verifyLeadingWhitespaceBeforeBracket(ctx, Messages.CHEVRONS, Messages.NO_WHITESPACE_BEFORE, 0);
        }
    }

}
