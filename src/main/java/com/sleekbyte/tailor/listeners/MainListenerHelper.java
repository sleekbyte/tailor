package com.sleekbyte.tailor.listeners;

import static com.sleekbyte.tailor.antlr.SwiftParser.ClassDeclarationContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.ElseClauseContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.ExpressionContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.ForInStatementContext;
import static com.sleekbyte.tailor.antlr.SwiftParser.ForStatementContext;
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

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
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

    //region Utils
    Location getContextStartLocation(ParserRuleContext ctx) {
        return new Location(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1);
    }

    Location getContextStopLocation(ParserRuleContext ctx) {
        return new Location(ctx.getStop().getLine(), ctx.getStop().getCharPositionInLine() + 1);
    }

    Location getTokenLocation(Token token) {
        return new Location(token.getLine(), token.getCharPositionInLine() + 1);
    }
    //endregion

    //region UpperCamelCase name check
    void verifyUpperCamelCase(String constructType, ParserRuleContext ctx) {
        String constructName = ctx.getText();
        if (!CharFormatUtil.isUpperCamelCase(constructName)) {
            Location location = getContextStartLocation(ctx);
            this.printer.error(constructType + Messages.UPPER_CAMEL_CASE, location);
        }
    }
    //endregion

    //region Semicolon terminated statement check
    void verifyNotSemicolonTerminated(String constructType, ParserRuleContext ctx) {
        String construct = ctx.getText();
        if (construct.endsWith(";")) {
            Location location = getContextStopLocation(ctx);
            this.printer.error(constructType + Messages.SEMICOLON, location);
        }
    }
    //endregion

    //region Length checks
    void verifyConstructLength(String constructType, int maxLength, ParserRuleContext ctx) {
        if (SourceFileUtil.constructTooLong(ctx, maxLength)) {
            int constructLength = ctx.getStop().getLine() - ctx.getStart().getLine();
            String lengthVersusLimit = " (" + constructLength + "/" + maxLength + ")";
            Location location = getContextStartLocation(ctx);
            this.printer.error(constructType + Messages.EXCEEDS_LINE_LIMIT + lengthVersusLimit, location);
        }
    }

    void verifyNameLength(String constructType, int maxLength, ParserRuleContext ctx) {
        if (SourceFileUtil.nameTooLong(ctx, maxLength)) {
            String lengthVersusLimit = " (" + ctx.getText().length() + "/" + maxLength + ")";
            Location location = getContextStartLocation(ctx);
            this.printer.error(constructType + Messages.EXCEEDS_CHARACTER_LIMIT + lengthVersusLimit, location);
        }
    }
    //endregion

    //region Lowercamelcase check
    void verifyLowerCamelCase(String constructType, ParserRuleContext ctx) {
        String constructName = ctx.getText();
        if (!CharFormatUtil.isLowerCamelCase(constructName)) {
            Location location = getContextStartLocation(ctx);
            this.printer.error(constructType + Messages.LOWER_CAMEL_CASE, location);
        }
    }
    //endregion

    //region Multiple import check
    void verifyMultipleImports(ImportDeclarationContext ctx) {

        int lineNum = ctx.getStart().getLine();
        if (importLineNumbers.contains(lineNum)) {
            Location location = new Location(lineNum);
            this.printer.warn(Messages.IMPORTS + Messages.MULTIPLE_IMPORTS, location);
        } else {
            importLineNumbers.add(lineNum);
        }
    }
    //endregion

    //region Tuple pattern evaluation
    void walkListener(ParseTreeWalker walker, ParserRuleContext tree, SwiftBaseListener listener) {
        walker.walk(listener, tree);
    }

    void evaluatePattern(PatternContext pattern, ParseTreeWalker walker, SwiftBaseListener listener) {
        if (pattern.identifierPattern() != null) {
            walkListener(walker, pattern.identifierPattern(), listener);

        } else if (pattern.tuplePattern() != null && pattern.tuplePattern().tuplePatternElementList() != null) {
            evaluateTuplePattern(pattern.tuplePattern(), walker, listener);

        } else if (pattern.enumCasePattern() != null && pattern.enumCasePattern().tuplePattern() != null) {
            evaluateTuplePattern(pattern.enumCasePattern().tuplePattern(), walker, listener);

        } else if (pattern.pattern() != null) {
            evaluatePattern(pattern.pattern(), walker, listener);

        } else if (pattern.expressionPattern() != null) {
            walkListener(walker, pattern.expressionPattern().expression().prefixExpression(), listener);
        }
    }

    void evaluateTuplePattern(TuplePatternContext tuplePatternContext, ParseTreeWalker walker,
                              SwiftBaseListener listener) {
        List<TuplePatternElementContext> tuplePatternElementContexts =
            tuplePatternContext.tuplePatternElementList().tuplePatternElement();

        for (TuplePatternElementContext tuplePatternElement : tuplePatternElementContexts) {
            evaluatePattern(tuplePatternElement.pattern(), walker, listener);
        }
    }
    //endregion

    //region Parenthesis check
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
            Location startLocation = getTokenLocation(openParenthesisToken);
            this.printer.warn(Messages.FOR_LOOP + Messages.ENCLOSED_PARENTHESIS, startLocation);
        }
    }

    void verifyRedundantCatchParentheses(ParserRuleContext ctx) {
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
    //endregion

    //region Bracket style check
    void verifySwitchStatementOpenBraceStyle(SwitchStatementContext ctx) {
        Location switchExpLocation = getTokenLocation(ctx.expression().getStop());
        Token openBraceToken = ((TerminalNodeImpl) ctx.getChild(2)).getSymbol();
        Location openBraceLocation = getTokenLocation(openBraceToken);

        if (switchExpLocation.line != openBraceLocation.line) {
            this.printer.warn(Messages.SWITCH_STATEMENT + Messages.BRACKET_STYLE, openBraceLocation);
        }
    }

    private void verifyCodeBlockOpenBraceStyle(String constructName, Location constructLocation,
                                               ParserRuleContext codeBlockCtx) {
        Token openBraceToken = ((TerminalNodeImpl) codeBlockCtx.getChild(0)).getSymbol();
        Location openBraceLocation = getTokenLocation(openBraceToken);

        if (constructLocation.line != openBraceLocation.line) {
            this.printer.warn(constructName + Messages.BRACKET_STYLE, openBraceLocation);
        }
    }

    void verifyForInStatementOpenBraceStyle(ForInStatementContext ctx) {
        Location expressionLocation = getContextStopLocation(ctx.expression());
        verifyCodeBlockOpenBraceStyle(Messages.FOR_IN_LOOP, expressionLocation, ctx.codeBlock());
    }

    void verifyInitializerOpenBraceStyle(InitializerDeclarationContext ctx) {
        Location parameterClauseLocation = getContextStopLocation(ctx.parameterClause());
        verifyCodeBlockOpenBraceStyle(Messages.INITIALIZER_BODY, parameterClauseLocation,
                                             ctx.initializerBody().codeBlock());
    }

    void verifyRepeatWhileLoopOpenBraceStyle(RepeatWhileStatementContext ctx) {
        Location repeatClause = getContextStartLocation(ctx);
        verifyCodeBlockOpenBraceStyle(Messages.REPEAT_WHILE_STATEMENT, repeatClause, ctx.codeBlock());
    }

    void verifyWhileLoopOpenBraceStyle(WhileStatementContext ctx) {
        Location conditionClauseLocation = getContextStopLocation(ctx.conditionClause());
        verifyCodeBlockOpenBraceStyle(Messages.WHILE_STATEMENT, conditionClauseLocation, ctx.codeBlock());
    }

    void verifyIfStatementOpenBraceStyle(IfStatementContext ctx) {
        Location conditionClauseLocation = getContextStopLocation(ctx.conditionClause());
        verifyCodeBlockOpenBraceStyle(Messages.IF_STATEMENT, conditionClauseLocation, ctx.codeBlock());
    }

    void verifyElseClauseOpenBraceStyle(ElseClauseContext ctx) {
        if (ctx.codeBlock() == null) {
            return;
        }
        Location elseClauseLocation = getContextStartLocation(ctx);
        verifyCodeBlockOpenBraceStyle(Messages.ELSE_CLAUSE, elseClauseLocation, ctx.codeBlock());
    }

    void verifyFunctionOpenBraceStyle(FunctionDeclarationContext ctx) {
        Location functionDeclarationLocation = getContextStopLocation(ctx.functionSignature());
        verifyCodeBlockOpenBraceStyle(Messages.FUNCTION, functionDeclarationLocation, ctx.functionBody().codeBlock());
    }

    void verifyClassOpenBraceStyle(ClassDeclarationContext ctx) {
        TypeInheritanceClauseContext typeInheritanceClauseContext = ctx.typeInheritanceClause();
        Location classLocation;
        if (typeInheritanceClauseContext != null) {
            classLocation = getContextStopLocation(typeInheritanceClauseContext);
        } else {
            classLocation = getContextStopLocation(ctx.className());
        }

        verifyCodeBlockOpenBraceStyle(Messages.CLASS, classLocation, ctx.classBody());
    }

    void verifyStructOpenBraceStyle(StructDeclarationContext ctx) {
        TypeInheritanceClauseContext typeInheritanceClauseContext = ctx.typeInheritanceClause();
        Location classLocation;
        if (typeInheritanceClauseContext != null) {
            classLocation = getContextStopLocation(typeInheritanceClauseContext);
        } else {
            classLocation = getContextStopLocation(ctx.structName());
        }

        verifyCodeBlockOpenBraceStyle(Messages.STRUCT, classLocation, ctx.structBody());
    }

    void verifyForLoopOpenBraceStyle(ForStatementContext ctx) {
        int numChildren = ctx.getChildCount();
        Location loopEndLocation;

        // object at [numChildren - 1] index is codeBlock
        // object at [numChildren - 2 index] is either an expression or ';'
        Object constructBeforeOpenBrace = ctx.getChild(numChildren - 2);
        if (constructBeforeOpenBrace instanceof TerminalNodeImpl) {
            Token semicolon = ((TerminalNodeImpl) constructBeforeOpenBrace).getSymbol();
            loopEndLocation = getTokenLocation(semicolon);
        } else {
            ExpressionContext expressionContext = (ExpressionContext) constructBeforeOpenBrace;
            loopEndLocation = getContextStopLocation(expressionContext);
        }
        verifyCodeBlockOpenBraceStyle(Messages.FOR_LOOP, loopEndLocation, ctx.codeBlock());
    }
    //endregion

    //region Optional binding condition evaluators
    void evaluateOptionalBindingHead(OptionalBindingHeadContext ctx, SwiftBaseListener listener) {
        ParseTreeWalker walker = new ParseTreeWalker();
        evaluatePattern(ctx.pattern(), walker, listener);
    }

    void evaluateOptionalBindingContinuation(OptionalBindingContinuationContext ctx,
                                                    SwiftBaseListener listener) {
        if (ctx.optionalBindingHead() != null) {
            evaluateOptionalBindingHead(ctx.optionalBindingHead(), listener);
        } else {
            ParseTreeWalker walker = new ParseTreeWalker();
            evaluatePattern(ctx.pattern(), walker, listener);
        }
    }

    String letOrVar(OptionalBindingHeadContext ctx) {
        return ctx.getChild(0).getText();
    }
    //endregion
}
