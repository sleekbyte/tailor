package com.sleekbyte.tailor.utils;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

/**
 *  Contains utility functions that are used by various whitespace listeners to verify whitespace.
 */
public final class WhitespaceVerifier {

    private Printer printer;
    private Rules rule;

    public WhitespaceVerifier(Printer printer, Rules rule) {
        this.printer = printer;
        this.rule = rule;
    }

    public boolean checkLeftSpaces(Token left, Token op, int numSpaces) {
        return op.getLine() == left.getLine()
            && op.getCharPositionInLine() - ListenerUtil.getLastCharPositionInLine(left) != numSpaces + 1;
    }

    public boolean checkRightSpaces(Token right, Token op, int numSpaces) {
        return right.getLine() == op.getLine()
            && right.getCharPositionInLine() - ListenerUtil.getLastCharPositionInLine(op) != numSpaces + 1;
    }

    public boolean checkIfInline(Token one, Token two) {
        return one.getLine() != two.getLine();
    }

    /**
     * Verifies if a certain punctuation token is left associated (no space on the left).
     *
     * @param left Token on the left of the punctuation token
     * @param punc Punctuation token
     * @param puncStr String version of the punctuation to be used in violation messages
     */
    public void verifyPunctuationLeftAssociation(Token left, Token punc, String puncStr) {
        Location puncLocation = ListenerUtil.getTokenLocation(punc);

        if (checkIfInline(left, punc) || checkLeftSpaces(left, punc, 0)) {
            printer.error(rule, puncStr + Messages.AT_COLUMN + puncLocation.column + " "
                + Messages.NO_SPACE_BEFORE, puncLocation);
        }
    }

    /**
     * Verifies if a certain punctuation token is left associated (no space on the left, one space on the right).
     *
     * @param left Token on the left of the punctuation token
     * @param right Token on the right of the punctuation token
     * @param punc Punctuation token
     * @param puncStr String version of the punctuation to be used in violation messages
     */
    public void verifyPunctuationLeftAssociation(Token left, Token right, Token punc, String puncStr) {
        Location puncLocation = ListenerUtil.getTokenLocation(punc);

        verifyPunctuationLeftAssociation(left, punc, puncStr);

        if (checkRightSpaces(right, punc, 1)) {
            printer.error(rule, puncStr + Messages.AT_COLUMN + puncLocation.column + " "
                + Messages.SPACE_AFTER, puncLocation);
        }
    }

    /**
     * Verifies if a particular punctuation token is space delimited (single space on either side).
     *
     * @param left Token on the left of the punctuation token
     * @param right Token on the right of the punctuation token
     * @param punc Punctuation token
     * @param puncStr String version of the punctuation to be used in violation messages
     */
    public void verifyPunctuationIsSpaceDelimited(Token left, Token right, Token punc, String puncStr) {
        Location puncLocation = ListenerUtil.getTokenLocation(punc);
        if (checkLeftSpaces(left, punc, 1)) {
            printer.error(rule, puncStr + Messages.AT_COLUMN + puncLocation.column + " "
                + Messages.SPACE_BEFORE, puncLocation);
        }

        if (checkRightSpaces(right, punc, 1)) {
            printer.error(rule, puncStr + Messages.AT_COLUMN + puncLocation.column + " "
                + Messages.SPACE_AFTER, puncLocation);
        }
    }

    /**
     * Verifies if a bracket construct does not contain whitespace immediately after the opening bracket and
     * immediately before the closing bracket.
     *
     * @param ctx Context comprised of brackets
     * @param construct Name of construct
     */
    public void verifyBracketContentWhitespace(ParserRuleContext ctx, String construct) {
        Token openingParenthesis = ParseTreeUtil.getStopTokenForNode(ctx.getChild(0));
        Token closingParenthesis = ParseTreeUtil.getStopTokenForNode(ctx.getChild(ctx.getChildCount() - 1));
        Location openingParenthesisLoc = ListenerUtil.getTokenLocation(openingParenthesis);

        // Handles cases where the parentheses only contain whitespace
        // Example: if ( ) {}
        if (ctx.getChildCount() == 2) {
            if (checkLeftSpaces(openingParenthesis, closingParenthesis, 0)) {
                printer.error(rule, Messages.EMPTY + construct.toLowerCase() + Messages.ILLEGAL_WHITESPACE,
                    new Location(openingParenthesisLoc.line, openingParenthesisLoc.column + 1) );
            }
            return;
        }

        Token contentStart = ParseTreeUtil.getStartTokenForNode(ctx.getChild(1));
        Token contentEnd = ParseTreeUtil.getStopTokenForNode(ctx.getChild(ctx.getChildCount() - 2));

        if (checkLeftSpaces(openingParenthesis, contentStart, 0)) {
            printer.error(rule, construct + Messages.CONTENT + Messages.LEADING_WHITESPACE,
                new Location(openingParenthesisLoc.line, openingParenthesisLoc.column + 1));
        }
        if (checkRightSpaces(closingParenthesis, contentEnd, 0)) {
            Location contentStopLocation = ListenerUtil.getTokenEndLocation(contentEnd);
            printer.error(rule, construct + Messages.CONTENT + Messages.NOT_END_SPACE, contentStopLocation);
        }
    }

    /**
     * Verifies that bracket constructs do not have a whitespace before the opening bracket.
     *
     * @param ctx Context comprised of brackets
     * @param construct Name of construct
     * @param message Violation message
     * @param numSpace Number of spaces to check for
     */
    public void verifyLeadingWhitespaceBeforeBracket(ParserRuleContext ctx,
                                                     String construct,
                                                     String message,
                                                     int numSpace) {
        Token left = ParseTreeUtil.getStopTokenForNode(ParseTreeUtil.getLeftNode(ctx));
        Token openingParenthesis = ParseTreeUtil.getStartTokenForNode(ctx.getChild(0));

        if (checkLeftSpaces(left, openingParenthesis, numSpace)) {
            Location illegalWhitespaceLocation =  ListenerUtil.getTokenEndLocation(left);
            printer.error(rule, construct + message, illegalWhitespaceLocation);
        }
    }

    /**
     * Verifies that bracket constructs do not have a whitespace before the opening bracket.
     *
     * @param ctx Context comprised of brackets
     * @param message Violation message
     * @param numSpace Number of spaces to check for
     */
    public void verifyLeadingWhitespaceBeforeBracket(ParserRuleContext ctx,
                                                     String message,
                                                     int numSpace) {
        Token left = ParseTreeUtil.getStopTokenForNode(ParseTreeUtil.getLeftNode(ctx));
        Token openingParenthesis = ParseTreeUtil.getStartTokenForNode(ctx.getChild(0));

        if (checkLeftSpaces(left, openingParenthesis, numSpace)) {
            Location illegalWhitespaceLocation =  ListenerUtil.getTokenEndLocation(left);
            printer.error(rule, message, illegalWhitespaceLocation);
        }
    }

}
