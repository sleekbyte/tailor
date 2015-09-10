package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
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
    public void enterConditionClause(SwiftParser.ConditionClauseContext ctx) {
        verifyRedundantExpressionParentheses(Messages.CONDITIONAL_CLAUSE, ctx.expression());
    }

    @Override
    public void enterSwitchStatement(SwiftParser.SwitchStatementContext ctx) {
        verifyRedundantExpressionParentheses(Messages.SWITCH_EXPRESSION, ctx.expression());
    }

    @Override
    public void enterForStatement(SwiftParser.ForStatementContext ctx) {
        verifyRedundantForLoopParentheses(ctx);
    }

    @Override
    public void enterThrowStatement(SwiftParser.ThrowStatementContext ctx) {
        verifyRedundantExpressionParentheses(Messages.THROW_STATEMENT, ctx.expression());
    }

    @Override
    public void enterCatchClause(SwiftParser.CatchClauseContext ctx) {
        verifyRedundantCatchParentheses(ctx.pattern());
    }

    @Override
    public void enterInitializer(SwiftParser.InitializerContext ctx) {
        verifyRedundantExpressionParentheses(Messages.INITIALIZER_EXPRESSION, ctx.expression());
    }

    @Override
    public void enterArrayLiteralItem(SwiftParser.ArrayLiteralItemContext ctx) {
        verifyRedundantExpressionParentheses(Messages.ARRAY_LITERAL, ctx.expression());
    }

    @Override
    public void enterDictionaryLiteralItem(SwiftParser.DictionaryLiteralItemContext ctx) {
        for (SwiftParser.ExpressionContext expressionContext : ctx.expression()) {
            verifyRedundantExpressionParentheses(Messages.DICTIONARY_LITERAL, expressionContext);
        }
    }

    private void verifyRedundantExpressionParentheses(String constructType, SwiftParser.ExpressionContext ctx) {
        if (ctx == null
            || ctx.getChildCount() != 1
            || ctx.prefixExpression() == null
            || ctx.prefixExpression().prefixOperator() != null // flag cases with trailing ;
            || ctx.prefixExpression().postfixExpression() == null
            || ctx.prefixExpression().postfixExpression().getChildCount() != 1) {
            return;
        }

        SwiftParser.PostfixExpressionContext postfixExpression = ctx.prefixExpression().postfixExpression();

        if (!(postfixExpression.getChild(0) instanceof SwiftParser.PrimaryExpressionContext)) {
            return;
        }

        SwiftParser.PrimaryExpressionContext primaryExpression =
            (SwiftParser.PrimaryExpressionContext) postfixExpression.getChild(0);

        if (primaryExpression.getChildCount() != 1
            || !(primaryExpression.getChild(0) instanceof SwiftParser.ParenthesizedExpressionContext)) {
            return;
        }

        SwiftParser.ParenthesizedExpressionContext parenthesizedExpressionContext =
            (SwiftParser.ParenthesizedExpressionContext) primaryExpression.getChild(0);

        // check to not flag tuple initialization
        if (parenthesizedExpressionContext.expressionElementList() == null
            || parenthesizedExpressionContext.expressionElementList().getChildCount() != 1) {
            return;
        }

        printRedundantParenthesesWarning(ctx, constructType + Messages.ENCLOSED_PARENTHESES);
    }

    private void verifyRedundantForLoopParentheses(ParserRuleContext ctx) {
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

    private void verifyRedundantCatchParentheses(ParserRuleContext ctx) {
        if (ctx == null) {
            return;
        }
        String pattern = ctx.getText();
        char firstCharacter = pattern.charAt(0);
        char lastCharacter = pattern.charAt(pattern.length() - 1);

        if (firstCharacter == '(' && lastCharacter == ')') {
            printRedundantParenthesesWarning(ctx, Messages.CATCH_CLAUSE + Messages.ENCLOSED_PARENTHESES);
        }
    }

    private void printRedundantParenthesesWarning(ParserRuleContext ctx, String firstParenthesisMsg) {
        Location startLocation = ListenerUtil.getContextStartLocation(ctx);
        this.printer.warn(Rules.REDUNDANT_PARENTHESES, firstParenthesisMsg, startLocation);
    }

}
