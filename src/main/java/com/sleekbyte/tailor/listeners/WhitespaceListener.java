package com.sleekbyte.tailor.listeners;

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
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.Optional;

/**
 * Parse tree listener for whitespace checks.
 */
public class WhitespaceListener extends SwiftBaseListener {

    private Printer printer;

    public WhitespaceListener(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void enterDictionaryLiteralItem(SwiftParser.DictionaryLiteralItemContext ctx) {
        checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterOperatorDeclaration(SwiftParser.OperatorDeclarationContext ctx) {
        checkWhitespaceAroundOperator(ctx);
    }

    @Override
    public void enterTypeAnnotation(SwiftParser.TypeAnnotationContext ctx) {
        checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterDictionaryType(SwiftParser.DictionaryTypeContext ctx) {
        checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterSwitchCase(SwiftParser.SwitchCaseContext ctx) {
        checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterTypeInheritanceClause(SwiftParser.TypeInheritanceClauseContext ctx) {
        checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterConditionalOperator(SwiftParser.ConditionalOperatorContext ctx) {
        checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterExpressionElement(SwiftParser.ExpressionElementContext ctx) {
        checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterGenericParameter(SwiftParser.GenericParameterContext ctx) {
        checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterFunctionResult(SwiftParser.FunctionResultContext ctx) {
        checkWhitespaceAroundArrow(ctx);
    }

    @Override
    public void enterSType(SwiftParser.STypeContext ctx) {
        checkWhitespaceAroundArrow(ctx);
    }

    @Override
    public void enterSubscriptResult(SwiftParser.SubscriptResultContext ctx) {
        checkWhitespaceAroundArrow(ctx);
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

    private void checkWhitespaceAroundColon(SwiftParser.TypeAnnotationContext ctx) {
        TerminalNodeImpl colon = (TerminalNodeImpl) ctx.getChild(0);
        ParseTree parentLeftSibling = ParseTreeUtil.getLeftSibling(colon.getParent());
        ParseTree rightSibling = ctx.getChild(1);

        assert !(parentLeftSibling == null || rightSibling == null);

        Token left = ParseTreeUtil.getStopTokenForNode(parentLeftSibling);
        Token right = ParseTreeUtil.getStartTokenForNode(rightSibling);
        Token colonToken = colon.getSymbol();

        verifyColonLeftAssociation(left, right, colonToken);
    }

    private void checkWhitespaceAroundColon(SwiftParser.DictionaryLiteralItemContext ctx) {
        Token left = ctx.expression(0).getStop();
        Token right = ctx.expression(1).getStart();
        Token colon = ((TerminalNodeImpl) ctx.getChild(1)).getSymbol();

        verifyColonLeftAssociation(left, right, colon);
    }

    private void checkWhitespaceAroundColon(SwiftParser.DictionaryTypeContext ctx) {
        Token left = ctx.sType(0).getStop();
        Token right = ctx.sType(1).getStart();
        Token colon = ((TerminalNodeImpl) ctx.getChild(2)).getSymbol();

        verifyColonLeftAssociation(left, right, colon);
    }

    private void checkWhitespaceAroundColon(SwiftParser.SwitchCaseContext ctx) {
        Token left = null;
        Token right = null;
        Token colon = null;

        if (ctx.caseLabel() != null) {
            left = ctx.caseLabel().caseItemList().getStop();
            ParseTree rightChild = ctx.getChild(1);
            // right child can be statements or a semi colon
            right = ParseTreeUtil.getStartTokenForNode(rightChild);
            colon = ((TerminalNodeImpl) ctx.caseLabel().getChild(2)).getSymbol();
        } else {
            left = ((TerminalNodeImpl) ctx.defaultLabel().getChild(0)).getSymbol();
            ParseTree rightChild = ctx.getChild(1);
            right = ParseTreeUtil.getStartTokenForNode(rightChild);
            colon = ((TerminalNodeImpl) ctx.defaultLabel().getChild(1)).getSymbol();
        }

        verifyColonLeftAssociation(left, right, colon);
    }

    private void checkWhitespaceAroundColon(SwiftParser.TypeInheritanceClauseContext ctx) {
        Token colon = ((TerminalNodeImpl) ctx.getChild(0)).getSymbol();
        Token right = ((ParserRuleContext) ctx.getChild(1)).getStart();
        Token left = ((ParserRuleContext) ParseTreeUtil.getLeftSibling(ctx)).getStop();

        verifyColonLeftAssociation(left, right, colon);
    }

    private void checkWhitespaceAroundColon(SwiftParser.ConditionalOperatorContext ctx) {
        Token colon = ((TerminalNodeImpl) ctx.getChild(2)).getSymbol();
        Token left = ctx.expression().getStop();
        Token right = ((ParserRuleContext) ParseTreeUtil.getRightSibling(ctx)).getStart();

        verifyColonIsSpaceDelimited(left, right, colon);
    }

    private void checkWhitespaceAroundColon(SwiftParser.ExpressionElementContext ctx) {
        if (ctx.identifier() != null) {
            Token colon = ((TerminalNodeImpl) ctx.getChild(1)).getSymbol();
            Token left = ctx.identifier().getStop();
            Token right = ctx.expression().getStart();

            verifyColonLeftAssociation(left, right, colon);
        }
    }

    private void checkWhitespaceAroundColon(SwiftParser.GenericParameterContext ctx) {
        if (ctx.getChildCount() == 3) {
            Token colon = ((TerminalNodeImpl) ctx.getChild(1)).getSymbol();
            Token left = ctx.typeName().getStop();
            Token right = ((ParserRuleContext) ctx.getChild(2)).getStart();

            verifyColonLeftAssociation(left, right, colon);
        }
    }

    private void checkWhitespaceAroundArrow(SwiftParser.FunctionResultContext ctx) {
        checkWhitespaceAroundReturnArrow(ctx);
    }

    private void checkWhitespaceAroundArrow(SwiftParser.SubscriptResultContext ctx) {
        checkWhitespaceAroundReturnArrow(ctx);
    }

    private void checkWhitespaceAroundArrow(SwiftParser.STypeContext ctx) {
        Optional<ParseTree> arrowOptional = ctx.children.stream()
            .filter(node -> node.getText().equals("->"))
            .findFirst();
        if (!arrowOptional.isPresent()) {
            return;
        }
        ParseTree arrow = arrowOptional.get();
        Token left = ParseTreeUtil.getStopTokenForNode(ParseTreeUtil.getLeftSibling(arrow));
        Token right = ParseTreeUtil.getStartTokenForNode(ParseTreeUtil.getRightSibling(arrow));

        verifyArrowIsSpaceDelimited(left, right, ((TerminalNodeImpl) arrow).getSymbol());
    }

    private void verifyColonIsSpaceDelimited(Token left, Token right, Token colon) {
        Location colonLocation = ListenerUtil.getTokenLocation(colon);

        if (checkLeftSpaces(left, colon, 1)) {
            printer.error(Rules.WHITESPACE, Messages.COLON + Messages.AT_COLUMN + colonLocation.column + " "
                    + Messages.SPACE_BEFORE, colonLocation);
        }

        if (checkRightSpaces(right, colon, 1)) {
            printer.error(Rules.WHITESPACE, Messages.COLON + Messages.AT_COLUMN + colonLocation.column + " "
                    + Messages.SPACE_AFTER, colonLocation);
        }
    }

    private void verifyColonLeftAssociation(Token left, Token right, Token colon) {
        Location colonLocation = ListenerUtil.getTokenLocation(colon);

        if (checkLeftSpaces(left, colon, 0)) {
            printer.error(Rules.WHITESPACE, Messages.COLON + Messages.AT_COLUMN + colonLocation.column + " "
                    + Messages.NO_SPACE_BEFORE, colonLocation);
        }

        if (checkRightSpaces(right, colon, 1)) {
            printer.error(Rules.WHITESPACE, Messages.COLON + Messages.AT_COLUMN + colonLocation.column + " "
                    + Messages.SPACE_AFTER, colonLocation);
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

    private void checkWhitespaceAroundReturnArrow(ParserRuleContext ctx) {
        Token arrow = ((TerminalNodeImpl) ctx.getChild(0)).getSymbol();
        Token left = ParseTreeUtil.getStopTokenForNode(ParseTreeUtil.getLeftSibling(ctx));
        Token right = ParseTreeUtil.getStartTokenForNode(ctx.getChild(1));

        verifyArrowIsSpaceDelimited(left, right, arrow);
    }

    private void verifyArrowIsSpaceDelimited(Token left, Token right, Token arrow) {
        if (checkLeftSpaces(left, arrow, 1)) {
            printer.error(Rules.WHITESPACE, Messages.RETURN_ARROW + Messages.SPACE_BEFORE,
                ListenerUtil.getTokenLocation(arrow));
        }
        if (checkRightSpaces(right, arrow, 1)) {
            printer.error(Rules.WHITESPACE, Messages.RETURN_ARROW + Messages.SPACE_AFTER,
                ListenerUtil.getTokenEndLocation(arrow));
        }
    }
}
