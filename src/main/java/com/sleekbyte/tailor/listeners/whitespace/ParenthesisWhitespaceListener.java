package com.sleekbyte.tailor.listeners.whitespace;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import com.sleekbyte.tailor.utils.ParseTreeUtil;
import com.sleekbyte.tailor.utils.WhitespaceVerifier;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

/**
 * Parse tree listener to flag illegal whitespace in parentheses.
 */
public final class ParenthesisWhitespaceListener extends SwiftBaseListener {
    private Printer printer;
    private WhitespaceVerifier verifier;
    private Rules rule = Rules.PARENTHESIS_WHITESPACE;

    public ParenthesisWhitespaceListener(Printer printer) {
        this.printer = printer;
        this.verifier = new WhitespaceVerifier(printer, rule);
    }

    @Override
    public void enterGenericParameterClause(SwiftParser.GenericParameterClauseContext ctx) {
        verifyParenthesisContentWhitespace(ctx);
    }

    @Override
    public void enterParameterClause(SwiftParser.ParameterClauseContext ctx) {
        verifyParenthesisContentWhitespace(ctx);
    }

    @Override
    public void enterTupleType(SwiftParser.TupleTypeContext ctx) {
        verifyParenthesisContentWhitespace(ctx);
    }

    @Override
    public void enterParenthesizedExpression(SwiftParser.ParenthesizedExpressionContext ctx) {
        verifyParenthesisContentWhitespace(ctx);
    }

    @Override
    public void enterParameterClauses(SwiftParser.ParameterClausesContext ctx) {
        verifyParenthesisSurroundingWhitespace(ctx);
    }

    @Override
    public void enterInitializerDeclaration(SwiftParser.InitializerDeclarationContext ctx) {
        verifyParenthesisSurroundingWhitespace(ctx.parameterClause());
    }

    /**
     * Verifies if a parenthesized construct does not contain whitespace immediately after the opening parenthesis and
     * immediately before the closing parenthesis.
     *
     * @param ctx Context comprised of parentheses
     */
    private void verifyParenthesisContentWhitespace(ParserRuleContext ctx) {
        Token openingParenthesis = ParseTreeUtil.getStopTokenForNode(ctx.getChild(0));
        Token closingParenthesis = ParseTreeUtil.getStopTokenForNode(ctx.getChild(ctx.getChildCount() - 1));
        Location openingParenthesisLoc = ListenerUtil.getTokenLocation(openingParenthesis);

        // Handles cases where the parentheses only contain whitespace
        // Example: if ( ) {}
        if (ctx.getChildCount() == 2) {
            if (verifier.checkLeftSpaces(openingParenthesis, closingParenthesis, 0)) {
                printer.error(rule, Messages.EMPTY_PARENTHESES + Messages.ILLEGAL_WHITESPACE,
                    new Location(openingParenthesisLoc.line, openingParenthesisLoc.column + 1) );
            }
            return;
        }

        Token contentStart = ParseTreeUtil.getStartTokenForNode(ctx.getChild(1));
        Token contentEnd = ParseTreeUtil.getStopTokenForNode(ctx.getChild(ctx.getChildCount() - 2));

        if (verifier.checkLeftSpaces(openingParenthesis, contentStart, 0)) {
            printer.error(rule, Messages.PARENTHESES_CONTENT + Messages.LEADING_WHITESPACE,
                new Location(openingParenthesisLoc.line, openingParenthesisLoc.column + 1));
        }
        if (verifier.checkRightSpaces(closingParenthesis, contentEnd, 0)) {
            Location contentStopLocation = ListenerUtil.getTokenEndLocation(contentEnd);
            printer.error(rule, Messages.PARENTHESES_CONTENT + Messages.NOT_END_SPACE, contentStopLocation);
        }
    }

    /**
     * Verifies that parenthesized constructs do not have a whitespace before the opening parenthesis.
     *
     * @param ctx Context comprised of parentheses
     */
    private void verifyParenthesisSurroundingWhitespace(ParserRuleContext ctx) {
        Token left = ParseTreeUtil.getStopTokenForNode(ParseTreeUtil.getLeftNode(ctx));
        Token openingParenthesis = ParseTreeUtil.getStartTokenForNode(ctx.getChild(0));

        if (verifier.checkLeftSpaces(left, openingParenthesis, 0)) {
            Location illegalWhitespaceLocation =  ListenerUtil.getTokenEndLocation(left);
            printer.error(rule, Messages.NO_WHITESPACE_BEFORE_PARENTHESES, illegalWhitespaceLocation);
        }
    }

}
