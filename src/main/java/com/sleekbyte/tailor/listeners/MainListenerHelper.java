package com.sleekbyte.tailor.listeners;

import static com.sleekbyte.tailor.antlr.SwiftParser.ExpressionContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.OptionalBindingContinuationContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.OptionalBindingHeadContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.ParenthesizedExpressionContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.PatternContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.PostfixExpressionContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.PrimaryExpressionContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.TuplePatternContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.TuplePatternElementContext;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.CharFormatUtil;
import com.sleekbyte.tailor.utils.SourceFileUtil;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.List;

/**
 * Helper class for {@link MainListener}.
 */
class MainListenerHelper {

    private Printer printer;

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    void verifyUpperCamelCase(String constructType, ParserRuleContext ctx) {
        String constructName = ctx.getText();
        if (!CharFormatUtil.isUpperCamelCase(constructName)) {
            Location location = new Location(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1);
            this.printer.error(constructType + Messages.UPPER_CAMEL_CASE, location);
        }
    }

    void verifyNotSemicolonTerminated(String constructType, ParserRuleContext ctx) {
        String construct = ctx.getText();
        if (construct.endsWith(";")) {
            Location location = new Location(ctx.getStop().getLine(), ctx.getStop().getCharPositionInLine() + 1);
            this.printer.error(constructType + Messages.SEMICOLON, location);
        }
    }

    void verifyConstructLength(String constructType, int maxLength, ParserRuleContext ctx) {
        if (SourceFileUtil.constructTooLong(ctx, maxLength)) {
            int constructLength = ctx.getStop().getLine() - ctx.getStart().getLine();
            String lengthVersusLimit = " (" + constructLength + "/" + maxLength + ")";
            Location location = new Location(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1);
            this.printer.error(constructType + Messages.EXCEEDS_LINE_LIMIT + lengthVersusLimit, location);
        }
    }

    void verifyNameLength(String constructType, int maxLength, ParserRuleContext ctx) {
        if (SourceFileUtil.nameTooLong(ctx, maxLength)) {
            String lengthVersusLimit = " (" + ctx.getText().length() + "/" + maxLength + ")";
            Location location = new Location(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1);
            this.printer.error(constructType + Messages.EXCEEDS_CHARACTER_LIMIT + lengthVersusLimit, location);
        }
    }

    void verifyLowerCamelCase(String constructType, ParserRuleContext ctx) {
        String constructName = ctx.getText();
        if (!CharFormatUtil.isLowerCamelCase(constructName)) {
            Location location = new Location(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1);
            this.printer.error(constructType + Messages.LOWER_CAMEL_CASE, location);
        }
    }

    void walkConstantDecListener(ParseTreeWalker walker, ParserRuleContext tree) {
        walker.walk(new ConstantDecListener(this.printer), tree);
    }

    void evaluatePattern(PatternContext pattern, ParseTreeWalker walker) {
        if (pattern.identifierPattern() != null) {
            walkConstantDecListener(walker, pattern.identifierPattern());

        } else if (pattern.tuplePattern() != null && pattern.tuplePattern().tuplePatternElementList() != null) {
            evaluateTuplePattern(pattern.tuplePattern(), walker);

        } else if (pattern.enumCasePattern() != null && pattern.enumCasePattern().tuplePattern() != null) {
            evaluateTuplePattern(pattern.enumCasePattern().tuplePattern(), walker);

        } else if (pattern.pattern() != null) {
            evaluatePattern(pattern.pattern(), walker);

        } else if (pattern.expressionPattern() != null) {
            walkConstantDecListener(walker, pattern.expressionPattern().expression().prefixExpression());
        }
    }

    void evaluateTuplePattern(TuplePatternContext tuplePatternContext, ParseTreeWalker walker) {
        List<TuplePatternElementContext> tuplePatternElementContexts =
                tuplePatternContext.tuplePatternElementList().tuplePatternElement();

        for (TuplePatternElementContext tuplePatternElement : tuplePatternElementContexts) {
            evaluatePattern(tuplePatternElement.pattern(), walker);
        }
    }

    void verifyRedundantExpressionParenthesis(String constructType, ExpressionContext ctx) {
        if (ctx == null
                || ctx.getChildCount() != 1
                || ctx.prefixExpression() == null
                || ctx.prefixExpression().prefixOperator() != null // flag cases with trailing ;
                || ctx.prefixExpression().postfixExpression() == null
                || ctx.prefixExpression().postfixExpression().getChildCount() != 1) {
            return;
        }

        PostfixExpressionContext postfixExpression = ctx.prefixExpression().postfixExpression();

        if (!(postfixExpression.getChild(0) instanceof PrimaryExpressionContext)) {
            return;
        }

        PrimaryExpressionContext primaryExpression = (PrimaryExpressionContext) postfixExpression.getChild(0);

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

        printRedundantParenthesisWarning(ctx, constructType + Messages.ENCLOSED_PARENTHESIS);
    }

    void verifyRedundantForLoopParenthesis(ParserRuleContext ctx) {
        if (!(ctx.getChild(1) instanceof TerminalNodeImpl)) {
            return;
        } // return if '(' not present

        Token openParenthesisToken = ((TerminalNodeImpl) ctx.getChild(1)).getSymbol();
        char firstCharacter = openParenthesisToken.getText().charAt(0);

        if (firstCharacter == '(') {
            Location startLocation = new Location(openParenthesisToken.getLine(),
                    openParenthesisToken.getCharPositionInLine() + 1);
            this.printer.warn(Messages.FOR_LOOP + Messages.ENCLOSED_PARENTHESIS, startLocation);
        }
    }

    public void verifyRedundantCatchParentheses(ParserRuleContext ctx) {
        if (ctx == null) {
            return;
        }
        String pattern = ctx.getText();
        char firstCharacter = pattern.charAt(0);
        char lastCharacter = pattern.charAt(pattern.length() - 1);

        if (firstCharacter == '(' && lastCharacter == ')') {
            printRedundantParenthesisWarning(ctx, Messages.CATCH_CLAUSE + Messages.ENCLOSED_PARENTHESIS);
        }
    }

    private void printRedundantParenthesisWarning(ParserRuleContext ctx, String firstParenthesisMsg) {
        Location startLocation = new Location(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1);
        this.printer.warn(firstParenthesisMsg, startLocation);
    }

    /* Optional Binding Condition Evaluators */

    public void evaluateOptionalBindingHead(OptionalBindingHeadContext ctx) {
        ParseTreeWalker walker = new ParseTreeWalker();
        evaluatePattern(ctx.pattern(), walker);
    }

    public void evaluateOptionalBindingContinuation(OptionalBindingContinuationContext ctx) {
        if (ctx.optionalBindingHead() != null) {
            evaluateOptionalBindingHead(ctx.optionalBindingHead());
        } else {
            ParseTreeWalker walker = new ParseTreeWalker();
            evaluatePattern(ctx.pattern(), walker);
        }
    }

    public String letOrVar(OptionalBindingHeadContext ctx) {
        return ctx.getChild(0).getText();
    }
}
