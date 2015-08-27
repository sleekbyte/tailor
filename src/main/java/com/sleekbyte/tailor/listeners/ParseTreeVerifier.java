package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser.ConditionalOperatorContext;
import com.sleekbyte.tailor.antlr.SwiftParser.DeclarationContext;
import com.sleekbyte.tailor.antlr.SwiftParser.DictionaryLiteralItemContext;
import com.sleekbyte.tailor.antlr.SwiftParser.DictionaryTypeContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ExpressionElementContext;
import com.sleekbyte.tailor.antlr.SwiftParser.FunctionDeclarationContext;
import com.sleekbyte.tailor.antlr.SwiftParser.FunctionResultContext;
import com.sleekbyte.tailor.antlr.SwiftParser.GenericParameterContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ImportDeclarationContext;
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
import com.sleekbyte.tailor.antlr.SwiftParser.STypeContext;
import com.sleekbyte.tailor.antlr.SwiftParser.SubscriptResultContext;
import com.sleekbyte.tailor.antlr.SwiftParser.SwitchCaseContext;
import com.sleekbyte.tailor.antlr.SwiftParser.TuplePatternContext;
import com.sleekbyte.tailor.antlr.SwiftParser.TuplePatternElementContext;
import com.sleekbyte.tailor.antlr.SwiftParser.TypeAnnotationContext;
import com.sleekbyte.tailor.antlr.SwiftParser.TypeInheritanceClauseContext;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.CharFormatUtil;
import com.sleekbyte.tailor.utils.ListenerUtil;
import com.sleekbyte.tailor.utils.ParseTreeUtil;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Verifier class for listeners that extend {@link SwiftBaseListener}.
 */
class ParseTreeVerifier {

    private Set<Integer> importLineNumbers = new HashSet<>();
    Printer printer;
    MaxLengths maxLengths;
    BufferedTokenStream tokenStream;

    static final ParseTreeVerifier INSTANCE = new ParseTreeVerifier();

    private ParseTreeVerifier() {
        // Exists only to defeat instantiation.
    }

    void reset() {
        // Ensure that importLineNumbers is refreshed for each new source file.
        importLineNumbers.clear();
    }

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

        Token left = ParseTreeUtil.getStopTokenForNode(parentLeftSibling);
        Token right = ParseTreeUtil.getStartTokenForNode(rightSibling);
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
            right = ParseTreeUtil.getStartTokenForNode(rightChild);
            colon = ((TerminalNodeImpl) ctx.caseLabel().getChild(2)).getSymbol();
        } else {
            left = ((TerminalNodeImpl) ctx.defaultLabel().getChild(0)).getSymbol();
            ParseTree rightChild = ctx.getChild(1);
            right = ParseTreeUtil.getStartTokenForNode(rightChild);
            colon = ((TerminalNodeImpl) ctx.defaultLabel().getChild(1)).getSymbol();
        }

        verifyColonLeftAssociation(left, right, colon);
    }

    void checkWhitespaceAroundColon(TypeInheritanceClauseContext ctx) {
        Token colon = ((TerminalNodeImpl) ctx.getChild(0)).getSymbol();
        Token right = ((ParserRuleContext) ctx.getChild(1)).getStart();
        Token left = ((ParserRuleContext) ParseTreeUtil.getLeftSibling(ctx)).getStop();

        verifyColonLeftAssociation(left, right, colon);
    }

    void checkWhitespaceAroundColon(ConditionalOperatorContext ctx) {
        Token colon = ((TerminalNodeImpl) ctx.getChild(2)).getSymbol();
        Token left = ctx.expression().getStop();
        Token right = ((ParserRuleContext) ParseTreeUtil.getRightSibling(ctx)).getStart();

        verifyColonIsSpaceDelimited(left, right, colon);
    }

    void checkWhitespaceAroundColon(ExpressionElementContext ctx) {
        if (ctx.identifier() != null) {
            Token colon = ((TerminalNodeImpl) ctx.getChild(1)).getSymbol();
            Token left = ctx.identifier().getStop();
            Token right = ctx.expression().getStart();

            verifyColonLeftAssociation(left, right, colon);
        }
    }

    void checkWhitespaceAroundColon(GenericParameterContext ctx) {
        if (ctx.getChildCount() == 3) {
            Token colon = ((TerminalNodeImpl) ctx.getChild(1)).getSymbol();
            Token left = ctx.typeName().getStop();
            Token right = ((ParserRuleContext) ctx.getChild(2)).getStart();

            verifyColonLeftAssociation(left, right, colon);
        }
    }

    private void verifyColonIsSpaceDelimited(Token left, Token right, Token colon) {
        Location colonLocation = ListenerUtil.getTokenLocation(colon);

        if (checkLeftSpaces(left, colon, 1)) {
            printer.error(Messages.COLON + Messages.AT_COLUMN + colonLocation.column + " " + Messages.SPACE_BEFORE,
                colonLocation);
        }

        if (checkRightSpaces(right, colon, 1)) {
            printer.error(Messages.COLON + Messages.AT_COLUMN + colonLocation.column + " " + Messages.SPACE_AFTER,
                colonLocation);
        }
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

    void verifyBlankLinesAroundFunction(FunctionDeclarationContext ctx) {
        DeclarationContext declCtx = (DeclarationContext) ctx.getParent();

        ParseTree left = ParseTreeUtil.getLeftNode(declCtx);
        if (left != null) {
            Token start = declCtx.getStart();
            List<Token> tokens = tokenStream.getHiddenTokensToLeft(start.getTokenIndex());
            if (getNumberOfBlankLines(tokens) < 1) {
                printer.error(Messages.FUNCTION + Messages.BLANK_LINE_BEFORE, ListenerUtil.getTokenLocation(start));
            }
        }

        ParseTree right = ParseTreeUtil.getRightNode(declCtx);
        if (right != null) {
            if (right.getText().equals("<EOF>")) { // function is at the end of the file
                return;
            }
            Token end = declCtx.getStop();
            List<Token> tokens = tokenStream.getHiddenTokensToRight(end.getTokenIndex());
            if (getNumberOfBlankLines(tokens) < 1) {
                printer.error(Messages.FUNCTION + Messages.BLANK_LINE_AFTER, ListenerUtil.getTokenEndLocation(end));
            }
        }
    }

    private static int getNumberOfBlankLines(List<Token> tokens) {
        if (tokens == null || tokens.size() <= 1) {
            return 0;
        }

        int firstNewlineOrCommentIndex = -1;
        // skip tokens until it hits a newline or comment (skipping other whitespace)
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (ListenerUtil.isComment(token) || ListenerUtil.isNewline(token)) {
                firstNewlineOrCommentIndex = i;
                break;
            }
        }
        // skip first newline or comment
        tokens = tokens.subList(firstNewlineOrCommentIndex + 1, tokens.size());

        return (int) tokens.stream().filter(ListenerUtil::isNewline).count();
    }

    void checkWhitespaceAroundArrow(FunctionResultContext ctx) {
        checkWhitespaceAroundReturnArrow(ctx);
    }

    void checkWhitespaceAroundArrow(SubscriptResultContext ctx) {
        checkWhitespaceAroundReturnArrow(ctx);
    }

    void checkWhitespaceAroundArrow(STypeContext ctx) {
        Optional<ParseTree> arrowOptional = ctx.children.stream()
            .filter(node -> node.getText().equals("->"))
            .findFirst();
        if (!arrowOptional.isPresent()) {
            return;
        }
        ParseTree arrow = arrowOptional.get();
        Token left = ParseTreeUtil.getStopTokenForNode(ParseTreeUtil.getLeftSibling(arrow));
        Token right = ParseTreeUtil.getStartTokenForNode(ParseTreeUtil.getRightSibling(arrow));

        verifyArrowIsSpaceDelimited(left, right, ((TerminalNodeImpl) arrow).getSymbol());
    }

    private void checkWhitespaceAroundReturnArrow(ParserRuleContext ctx) {
        Token arrow = ((TerminalNodeImpl) ctx.getChild(0)).getSymbol();
        Token left = ParseTreeUtil.getStopTokenForNode(ParseTreeUtil.getLeftSibling(ctx));
        Token right = ParseTreeUtil.getStartTokenForNode(ctx.getChild(1));

        verifyArrowIsSpaceDelimited(left, right, arrow);
    }



    private void verifyArrowIsSpaceDelimited(Token left, Token right, Token arrow) {
        if (checkLeftSpaces(left, arrow, 1)) {
            printer.error(Messages.RETURN_ARROW + Messages.SPACE_BEFORE, ListenerUtil.getTokenLocation(arrow));
        }
        if (checkRightSpaces(right, arrow, 1)) {
            printer.error(Messages.RETURN_ARROW + Messages.SPACE_AFTER, ListenerUtil.getTokenEndLocation(arrow));
        }
    }
    //endregion

}
