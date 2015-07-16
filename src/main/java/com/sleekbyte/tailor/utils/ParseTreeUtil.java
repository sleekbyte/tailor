package com.sleekbyte.tailor.utils;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Utils for traversing Parse Trees.
 */
public final class ParseTreeUtil {

    /**
     * Return parent `nval` levels above ctx.
     *
     * @param ctx Child node
     * @param nval 'n' value, number of levels to go up the tree
     * @return Parent node or null if parent does not exist
     */
    public static ParserRuleContext getNthParent(ParserRuleContext ctx, int nval) {
        if (ctx == null) {
            return null;
        }
        while (nval != 0) {
            nval--;
            ctx = ctx.getParent();
            if (ctx == null) {
                return null;
            }
        }
        return ctx;
    }

    /**
     * Gets left sibling of a parse tree node.
     *
     * @param ctx A node.
     * @return Left sibling of a node, or null if no sibling is found
     */
    public static ParseTree getLeftSibling(ParseTree ctx) {
        if (ctx == null || ctx.getParent() == null) {
            return null;
        }
        ParseTree parent = ctx.getParent();
        for (int i = 0; i < parent.getChildCount(); i++) {
            if (parent.getChild(i) == ctx) {
                if (i < 1) {
                    return null;
                } else {
                    return parent.getChild(i - 1);
                }
            }
        }
        return null;
    }

    /**
     * Gets right sibling of a parse tree node.
     *
     * @param ctx A node.
     * @return Right sibling of a node, or null if no sibling is found
     */
    public static ParseTree getRightSibling(ParseTree ctx) {
        if (ctx == null || ctx.getParent() == null) {
            return null;
        }
        ParseTree parent = ctx.getParent();
        for (int i = 0; i < parent.getChildCount(); i++) {
            if (parent.getChild(i) == ctx) {
                if (i >= parent.getChildCount() - 1) {
                    return null;
                } else {
                    return parent.getChild(i + 1);
                }
            }
        }
        return null;
    }
}
