package com.sleekbyte.tailor.utils;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

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
     * @param node A child node
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
     * @param ctx A node
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
     * @param ctx A node
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

    /**
     * Gets last child of a parse tree node.
     *
     * @param ctx A node
     * @return Last child of a node, or null if node has no children
     */
    public static ParseTree getLastChild(ParseTree ctx) {
        if (ctx.getChildCount() == 0) {
            return null;
        }
        return ctx.getChild(ctx.getChildCount() - 1);
    }

    /**
     * Return node situated on the left of the input node (does not have to be at the same level as the current node).
     *
     * @param ctx A node
     * @return The left node
     */
    public static ParseTree getLeftNode(ParseTree ctx) {
        while (true) {
            if (ctx == null) {
                return null;
            }
            ParseTree left = getLeftSibling(ctx);
            if (left != null) {
                return left;
            }
            ctx = ctx.getParent();
        }
    }

    /**
     * Return node situated on the right of the input node (does not have to be at the level as the current node).
     *
     * @param ctx A node
     * @return The right node
     */
    public static ParseTree getRightNode(ParseTree ctx) {
        while (true) {
            if (ctx == null) {
                return null;
            }
            ParseTree right = getRightSibling(ctx);
            if (right != null) {
                return right;
            }
            ctx = ctx.getParent();
        }
    }

    /**
     * Returns the starting token of the construct represented by node.
     *
     * @param node A node
     * @return Start token
     */
    public static Token getStartTokenForNode(ParseTree node) {
        if (node instanceof TerminalNodeImpl) {
            return ((TerminalNodeImpl) node).getSymbol();
        } else {
            return ((ParserRuleContext) node).getStart();
        }
    }

    /**
     * Returns the last token of the construct represented by node.
     *
     * @param node A node
     * @return Stop token
     */
    public static Token getStopTokenForNode(ParseTree node) {
        if (node instanceof TerminalNodeImpl) {
            return ((TerminalNodeImpl) node).getSymbol();
        } else {
            return ((ParserRuleContext) node).getStop();
        }
    }
}
