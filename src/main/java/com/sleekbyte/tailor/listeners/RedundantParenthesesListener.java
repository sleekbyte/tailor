package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.antlr.SwiftParser.ArrayLiteralItemContext;
import com.sleekbyte.tailor.antlr.SwiftParser.CatchClauseContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ConditionClauseContext;
import com.sleekbyte.tailor.antlr.SwiftParser.DictionaryLiteralItemContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ForStatementContext;
import com.sleekbyte.tailor.antlr.SwiftParser.FunctionCallWithClosureExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.InitializerContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ParenthesizedExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.PatternContext;
import com.sleekbyte.tailor.antlr.SwiftParser.PrimaryExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.SwitchStatementContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ThrowStatementContext;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

/**
 * Parse tree listener for redundant parentheses checks.
 */
public class RedundantParenthesesListener extends SwiftBaseListener {

    private Printer printer;

    public RedundantParenthesesListener(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void enterConditionClause(ConditionClauseContext ctx) {
        verifyRedundantExpressionParentheses(Messages.CONDITIONAL_CLAUSE, ctx.expression());
    }

    @Override
    public void enterSwitchStatement(SwitchStatementContext ctx) {
        verifyRedundantExpressionParentheses(Messages.SWITCH_EXPRESSION, ctx.expression());
    }

    @Override
    public void enterForStatement(ForStatementContext ctx) {
        if (!(ctx.getChild(1) instanceof TerminalNodeImpl)) {
            return;
        } // return if '(' not present

        Token openParenthesisToken = ((TerminalNodeImpl) ctx.getChild(1)).getSymbol();
        char firstCharacter = openParenthesisToken.getText().charAt(0);

        if (firstCharacter == '(') {
            Location startLocation = ListenerUtil.getTokenLocation(openParenthesisToken);
            this.printer.warn(Rules.REDUNDANT_PARENTHESES, Messages.FOR_LOOP + Messages.ENCLOSED_PARENTHESES,
                startLocation);
        }
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
        if (ctx.parenthesizedExpression() != null && ctx.parenthesizedExpression().expressionElementList() == null) {
            printRedundantParenthesesWarning(ctx.parenthesizedExpression(), Messages.EMPTY_PARENTHESES
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

        ParenthesizedExpressionContext parenthesizedExpressionContext =
            (ParenthesizedExpressionContext) primaryExpression.getChild(0);

        // check to not flag tuple initialization
        if (parenthesizedExpressionContext.expressionElementList() == null
            || parenthesizedExpressionContext.expressionElementList().getChildCount() != 1) {
            return;
        }

        printRedundantParenthesesWarning(ctx, constructType + Messages.ENCLOSED_PARENTHESES);
    }

    private void printRedundantParenthesesWarning(ParserRuleContext ctx, String firstParenthesisMsg) {
        Location startLocation = ListenerUtil.getContextStartLocation(ctx);
        this.printer.warn(Rules.REDUNDANT_PARENTHESES, firstParenthesisMsg, startLocation);
    }

}
