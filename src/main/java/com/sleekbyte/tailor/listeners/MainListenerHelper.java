package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.CharFormatUtil;
import com.sleekbyte.tailor.utils.SourceFileUtil;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import javax.swing.text.html.parser.Parser;
import java.util.List;
import java.util.Optional;

/**
 * Helper class for {@link MainListener}
 */
class MainListenerHelper {

    private Printer printer;

    MainListenerHelper(Printer printer) {
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

    void walkConstantDecListener(ParseTreeWalker walker, ParserRuleContext tree) {
        walker.walk(new ConstantDecListener(this.printer), tree);
    }

    void evaluatePattern(SwiftParser.PatternContext pattern, ParseTreeWalker walker) {
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

    void evaluateTuplePattern(SwiftParser.TuplePatternContext tuplePatternContext, ParseTreeWalker walker) {
        List<SwiftParser.TuplePatternElementContext> tuplePatternElementContexts =
            tuplePatternContext.tuplePatternElementList().tuplePatternElement();

        for (SwiftParser.TuplePatternElementContext tuplePatternElement : tuplePatternElementContexts) {
            evaluatePattern(tuplePatternElement.pattern(), walker);
        }
    }

    void verifyRedundantParentheses(String constructType, ParserRuleContext ctx) {
        char firstCharacter = '\0', lastCharacter = '\0';
        Location startLocation = null, endLocation = null;

        if (ctx instanceof SwiftParser.ConditionClauseContext || ctx instanceof SwiftParser.ExpressionContext) {
            String conditionalClause = ctx.getText();
            firstCharacter = conditionalClause.charAt(0); // extract '('
            lastCharacter = conditionalClause.charAt(conditionalClause.length() - 1); // extract ')'
            startLocation = new Location(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1);
            endLocation = new Location(ctx.getStop().getLine(),  ctx.getStop().getCharPositionInLine() + 1);
            
        } else if (ctx instanceof SwiftParser.ForStatementContext) {
            if (!(ctx.getChild(1) instanceof TerminalNodeImpl)) { return; } // return if '(' not present
            Token openParenthesisToken = ((TerminalNodeImpl) ctx.getChild(1)).getSymbol();
            firstCharacter = openParenthesisToken.getText().charAt(0); // extract '(' token
            startLocation = new Location(openParenthesisToken.getLine(), openParenthesisToken.getCharPositionInLine());
        }

        if (firstCharacter == '(') {
            this.printer.warn(constructType + Messages.STARTS_WITH_PARENTHESIS, startLocation);
        }
        if (lastCharacter == ')') {
            this.printer.warn(constructType + Messages.ENDS_WITH_PARENTHESIS, endLocation);
        }
    }

    /* Optional Binding Condition Evaluators */

    public void evaluateOptionalBindingHead(SwiftParser.OptionalBindingHeadContext ctx) {
        ParseTreeWalker walker = new ParseTreeWalker();
        evaluatePattern(ctx.pattern(), walker);
    }

    public void evaluateOptionalBindingContinuation(SwiftParser.OptionalBindingContinuationContext ctx) {
        if (ctx.optionalBindingHead() != null) {
            evaluateOptionalBindingHead(ctx.optionalBindingHead());
        } else {
            ParseTreeWalker walker = new ParseTreeWalker();
            evaluatePattern(ctx.pattern(), walker);
        }
    }

    public String letOrVar(SwiftParser.OptionalBindingHeadContext ctx) {
        return ctx.getChild(0).getText();
    }

}
