package com.sleekbyte.tailor.listeners.whitespace;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ParseTreeUtil;
import com.sleekbyte.tailor.utils.WhitespaceVerifier;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.Optional;

/**
 * Verifies that the return arrow is always space delimited.
 */
public final class ArrowWhitespaceListener extends SwiftBaseListener {

    private WhitespaceVerifier verifier;

    public ArrowWhitespaceListener(Printer printer) {
        this.verifier = new WhitespaceVerifier(printer, Rules.ARROW_WHITESPACE);
    }

    @Override
    public void enterFunctionResult(SwiftParser.FunctionResultContext ctx) {
        checkWhitespaceAroundReturnArrow(ctx);
    }

    @Override
    public void enterFunctionType(SwiftParser.FunctionTypeContext ctx) {
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

    @Override
    public void enterSubscriptResult(SwiftParser.SubscriptResultContext ctx) {
        checkWhitespaceAroundReturnArrow(ctx);
    }

    private void checkWhitespaceAroundReturnArrow(ParserRuleContext ctx) {
        Token arrow = ((TerminalNodeImpl) ctx.getChild(0)).getSymbol();
        Token left = ParseTreeUtil.getStopTokenForNode(ParseTreeUtil.getLeftSibling(ctx));
        Token right = ParseTreeUtil.getStartTokenForNode(ctx.getChild(1));

        verifyArrowIsSpaceDelimited(left, right, arrow);
    }

    private void verifyArrowIsSpaceDelimited(Token left, Token right, Token arrow) {
        verifier.verifyPunctuationIsSpaceDelimited(left, right, arrow, Messages.RETURN_ARROW);
    }

}
