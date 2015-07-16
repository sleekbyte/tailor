package com.sleekbyte.tailor.utils;


import com.sleekbyte.tailor.common.Location;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

/**
 * Class with utility functions that are used by listeners.
 */
public final class ListenerUtil {

    public static Location getTokenLocation(Token token) {
        return new Location(token.getLine(), token.getCharPositionInLine() + 1);
    }

    public static int getLastCharPositionInLine(Token token) {
        return token.getCharPositionInLine() + (token.getStopIndex() - token.getStartIndex());
    }

    public static Location getContextStartLocation(ParserRuleContext ctx) {
        return new Location(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1);
    }

    public static Location getContextStopLocation(ParserRuleContext ctx) {
        return new Location(ctx.getStop().getLine(), ctx.getStop().getCharPositionInLine() + 1);
    }

    public static Location getLocationOfChildToken(ParserRuleContext ctx, int childNumber) {
        Token token = ((TerminalNodeImpl) ctx.getChild(childNumber)).getSymbol();
        return ListenerUtil.getTokenLocation(token);
    }
}
