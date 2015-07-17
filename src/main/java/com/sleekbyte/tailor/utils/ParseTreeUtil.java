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
     * Returns node's index with in its parent's child array.
     *
     * @param node A child node.
     * @return Node's index or -1 if node is null or doesn't have a parent
     */
    public static int getNodeIndex(ParseTree node) {
        if (node == null || node.getParent() == null) {
            return -1;
        }
        ParseTree parent = node.getParent();
        for (int i = 0; i < parent.getChildCount(); i++) {
            if (parent.getChild(i) == node) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Gets left sibling of a parse tree node.
     *
     * @param ctx A node.
     * @return Left sibling of a node, or null if no sibling is found
     */
    public static ParseTree getLeftSibling(ParseTree ctx) {
        int index = ParseTreeUtil.getNodeIndex(ctx);
        if (index < 1) {
            return null;
        }
        return ctx.getParent().getChild(index - 1);
    }

    /**
     * Gets right sibling of a parse tree node.
     *
     * @param ctx A node.
     * @return Right sibling of a node, or null if no sibling is found
     */
    public static ParseTree getRightSibling(ParseTree ctx) {
        int index = ParseTreeUtil.getNodeIndex(ctx);
        ParseTree parent = ctx.getParent();
        if (index < 0 || index >= parent.getChildCount() - 1) {
            return null;
        }
        return parent.getChild(index + 1);
    }

}
