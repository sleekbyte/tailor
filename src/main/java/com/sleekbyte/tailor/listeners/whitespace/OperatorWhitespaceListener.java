package com.sleekbyte.tailor.listeners.whitespace;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import com.sleekbyte.tailor.utils.ParseTreeUtil;
import com.sleekbyte.tailor.utils.WhitespaceVerifier;
import org.antlr.v4.runtime.Token;

/**
 * Parse tree listener for operator whitespace checks.
 */
public final class OperatorWhitespaceListener extends SwiftBaseListener {

    private Printer printer;
    private WhitespaceVerifier verifier;

    public OperatorWhitespaceListener(Printer printer) {
        this.printer = printer;
        this.verifier = new WhitespaceVerifier(printer, Rules.OPERATOR_WHITESPACE);
    }

    @Override
    public void enterOperatorDeclaration(SwiftParser.OperatorDeclarationContext ctx) {
        for (int i = 0; i < ctx.getChild(0).getChildCount(); i++) {
            if (ctx.getChild(0).getChild(i) instanceof SwiftParser.OperatorContext) {
                SwiftParser.OperatorContext op = (SwiftParser.OperatorContext) ctx.getChild(0).getChild(i);
                Token before = ParseTreeUtil.getStopTokenForNode(ctx.getChild(0).getChild(i - 1));

                if (verifier.checkLeftSpaces(before, op.getStart(), 1)) {
                    printer.error(Rules.OPERATOR_WHITESPACE, Messages.OPERATOR + Messages.SPACE_BEFORE,
                        ListenerUtil.getContextStartLocation(op));
                }

                // TODO: Verify one space after colon for infix operator declarations
            }
        }
    }
}
