package com.sleekbyte.tailor.listeners;

import static com.sleekbyte.tailor.antlr.SwiftParser.ClassDeclarationContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.ElseClauseContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.ExpressionContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.ForInStatementContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.FunctionDeclarationContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.IfStatementContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.ImportDeclarationContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.InitializerDeclarationContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.OptionalBindingContinuationContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.OptionalBindingHeadContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.ParenthesizedExpressionContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.PatternContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.PostfixExpressionContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.PrimaryExpressionContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.RepeatWhileStatementContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.StructDeclarationContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.SwitchStatementContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.TuplePatternContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.TuplePatternElementContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.TypeInheritanceClauseContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.WhileStatementContext;

import com.sleekbyte.tailor.antlr.SwiftParser.CodeBlockContext;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.CharFormatUtil;
import com.sleekbyte.tailor.utils.SourceFileUtil;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Helper class for {@link MainListener}.
 */
class MainListenerHelper {

    private Set<Integer> importLineNumbers = new HashSet<>();
    private Printer printer;

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    public static Location getContextStartLocation(ParserRuleContext ctx) {
        return new Location(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1);
    }

    public static Location getContextStopLocation(ParserRuleContext ctx) {
        return new Location(ctx.getStop().getLine(), ctx.getStop().getCharPositionInLine() + 1);
    }

    public static Location getTokenLocation(Token token) {
        return new Location(token.getLine(), token.getCharPositionInLine() + 1);
    }

    public void verifyUpperCamelCase(String constructType, ParserRuleContext ctx) {
        String constructName = ctx.getText();
        if (!CharFormatUtil.isUpperCamelCase(constructName)) {
            Location location = getContextStartLocation(ctx);
            this.printer.error(constructType + Messages.UPPER_CAMEL_CASE, location);
        }
    }

    public void verifyNotSemicolonTerminated(String constructType, ParserRuleContext ctx) {
        String construct = ctx.getText();
        if (construct.endsWith(";")) {
            Location location = getContextStopLocation(ctx);
            this.printer.error(constructType + Messages.SEMICOLON, location);
        }
    }

    public void verifyConstructLength(String constructType, int maxLength, ParserRuleContext ctx) {
        if (SourceFileUtil.constructTooLong(ctx, maxLength)) {
            int constructLength = ctx.getStop().getLine() - ctx.getStart().getLine();
            String lengthVersusLimit = " (" + constructLength + "/" + maxLength + ")";
            Location location = getContextStartLocation(ctx);
            this.printer.error(constructType + Messages.EXCEEDS_LINE_LIMIT + lengthVersusLimit, location);
        }
    }

    public void verifyNameLength(String constructType, int maxLength, ParserRuleContext ctx) {
        if (SourceFileUtil.nameTooLong(ctx, maxLength)) {
            String lengthVersusLimit = " (" + ctx.getText().length() + "/" + maxLength + ")";
            Location location = getContextStartLocation(ctx);
            this.printer.error(constructType + Messages.EXCEEDS_CHARACTER_LIMIT + lengthVersusLimit, location);
        }
    }

    public void verifyMultipleImports(ImportDeclarationContext ctx) {
        int lineNum = ctx.getStart().getLine();
        if (importLineNumbers.contains(lineNum)) {
            Location location = new Location(lineNum);
            this.printer.warn(Messages.IMPORTS + Messages.MULTIPLE_IMPORTS, location);
        } else {
            importLineNumbers.add(lineNum);
        }
    }

    public void walkConstantDecListener(ParseTreeWalker walker, ParserRuleContext tree) {
        walker.walk(new ConstantDecListener(this.printer), tree);
    }

    public void evaluatePattern(PatternContext pattern, ParseTreeWalker walker) {
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

    public void evaluateTuplePattern(TuplePatternContext tuplePatternContext, ParseTreeWalker walker) {
        List<TuplePatternElementContext> tuplePatternElementContexts =
            tuplePatternContext.tuplePatternElementList().tuplePatternElement();

        for (TuplePatternElementContext tuplePatternElement : tuplePatternElementContexts) {
            evaluatePattern(tuplePatternElement.pattern(), walker);
        }
    }

    public void verifyRedundantExpressionParenthesis(String constructType, ExpressionContext ctx) {
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

    public void verifyRedundantForLoopParenthesis(ParserRuleContext ctx) {
        if (!(ctx.getChild(1) instanceof TerminalNodeImpl)) {
            return;
        } // return if '(' not present

        Token openParenthesisToken = ((TerminalNodeImpl) ctx.getChild(1)).getSymbol();
        char firstCharacter = openParenthesisToken.getText().charAt(0);

        if (firstCharacter == '(') {
            Location startLocation = getTokenLocation(openParenthesisToken);
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
        Location startLocation = getContextStartLocation(ctx);
        this.printer.warn(firstParenthesisMsg, startLocation);
    }

    public void verifySwitchStatementBracketStyle(SwitchStatementContext ctx) {
        Location switchExpLocation = getTokenLocation(ctx.expression().getStop());
        Token openBraceToken = ((TerminalNodeImpl) ctx.getChild(2)).getSymbol();
        Location openBraceLocation = getTokenLocation(openBraceToken);

        if (switchExpLocation.line != openBraceLocation.line) {
            this.printer.warn(Messages.SWITCH_STATEMENT + Messages.BRACKET_STYLE, openBraceLocation);
        }
    }

    private void verifyCodeBlockBracketStyle(String constructName, Location constructLocation,
                                     CodeBlockContext codeBlockCtx) {
        Token openBraceToken = ((TerminalNodeImpl) codeBlockCtx.getChild(0)).getSymbol();
        Location openBraceLocation = getTokenLocation(openBraceToken);

        if (constructLocation.line != openBraceLocation.line) {
            this.printer.warn(constructName + Messages.BRACKET_STYLE, openBraceLocation);
        }
    }

    public void verifyForInStatementBrackets(ForInStatementContext ctx) {
        Location expressionLocation = MainListenerHelper.getContextStopLocation(ctx.expression());
        verifyCodeBlockBracketStyle(Messages.FOR_IN_LOOP, expressionLocation, ctx.codeBlock());
    }

    public void verifyInitializerBrackets(InitializerDeclarationContext ctx) {
        Location parameterClauseLocation = MainListenerHelper.getContextStopLocation(ctx.parameterClause());
        verifyCodeBlockBracketStyle(Messages.INITIALIZER_BODY, parameterClauseLocation,
                                           ctx.initializerBody().codeBlock());
    }

    public void verifyRepeatWhileLoopBrackets(RepeatWhileStatementContext ctx) {
        Location repeatClause = MainListenerHelper.getContextStartLocation(ctx);
        verifyCodeBlockBracketStyle(Messages.REPEAT_WHILE_STATEMENT, repeatClause, ctx.codeBlock());
    }

    public void verifyWhileLoopBrackets(WhileStatementContext ctx) {
        Location conditionClauseLocation = MainListenerHelper.getContextStopLocation(ctx.conditionClause());
        verifyCodeBlockBracketStyle(Messages.WHILE_STATEMENT, conditionClauseLocation, ctx.codeBlock());
    }

    public void verifyIfStatementBrackets(IfStatementContext ctx) {
        Location conditionClauseLocation = MainListenerHelper.getContextStopLocation(ctx.conditionClause());
        verifyCodeBlockBracketStyle(Messages.IF_STATEMENT, conditionClauseLocation, ctx.codeBlock());
    }

    public void verifyElseClauseBrackets(ElseClauseContext ctx) {
        if (ctx.codeBlock() == null) {
            return;
        }
        Location elseClauseLocation = MainListenerHelper.getContextStartLocation(ctx);
        verifyCodeBlockBracketStyle(Messages.ELSE_CLAUSE, elseClauseLocation, ctx.codeBlock());
    }

    public void verifyFunctionBrackets(FunctionDeclarationContext ctx) {
        Location functionDeclarationLocation = MainListenerHelper.getContextStopLocation(ctx.functionSignature());
        verifyCodeBlockBracketStyle(Messages.FUNCTION, functionDeclarationLocation, ctx.functionBody().codeBlock());
    }

    public void verifyClassBrackets(ClassDeclarationContext ctx) {
        TypeInheritanceClauseContext typeInheritanceClauseContext = ctx.typeInheritanceClause();
        Location classLocation;
        if (typeInheritanceClauseContext != null) {
            classLocation = getContextStopLocation(typeInheritanceClauseContext);
        } else {
            classLocation = getContextStopLocation(ctx.className());
        }

        Token openBraceToken = ((TerminalNodeImpl) ctx.classBody().getChild(0)).getSymbol();
        Location openBraceLocation = getTokenLocation(openBraceToken);

        if (classLocation.line != openBraceLocation.line) {
            this.printer.warn(Messages.CLASS + Messages.BRACKET_STYLE, openBraceLocation);
        }
    }

    public void verifyStructBrackets(StructDeclarationContext ctx) {
        TypeInheritanceClauseContext typeInheritanceClauseContext = ctx.typeInheritanceClause();
        Location classLocation;
        if (typeInheritanceClauseContext != null) {
            classLocation = getContextStopLocation(typeInheritanceClauseContext);
        } else {
            classLocation = getContextStopLocation(ctx.structName());
        }

        Token openBraceToken = ((TerminalNodeImpl) ctx.structBody().getChild(0)).getSymbol();
        Location openBraceLocation = getTokenLocation(openBraceToken);

        if (classLocation.line != openBraceLocation.line) {
            this.printer.warn(Messages.STRUCT + Messages.BRACKET_STYLE, openBraceLocation);
        }
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
