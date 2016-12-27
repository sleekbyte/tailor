package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.antlr.SwiftParser.ArrayLiteralItemContext;
import com.sleekbyte.tailor.antlr.SwiftParser.CatchClauseContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ConditionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.DictionaryLiteralItemContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.FunctionCallArgumentClauseContext;
import com.sleekbyte.tailor.antlr.SwiftParser.FunctionCallWithClosureExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.InitializerContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ParenthesizedExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.PatternContext;
import com.sleekbyte.tailor.antlr.SwiftParser.PrimaryExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.RepeatWhileStatementContext;
import com.sleekbyte.tailor.antlr.SwiftParser.SwitchStatementContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ThrowStatementContext;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Parse tree listener for redundant parentheses checks.
 */
public class RedundantParenthesesListener extends SwiftBaseListener {

    private Printer printer;

    public RedundantParenthesesListener(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void enterCondition(ConditionContext ctx) {
        verifyRedundantExpressionParentheses(Messages.CONDITION, ctx.expression());
    }

    @Override
    public void enterRepeatWhileStatement(RepeatWhileStatementContext ctx) {
        verifyRedundantExpressionParentheses(Messages.CONDITION, ctx.expression());
    }

    @Override
    public void enterSwitchStatement(SwitchStatementContext ctx) {
        verifyRedundantExpressionParentheses(Messages.SWITCH_EXPRESSION, ctx.expression());
    }

    @Override
    public void enterThrowStatement(ThrowStatementContext ctx) {
        verifyRedundantExpressionParentheses(Messages.THROW_STATEMENT, ctx.expression());
    }

    @Override
    public void enterCatchClause(CatchClauseContext ctx) {
        PatternContext patternCtx = ctx.pattern();
        if (patternCtx == null) {
            return;
        }
        String pattern = patternCtx.getText();
        char firstCharacter = pattern.charAt(0);
        char lastCharacter = pattern.charAt(pattern.length() - 1);

        if (firstCharacter == '(' && lastCharacter == ')') {
            printRedundantParenthesesWarning(patternCtx, Messages.CATCH_CLAUSE + Messages.ENCLOSED_PARENTHESES);
        }
    }

    @Override
    public void enterInitializer(InitializerContext ctx) {
        verifyRedundantExpressionParentheses(Messages.INITIALIZER_EXPRESSION, ctx.expression());
    }

    @Override
    public void enterArrayLiteralItem(ArrayLiteralItemContext ctx) {
        verifyRedundantExpressionParentheses(Messages.ARRAY_LITERAL, ctx.expression());
    }

    @Override
    public void enterDictionaryLiteralItem(DictionaryLiteralItemContext ctx) {
        for (ExpressionContext expressionContext : ctx.expression()) {
            verifyRedundantExpressionParentheses(Messages.DICTIONARY_LITERAL, expressionContext);
        }
    }

    @Override
    public void enterFunctionCallWithClosureExpression(FunctionCallWithClosureExpressionContext ctx) {
        FunctionCallArgumentClauseContext argumentClause = ctx.functionCallArgumentClause();
        if (argumentClause != null && argumentClause.functionCallArgumentList() == null) {
            printRedundantParenthesesWarning(argumentClause, Messages.EMPTY_PARENTHESES
                + Messages.REDUNDANT_METHOD_PARENTHESES);
        }
    }

    private void verifyRedundantExpressionParentheses(String constructType, ExpressionContext ctx) {
        if (ctx == null
            || ctx.getChildCount() != 1
            || ctx.prefixExpression() == null
            || ctx.prefixExpression().prefixOperator() != null // flag cases with trailing ;
            || ctx.prefixExpression().postfixExpression() == null
            || ctx.prefixExpression().postfixExpression().getChildCount() != 1) {
            return;
        }

        SwiftParser.PostfixExpressionContext postfixExpression = ctx.prefixExpression().postfixExpression();

        if (!(postfixExpression.getChild(0) instanceof PrimaryExpressionContext)) {
            return;
        }

        PrimaryExpressionContext primaryExpression =
            (PrimaryExpressionContext) postfixExpression.getChild(0);

        if (primaryExpression.getChildCount() != 1
            || !(primaryExpression.getChild(0) instanceof ParenthesizedExpressionContext)) {
            return;
        }

        printRedundantParenthesesWarning(ctx, constructType + Messages.ENCLOSED_PARENTHESES);
    }

    private void printRedundantParenthesesWarning(ParserRuleContext ctx, String firstParenthesisMsg) {
        Location startLocation = ListenerUtil.getContextStartLocation(ctx);
        this.printer.warn(Rules.REDUNDANT_PARENTHESES, firstParenthesisMsg, startLocation);
    }

}
