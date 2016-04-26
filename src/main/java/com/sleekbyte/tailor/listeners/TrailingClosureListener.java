package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser.ExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ExpressionElementContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ExpressionElementListContext;
import com.sleekbyte.tailor.antlr.SwiftParser.FunctionCallExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.PostfixExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.PrimaryExpressionContext;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;

import java.util.List;

/**
 * Listener that checks for closures that should be passed to functions as trailing closures.
 */
public final class TrailingClosureListener extends SwiftBaseListener {

    private Printer printer;

    public TrailingClosureListener(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void enterFunctionCallExpression(FunctionCallExpressionContext ctx) {
        ExpressionElementListContext elemList = ctx.parenthesizedExpression().expressionElementList();

        // Check if the function call has any parameters
        if (elemList == null) {
            return;
        }

        List<ExpressionElementContext> elements = elemList.expressionElement();

        // Check if the last parameter isn't named
        ExpressionElementContext element = elements.get(elements.size() - 1);
        if (element.expressionElementIdentifier() != null) {
            return;
        }

        // Check if the parameter is a simple prefix expression
        ExpressionContext expression = element.expression();
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
