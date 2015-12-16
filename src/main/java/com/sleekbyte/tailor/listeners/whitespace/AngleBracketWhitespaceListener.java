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
        if (leftSibling instanceof FunctionNameContext) {
            FunctionNameContext functionName = (FunctionNameContext)leftSibling;
            if (functionName.operator() != null) {
                // Enforce whitespace around operators defined with func
                verifier.verifyLeadingWhitespaceBeforeBracket(ctx, Messages.CHEVRONS,
                    Messages.OPERATOR_OVERLOADING_ONE_WHITESPACE_BEFORE, 1);
                return;
            }
        }
        verifier.verifyLeadingWhitespaceBeforeBracket(ctx, Messages.CHEVRONS, Messages.NO_WHITESPACE_BEFORE, 0);
    }

}
