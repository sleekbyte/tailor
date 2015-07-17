package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser.ClassBodyContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ClosureExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.DictionaryLiteralItemContext;
import com.sleekbyte.tailor.antlr.SwiftParser.DictionaryTypeContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ElseClauseContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ExpressionElementListContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ExtensionBodyContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ForInStatementContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ForStatementContext;
import com.sleekbyte.tailor.antlr.SwiftParser.FunctionDeclarationContext;
import com.sleekbyte.tailor.antlr.SwiftParser.GetterClauseContext;
import com.sleekbyte.tailor.antlr.SwiftParser.IfStatementContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ImportDeclarationContext;
import com.sleekbyte.tailor.antlr.SwiftParser.InitializerDeclarationContext;
import com.sleekbyte.tailor.antlr.SwiftParser.OperatorContext;
import com.sleekbyte.tailor.antlr.SwiftParser.OperatorDeclarationContext;
import com.sleekbyte.tailor.antlr.SwiftParser.OptionalBindingContinuationContext;
import com.sleekbyte.tailor.antlr.SwiftParser.OptionalBindingHeadContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ParenthesizedExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.PatternContext;
import com.sleekbyte.tailor.antlr.SwiftParser.PatternInitializerContext;
import com.sleekbyte.tailor.antlr.SwiftParser.PatternInitializerListContext;
import com.sleekbyte.tailor.antlr.SwiftParser.PostfixExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.PrimaryExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ProtocolBodyContext;
import com.sleekbyte.tailor.antlr.SwiftParser.RepeatWhileStatementContext;
import com.sleekbyte.tailor.antlr.SwiftParser.SetterClauseContext;
import com.sleekbyte.tailor.antlr.SwiftParser.StatementsContext;
import com.sleekbyte.tailor.antlr.SwiftParser.StructBodyContext;
import com.sleekbyte.tailor.antlr.SwiftParser.SwitchCaseContext;
import com.sleekbyte.tailor.antlr.SwiftParser.SwitchStatementContext;
import com.sleekbyte.tailor.antlr.SwiftParser.TuplePatternContext;
import com.sleekbyte.tailor.antlr.SwiftParser.TuplePatternElementContext;
import com.sleekbyte.tailor.antlr.SwiftParser.TypeAnnotationContext;
import com.sleekbyte.tailor.antlr.SwiftParser.WhileStatementContext;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.CharFormatUtil;
import com.sleekbyte.tailor.utils.ListenerUtil;
import com.sleekbyte.tailor.utils.ParseTreeUtil;
import com.sleekbyte.tailor.utils.SourceFileUtil;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
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

    //region UpperCamelCase name check
    void verifyUpperCamelCase(String constructType, ParserRuleContext ctx) {
        String constructName = ctx.getText();
        if (!CharFormatUtil.isUpperCamelCase(constructName)) {
            Location location = ListenerUtil.getContextStartLocation(ctx);
            this.printer.error(constructType + Messages.UPPER_CAMEL_CASE, location);
        }
    }
    //endregion

    //region Semicolon terminated statement check
    void verifyNotSemicolonTerminated(String constructType, ParserRuleContext ctx) {
        String construct = ctx.getText();
        if (construct.endsWith(";")) {
            Location location = ListenerUtil.getContextStopLocation(ctx);
            this.printer.error(constructType + Messages.SEMICOLON, location);
        }
    }
    //endregion

    //region Length checks
    void verifyConstructLength(String constructType, int maxLength, ParserRuleContext ctx) {
        if (SourceFileUtil.constructTooLong(ctx, maxLength)) {
            int constructLength = ctx.getStop().getLine() - ctx.getStart().getLine();
            String lengthVersusLimit = " (" + constructLength + "/" + maxLength + ")";
            Location location = ListenerUtil.getContextStartLocation(ctx);
            this.printer.error(constructType + Messages.EXCEEDS_LINE_LIMIT + lengthVersusLimit, location);
        }
    }

    void verifyNameLength(String constructType, int maxLength, ParserRuleContext ctx) {
        if (SourceFileUtil.nameTooLong(ctx, maxLength)) {
            String lengthVersusLimit = " (" + ctx.getText().length() + "/" + maxLength + ")";
            Location location = ListenerUtil.getContextStartLocation(ctx);
            this.printer.error(constructType + Messages.EXCEEDS_CHARACTER_LIMIT + lengthVersusLimit, location);
        }
    }
    //endregion

    //region Lowercamelcase check
    void verifyLowerCamelCase(String constructType, ParserRuleContext ctx) {
        String constructName = ctx.getText();
        if (!CharFormatUtil.isLowerCamelCase(constructName)) {
            Location location = ListenerUtil.getContextStartLocation(ctx);
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

    void evaluatePatternInitializerList(PatternInitializerListContext ctx, SwiftBaseListener listener) {
        ParseTreeWalker walker = new ParseTreeWalker();
        for (PatternInitializerContext context : ctx.patternInitializer()) {
            PatternContext pattern = context.pattern();
            evaluatePattern(pattern, walker, listener);
        }
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
            Location startLocation = ListenerUtil.getTokenLocation(openParenthesisToken);
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
        Location startLocation = ListenerUtil.getContextStartLocation(ctx);
        this.printer.warn(firstParenthesisMsg, startLocation);
    }
    //endregion

    //region Open brace style check
    void verifySwitchStatementOpenBraceStyle(SwitchStatementContext ctx) {
        Location switchExpLocation = ListenerUtil.getTokenLocation(ctx.expression().getStop());
        Location openBraceLocation = ListenerUtil.getLocationOfChildToken(ctx, 2);

        if (switchExpLocation.line != openBraceLocation.line) {
            this.printer.warn(Messages.SWITCH_STATEMENT + Messages.BRACKET_STYLE, openBraceLocation);
        }
    }

    private void verifyCodeBlockOpenBraceStyle(String constructName, Location constructLocation,
                                               ParserRuleContext codeBlockCtx) {
        Location openBraceLocation = ListenerUtil.getLocationOfChildToken(codeBlockCtx, 0);
        if (constructLocation.line != openBraceLocation.line) {
            this.printer.warn(constructName + Messages.BRACKET_STYLE, openBraceLocation);
        }
    }

    void verifyForInStatementOpenBraceStyle(ForInStatementContext ctx) {
        Location expressionLocation = ListenerUtil.getContextStopLocation(ctx.expression());
        verifyCodeBlockOpenBraceStyle(Messages.FOR_IN_LOOP, expressionLocation, ctx.codeBlock());
    }

    void verifyInitializerOpenBraceStyle(InitializerDeclarationContext ctx) {
        Location parameterClauseLocation = ListenerUtil.getContextStopLocation(ctx.parameterClause());
        verifyCodeBlockOpenBraceStyle(Messages.INITIALIZER_BODY, parameterClauseLocation,
            ctx.initializerBody().codeBlock());
    }

    void verifyRepeatWhileLoopOpenBraceStyle(RepeatWhileStatementContext ctx) {
        Location repeatClause = ListenerUtil.getContextStartLocation(ctx);
        verifyCodeBlockOpenBraceStyle(Messages.REPEAT_WHILE_STATEMENT, repeatClause, ctx.codeBlock());
    }

    void verifyWhileLoopOpenBraceStyle(WhileStatementContext ctx) {
        Location conditionClauseLocation = ListenerUtil.getContextStopLocation(ctx.conditionClause());
        verifyCodeBlockOpenBraceStyle(Messages.WHILE_STATEMENT, conditionClauseLocation, ctx.codeBlock());
    }

    void verifyIfStatementOpenBraceStyle(IfStatementContext ctx) {
        Location conditionClauseLocation = ListenerUtil.getContextStopLocation(ctx.conditionClause());
        verifyCodeBlockOpenBraceStyle(Messages.IF_STATEMENT, conditionClauseLocation, ctx.codeBlock());
    }

    void verifyElseClauseOpenBraceStyle(ElseClauseContext ctx) {
        if (ctx.codeBlock() == null) {
            return;
        }
        Location elseClauseLocation = ListenerUtil.getContextStartLocation(ctx);
        verifyCodeBlockOpenBraceStyle(Messages.ELSE_CLAUSE, elseClauseLocation, ctx.codeBlock());
    }

    void verifyFunctionOpenBraceStyle(FunctionDeclarationContext ctx) {
        Location functionDeclarationLocation = ListenerUtil.getContextStopLocation(ctx.functionSignature());
        verifyCodeBlockOpenBraceStyle(Messages.FUNCTION, functionDeclarationLocation, ctx.functionBody().codeBlock());
    }

    void verifyClassOpenBraceStyle(ClassBodyContext ctx) {
        verifyBodyOpenBraceStyle(ctx, Messages.CLASS);
    }

    void verifyStructOpenBraceStyle(StructBodyContext ctx) {
        verifyBodyOpenBraceStyle(ctx, Messages.STRUCT);
    }

    void verifyForLoopOpenBraceStyle(ForStatementContext ctx) {
        int numChildren = ctx.getChildCount();
        Location loopEndLocation;

        // object at [numChildren - 1] index is codeBlock
        // object at [numChildren - 2] index is either an expression or ';'
        ParseTree constructBeforeOpenBrace = ctx.getChild(numChildren - 2);
        if (constructBeforeOpenBrace instanceof TerminalNodeImpl) {
            Token semicolon = ((TerminalNodeImpl) constructBeforeOpenBrace).getSymbol();
            loopEndLocation = ListenerUtil.getTokenLocation(semicolon);
        } else {
            ExpressionContext expressionContext = (ExpressionContext) constructBeforeOpenBrace;
            loopEndLocation = ListenerUtil.getContextStopLocation(expressionContext);
        }
        verifyCodeBlockOpenBraceStyle(Messages.FOR_LOOP, loopEndLocation, ctx.codeBlock());
    }

    void verifyProtocolOpenBraceStyle(ProtocolBodyContext ctx) {
        verifyBodyOpenBraceStyle(ctx, Messages.PROTOCOL);
    }

    private void verifyBodyOpenBraceStyle(ParserRuleContext ctx, String constructName) {
        ParserRuleContext leftSibling = (ParserRuleContext) ParseTreeUtil.getLeftSibling(ctx);
        Location constructLocation = ListenerUtil.getContextStopLocation(leftSibling);
        verifyCodeBlockOpenBraceStyle(constructName, constructLocation, ctx);
    }

    void verifyEnumOpenBraceStyle(ParserRuleContext ctx) {
        for (ParseTree child : ctx.children) {
            if (child instanceof TerminalNodeImpl) {
                Token openBrace = ((TerminalNodeImpl) child).getSymbol();
                Location openBraceLocation = ListenerUtil.getTokenLocation(openBrace);
                ParserRuleContext leftSibling = (ParserRuleContext) ParseTreeUtil.getLeftSibling(child);
                Location leftSiblingLocation = ListenerUtil.getContextStopLocation(leftSibling);
                if (openBraceLocation.line != leftSiblingLocation.line) {
                    this.printer.warn(Messages.ENUM + Messages.BRACKET_STYLE, openBraceLocation);
                }
                break;
            }
        }
    }

    void verifyClosureExpressionOpenBraceStyle(ClosureExpressionContext ctx) {
        ParseTree sixthAncestor = ParseTreeUtil.getNthParent(ctx, 6);

        if (sixthAncestor == null || !(sixthAncestor instanceof ExpressionElementListContext)) {
            ParserRuleContext leftSibling = (ParserRuleContext) ParseTreeUtil.getLeftSibling(ctx);
            if (leftSibling != null) {
                Location leftSiblingLocation = ListenerUtil.getContextStopLocation(leftSibling);
                verifyCodeBlockOpenBraceStyle(Messages.CLOSURE, leftSiblingLocation, ctx);
            }
            return;
        }

        ExpressionElementListContext expressionElementListContext = (ExpressionElementListContext) sixthAncestor;

        ParseTree expElementListLeftSibling = ParseTreeUtil.getLeftSibling(expressionElementListContext);

        if (expElementListLeftSibling == null) {
            return;
        }

        Location expElementLeftSiblingLocation;

        if (expElementListLeftSibling instanceof TerminalNodeImpl) {
            Token leftToken = ((TerminalNodeImpl) expElementListLeftSibling).getSymbol();
            expElementLeftSiblingLocation = ListenerUtil.getTokenLocation(leftToken);
        } else {
            ParserRuleContext leftContext = (ParserRuleContext) expElementListLeftSibling;
            expElementLeftSiblingLocation = ListenerUtil.getContextStopLocation(leftContext);
        }

        verifyCodeBlockOpenBraceStyle(Messages.CLOSURE, expElementLeftSiblingLocation, ctx);
    }

    void verifyExtensionOpenBraceStyle(ExtensionBodyContext ctx) {
        verifyBodyOpenBraceStyle(ctx, Messages.EXTENSION);
    }

    void verifyGetterOpenBraceStyle(GetterClauseContext ctx) {
        TerminalNodeImpl get = (TerminalNodeImpl) ParseTreeUtil.getLeftSibling(ctx.codeBlock());
        Location getLocation = ListenerUtil.getTokenLocation(get.getSymbol());
        verifyCodeBlockOpenBraceStyle(Messages.GETTER, getLocation, ctx.codeBlock());
    }

    void verifySetterOpenBraceStyle(SetterClauseContext ctx) {
        ParseTree leftSibling = ParseTreeUtil.getLeftSibling(ctx.codeBlock());
        Location setLocation =  (leftSibling instanceof TerminalNodeImpl)
                                    ? ListenerUtil.getTokenLocation(((TerminalNodeImpl) leftSibling).getSymbol()) :
                                      ListenerUtil.getContextStopLocation((ParserRuleContext) leftSibling);
        verifyCodeBlockOpenBraceStyle(Messages.SETTER, setLocation, ctx.codeBlock());
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

    //region Whitespace check helpers
    void checkWhitespaceAroundOperator(OperatorDeclarationContext ctx) {
        for (int i = 0; i < ctx.getChild(0).getChildCount(); i++) {
            if (ctx.getChild(0).getChild(i) instanceof OperatorContext) {
                OperatorContext op = (OperatorContext) ctx.getChild(0).getChild(i);
                Token before = ((TerminalNodeImpl) ctx.getChild(0).getChild(i - 1)).getSymbol();
                Token after = ((TerminalNodeImpl) ctx.getChild(0).getChild(i + 1)).getSymbol();

                if (checkLeftSpaces(before, op.getStart(), 1)) {
                    printer.error(Messages.OPERATOR + Messages.SPACE_BEFORE,
                        ListenerUtil.getContextStopLocation(op));
                }

                if (checkRightSpaces(after, op.getStart(), 1)) {
                    printer.error(Messages.OPERATOR + Messages.SPACE_AFTER,
                        ListenerUtil.getContextStopLocation(op));
                }
            }
        }
    }

    void checkWhitespaceAroundColon(TypeAnnotationContext ctx) {
        TerminalNodeImpl colon = (TerminalNodeImpl) ctx.getChild(0);
        ParseTree parentLeftSibling = ParseTreeUtil.getLeftSibling(colon.getParent());
        ParseTree rightSibling = ctx.getChild(1);

        assert !(parentLeftSibling == null || rightSibling == null);

        Token left = parentLeftSibling instanceof ParserRuleContext ? ((ParserRuleContext) parentLeftSibling).getStop()
            : ((TerminalNodeImpl) parentLeftSibling).getSymbol();
        Token right = rightSibling instanceof ParserRuleContext ? ((ParserRuleContext) rightSibling).getStart() :
            ((TerminalNodeImpl) rightSibling).getSymbol();

        Token colonToken = colon.getSymbol();

        verifyColonLeftAssociation(left, right, colonToken);
    }

    void checkWhitespaceAroundColon(DictionaryLiteralItemContext ctx) {
        Token left = ctx.expression(0).getStop();
        Token right = ctx.expression(1).getStart();
        Token colon = ((TerminalNodeImpl) ctx.getChild(1)).getSymbol();

        verifyColonLeftAssociation(left, right, colon);
    }

    void checkWhitespaceAroundColon(DictionaryTypeContext ctx) {
        Token left = ctx.sType(0).getStop();
        Token right = ctx.sType(1).getStart();
        Token colon = ((TerminalNodeImpl) ctx.getChild(2)).getSymbol();

        verifyColonLeftAssociation(left, right, colon);
    }

    void checkWhitespaceAroundColon(SwitchCaseContext ctx) {
        Token left = null;
        Token right = null;
        Token colon = null;

        if (ctx.caseLabel() != null) {
            left = ctx.caseLabel().caseItemList().getStop();
            ParseTree rightChild = ctx.getChild(1);
            // right child can be statements or a semi colon
            right = rightChild instanceof StatementsContext ? ((StatementsContext) rightChild).getStart()
                    : ((TerminalNodeImpl) rightChild).getSymbol();
            colon = ((TerminalNodeImpl) ctx.caseLabel().getChild(2)).getSymbol();
        } else {
            left = ((TerminalNodeImpl) ctx.defaultLabel().getChild(0)).getSymbol();
            ParseTree rightChild = ctx.getChild(1);
            right = rightChild instanceof StatementsContext ? ((StatementsContext) rightChild).getStart()
                : ((TerminalNodeImpl) rightChild).getSymbol();
            colon = ((TerminalNodeImpl) ctx.defaultLabel().getChild(1)).getSymbol();
        }

        verifyColonLeftAssociation(left, right, colon);
    }

    private void verifyColonLeftAssociation(Token left, Token right, Token colon) {
        Location colonLocation = ListenerUtil.getTokenLocation(colon);

        if (checkLeftSpaces(left, colon, 0)) {
            printer.error(Messages.COLON + Messages.AT_COLUMN + colonLocation.column + " " + Messages.NO_SPACE_BEFORE,
                colonLocation);
        }

        if (checkRightSpaces(right, colon, 1)) {
            printer.error(Messages.COLON + Messages.AT_COLUMN + colonLocation.column + " " + Messages.SPACE_AFTER,
                colonLocation);
        }
    }

    private boolean checkLeftSpaces(Token left, Token op, int numSpaces) {
        return op.getLine() == left.getLine()
            && op.getCharPositionInLine() - ListenerUtil.getLastCharPositionInLine(left) != numSpaces + 1;
    }

    private boolean checkRightSpaces(Token right, Token op, int numSpaces) {
        return right.getLine() == op.getLine()
            && right.getCharPositionInLine() - ListenerUtil.getLastCharPositionInLine(op) != numSpaces + 1;
    }
    //endregion
}
