package com.sleekbyte.tailor.listeners.whitespace;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import com.sleekbyte.tailor.utils.ParseTreeUtil;
import org.antlr.v4.runtime.ParserRuleContext;
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

    @Override
    public void enterTypeInheritanceClause(SwiftParser.TypeInheritanceClauseContext ctx) {
        checkWhitespaceAroundCommas(ctx);
    }

    @Override
    public void enterGenericParameterList(SwiftParser.GenericParameterListContext ctx) {
        checkWhitespaceAroundCommaSeparatedList(ctx);
    }

    @Override
    public void enterRequirementList(SwiftParser.RequirementListContext ctx) {
        checkWhitespaceAroundCommaSeparatedList(ctx);
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

    private void checkWhitespaceAroundCommas(SwiftParser.TypeInheritanceClauseContext ctx) {
        if (ctx.classRequirement() != null && ctx.typeInheritanceList() != null) {
            Token left = ParseTreeUtil.getStopTokenForNode(ctx.classRequirement());
            Token right = ParseTreeUtil.getStartTokenForNode(ctx.typeInheritanceList());
            Token comma = ((TerminalNodeImpl) ctx.getChild(2)).getSymbol();

            verifyCommaLeftAssociation(left, right, comma);
        }

        if (ctx.typeInheritanceList() != null) {
            checkWhitespaceAroundCommaSeparatedList(ctx.typeInheritanceList());
        }
    }

    private void checkWhitespaceAroundCommaSeparatedList(ParserRuleContext ctx) {
        for (int i = 0; i < ctx.children.size() - 2; i += 2) {
            Token left = ParseTreeUtil.getStopTokenForNode(ctx.getChild(i));
            Token right = ParseTreeUtil.getStartTokenForNode(ctx.getChild(i + 2));
            Token comma = ((TerminalNodeImpl) ctx.getChild(i + 1)).getSymbol();

            verifyCommaLeftAssociation(left, right, comma);
        }
    }

    private void verifyCommaLeftAssociation(Token left, Token right, Token comma) {
        verifyPunctuationLeftAssociation(left, right, comma, Messages.COMMA);
    }

    private void verifyPunctuationLeftAssociation(Token left, Token right, Token punc, String puncStr) {
        Location puncLocation = ListenerUtil.getTokenLocation(punc);

        if (checkIfInline(left, punc) || checkLeftSpaces(left, punc, 0)) {
            printer.error(Rules.WHITESPACE, puncStr + Messages.AT_COLUMN + puncLocation.column + " "
                + Messages.NO_SPACE_BEFORE, puncLocation);
        }

        if (checkRightSpaces(right, punc, 1)) {
            printer.error(Rules.WHITESPACE, puncStr + Messages.AT_COLUMN + puncLocation.column + " "
                + Messages.SPACE_AFTER, puncLocation);
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

    private boolean checkIfInline(Token one, Token two) {
        return one.getLine() != two.getLine();
    }
}
