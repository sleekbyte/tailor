package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser.ConditionListContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.FunctionCallArgumentContext;
import com.sleekbyte.tailor.antlr.SwiftParser.FunctionCallArgumentListContext;
import com.sleekbyte.tailor.antlr.SwiftParser.FunctionCallExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.PostfixExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.PrimaryExpressionContext;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

/**
 * Listener that checks for closures that should be passed to functions as trailing closures.
 */
public final class TrailingClosureListener extends SwiftBaseListener {

    private Printer printer;

    public TrailingClosureListener(Printer printer) {
        this.printer = printer;
    }

    /**
     * Check if ctx is part of a condition-clause.
     *
     * @param ctx Parse tree node
     * @return true if ctx is inside a condition-clause.
     */
    public static boolean isInsideConditionList(ParserRuleContext ctx) {
        if (ctx == null) {
            return false;
        }
        if (ctx instanceof ConditionListContext) {
            return true;
        }
        return isInsideConditionList(ctx.getParent());
    }

    @Override
    public void enterFunctionCallExpression(FunctionCallExpressionContext ctx) {
        FunctionCallArgumentListContext argumentList =
            ctx.functionCallArgumentClause().functionCallArgumentList();

        // Check if the function call is inside a condition clause (Issue #401)
        if (isInsideConditionList(ctx)) {
            return;
        }

        // Check if the function call has any parameters
        if (argumentList == null) {
            return;
        }

        List<FunctionCallArgumentContext> arguments = argumentList.functionCallArgument();

        // Check if the last parameter isn't named
        FunctionCallArgumentContext argument = arguments.get(arguments.size() - 1);
        if (argument.functionCallIdentifier() != null) {
            return;
        }

        // Check if the last parameter isn't an operator
        if (argument.operator() != null) {
            return;
        }

        // Check if the last parameter has an expression
        if (argument.expression() == null) {
            return;
        }

        // Check if the parameter is a simple prefix expression
        ExpressionContext expression = argument.expression();
        if (expression.binaryExpression().size() != 0) {
            return;
        }

        // Check if the parameter is a closure
        PostfixExpressionContext postfixExpr = expression.prefixExpression().postfixExpression();
        if (postfixExpr.getChild(0) instanceof PrimaryExpressionContext) {
            PrimaryExpressionContext primaryExpr = (PrimaryExpressionContext) postfixExpr.getChild(0);
            if (primaryExpr.closureExpression() != null) {
                printer.warn(Rules.TRAILING_CLOSURE, Messages.CLOSURE + Messages.TRAILING_CLOSURE,
                    ListenerUtil.getContextStartLocation(primaryExpr.closureExpression()));
            }
        }
    }

}
