package com.sleekbyte.tailor.listeners.whitespace;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

/**
 * Parse tree listener for whitespace checks.
 */
public class WhitespaceListener extends SwiftBaseListener {

    private Printer printer;

    public WhitespaceListener(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void enterOperatorDeclaration(SwiftParser.OperatorDeclarationContext ctx) {
        checkWhitespaceAroundOperator(ctx);
    }

    private void checkWhitespaceAroundOperator(SwiftParser.OperatorDeclarationContext ctx) {
        for (int i = 0; i < ctx.getChild(0).getChildCount(); i++) {
            if (ctx.getChild(0).getChild(i) instanceof SwiftParser.OperatorContext) {
                SwiftParser.OperatorContext op = (SwiftParser.OperatorContext) ctx.getChild(0).getChild(i);
                Token before = ((TerminalNodeImpl) ctx.getChild(0).getChild(i - 1)).getSymbol();
                Token after = ((TerminalNodeImpl) ctx.getChild(0).getChild(i + 1)).getSymbol();

                if (checkLeftSpaces(before, op.getStart(), 1)) {
                    printer.error(Rules.WHITESPACE, Messages.OPERATOR + Messages.SPACE_BEFORE,
                        ListenerUtil.getContextStartLocation(op));
                }

                if (checkRightSpaces(after, op.getStop(), 1)) {
                    printer.error(Rules.WHITESPACE, Messages.OPERATOR + Messages.SPACE_AFTER,
                        ListenerUtil.getContextStartLocation(op));
                }
            }
        }
    }

    private boolean checkLeftSpaces(Token left, Token op, int numSpaces) {
        return op.getLine() == left.getLine()
            && op.getCharPositionInLine() - ListenerUtil.getLastCharPositionInLine(left) != numSpaces + 1;
    }

    private boolean checkRightSpaces(Token right, Token op, int numSpaces) {
        return right.getLine() == op.getLine()
            && right.getCharPositionInLine() - ListenerUtil.getLastCharPositionInLine(op) != numSpaces + 1;
    }
}
