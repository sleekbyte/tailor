package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser.ClassBodyContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ClosureExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ElseClauseContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ExtensionBodyContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ForInStatementContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ForStatementContext;
import com.sleekbyte.tailor.antlr.SwiftParser.FunctionDeclarationContext;
import com.sleekbyte.tailor.antlr.SwiftParser.GetterClauseContext;
import com.sleekbyte.tailor.antlr.SwiftParser.IfStatementContext;
import com.sleekbyte.tailor.antlr.SwiftParser.InitializerDeclarationContext;
import com.sleekbyte.tailor.antlr.SwiftParser.OptionalBindingContinuationContext;
import com.sleekbyte.tailor.antlr.SwiftParser.OptionalBindingHeadContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ParenthesizedExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.PatternContext;
import com.sleekbyte.tailor.antlr.SwiftParser.PatternInitializerContext;
import com.sleekbyte.tailor.antlr.SwiftParser.PatternInitializerListContext;
import com.sleekbyte.tailor.antlr.SwiftParser.PostfixExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ProtocolBodyContext;
import com.sleekbyte.tailor.antlr.SwiftParser.RepeatWhileStatementContext;
import com.sleekbyte.tailor.antlr.SwiftParser.SetterClauseContext;
import com.sleekbyte.tailor.antlr.SwiftParser.StructBodyContext;
import com.sleekbyte.tailor.antlr.SwiftParser.SwitchStatementContext;
import com.sleekbyte.tailor.antlr.SwiftParser.TuplePatternContext;
import com.sleekbyte.tailor.antlr.SwiftParser.TuplePatternElementContext;
import com.sleekbyte.tailor.antlr.SwiftParser.TypeCastingOperatorContext;
import com.sleekbyte.tailor.antlr.SwiftParser.WhileStatementContext;
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

import java.util.List;

/**
 * Verifier class for listeners that extend {@link SwiftBaseListener}.
 */
class ParseTreeVerifier {

    Printer printer;
    MaxLengths maxLengths;
    BufferedTokenStream tokenStream;

    static final ParseTreeVerifier INSTANCE = new ParseTreeVerifier();

    private ParseTreeVerifier() {
        // Exists only to defeat instantiation.
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

    //region Brace style check
    void verifySwitchStatementBraceStyle(SwitchStatementContext ctx) {
        // Open brace
        Location switchExpLocation = ListenerUtil.getTokenLocation(ctx.expression().getStop());
        Location openBraceLocation = ListenerUtil.getLocationOfChildToken(ctx, 2);

        if (switchExpLocation.line != openBraceLocation.line) {
            this.printer.warn(Messages.SWITCH_STATEMENT + Messages.OPEN_BRACKET_STYLE, openBraceLocation);
        }

        // Close brace
        verifyBodyCloseBraceStyle(ctx, Messages.SWITCH_STATEMENT);

    }

    void verifyForInStatementBraceStyle(ForInStatementContext ctx) {
        verifyCodeBlockOpenBraceStyle(ctx.codeBlock(), ctx.expression().getStop(), Messages.FOR_IN_LOOP);
        verifyBodyCloseBraceStyle(ctx.codeBlock(), Messages.FOR_IN_LOOP);
    }

    void verifyInitializerBraceStyle(InitializerDeclarationContext ctx) {
        verifyCodeBlockOpenBraceStyle(ctx.initializerBody().codeBlock(), ctx.parameterClause().getStop(),
            Messages.INITIALIZER_BODY);
        verifyBodyCloseBraceStyle(ctx.initializerBody().codeBlock(), Messages.INITIALIZER_BODY);
    }

    void verifyRepeatWhileLoopBraceStyle(RepeatWhileStatementContext ctx) {
        verifyCodeBlockOpenBraceStyle(ctx.codeBlock(), ctx.getStart(), Messages.REPEAT_WHILE_STATEMENT);
        verifyBodyCloseBraceStyle(ctx.codeBlock(), Messages.REPEAT_WHILE_STATEMENT);
    }

    void verifyWhileLoopBraceStyle(WhileStatementContext ctx) {
        verifyCodeBlockOpenBraceStyle(ctx.codeBlock(), ctx.conditionClause().getStop(), Messages.WHILE_STATEMENT);
        verifyBodyCloseBraceStyle(ctx.codeBlock(), Messages.WHILE_STATEMENT);
    }

    void verifyIfStatementBraceStyle(IfStatementContext ctx) {
        verifyCodeBlockOpenBraceStyle(ctx.codeBlock(), ctx.conditionClause().getStop(), Messages.IF_STATEMENT);
        verifyBodyCloseBraceStyle(ctx.codeBlock(), Messages.IF_STATEMENT);
    }

    void verifyElseClauseBraceStyle(ElseClauseContext ctx) {
        if (ctx.codeBlock() == null) {
            return;
        }
        verifyCodeBlockOpenBraceStyle(ctx.codeBlock(), ctx.getStart(), Messages.ELSE_CLAUSE);
        verifyBodyCloseBraceStyle(ctx.codeBlock(), Messages.ELSE_CLAUSE);
    }

    void verifyFunctionBraceStyle(FunctionDeclarationContext ctx) {
        verifyCodeBlockOpenBraceStyle(ctx.functionBody().codeBlock(), ctx.functionSignature().getStop(),
            Messages.FUNCTION);
        verifyBodyCloseBraceStyle(ctx.functionBody().codeBlock(), Messages.FUNCTION);
    }

    void verifyClassBraceStyle(ClassBodyContext ctx) {
        verifyBodyOpenBraceStyle(ctx, Messages.CLASS);
        verifyBodyCloseBraceStyle(ctx, Messages.CLASS);
    }

    void verifyStructBraceStyle(StructBodyContext ctx) {
        verifyBodyOpenBraceStyle(ctx, Messages.STRUCT);
        verifyBodyCloseBraceStyle(ctx, Messages.STRUCT);
    }

    void verifyForLoopBraceStyle(ForStatementContext ctx) {
        int numChildren = ctx.getChildCount();
        Token loopEndToken;

        // object at [numChildren - 1] index is codeBlock
        // object at [numChildren - 2] index is either an expression or ';'
        ParseTree constructBeforeOpenBrace = ctx.getChild(numChildren - 2);
        loopEndToken = ParseTreeUtil.getStopTokenForNode(constructBeforeOpenBrace);
        verifyCodeBlockOpenBraceStyle(ctx.codeBlock(), loopEndToken, Messages.FOR_LOOP);
        verifyBodyCloseBraceStyle(ctx.codeBlock(), Messages.FOR_LOOP);
    }

    void verifyProtocolBraceStyle(ProtocolBodyContext ctx) {
        verifyBodyOpenBraceStyle(ctx, Messages.PROTOCOL);
        verifyBodyCloseBraceStyle(ctx, Messages.PROTOCOL);
    }

    void verifyEnumBraceStyle(ParserRuleContext ctx) {
        for (ParseTree child : ctx.children) {
            if (child instanceof TerminalNodeImpl) {
                Token openBrace = ((TerminalNodeImpl) child).getSymbol();
                Location openBraceLocation = ListenerUtil.getTokenLocation(openBrace);
                ParserRuleContext leftSibling = (ParserRuleContext) ParseTreeUtil.getLeftSibling(child);
                Location leftSiblingLocation = ListenerUtil.getContextStopLocation(leftSibling);

                if (openBraceLocation.line != leftSiblingLocation.line) {
                    printer.warn(Messages.ENUM + Messages.OPEN_BRACKET_STYLE, openBraceLocation);
                } else if (checkLeftSpaces(leftSibling.getStop(), openBrace, 1)) {
                    printer.error(Messages.OPEN_BRACE + Messages.SPACE_BEFORE, openBraceLocation);
                }
                break;
            }
        }

        ParseTree lastChild = ParseTreeUtil.getLastChild(ctx);
        verifyCloseBraceStyle(lastChild, ParseTreeUtil.getLeftSibling(lastChild), Messages.ENUM);
    }

    private boolean checkLeftSpaces(Token left, Token op, int numSpaces) {
        return op.getLine() == left.getLine()
            && op.getCharPositionInLine() - ListenerUtil.getLastCharPositionInLine(left) != numSpaces + 1;
    }

    void verifyClosureExpressionBraceStyle(ClosureExpressionContext ctx) {
        ParseTree left = ParseTreeUtil.getLeftNode(ctx);
        if (left == null) {
            return;
        }

        // open brace style check
        Location leftLocation = ListenerUtil.getTokenLocation(ParseTreeUtil.getStopTokenForNode(left));
        verifyCodeBlockOpenBraceIsInline(ctx, leftLocation, Messages.CLOSURE);

        // close brace style check
        verifyClosureCloseBraceStyle(ctx);

        /* It doesn't always make sense to check if an opening brace for a closure has a single space before it.
           Example: list.map({(element: Int) in element * 2})
           Only places worth checking are scenarios like these:
            list.map() {(element: Int) in element * 2}
           or
            list.map {(element: Int) in element * 2}
         */
        ParseTree leftSibling = ParseTreeUtil.getLeftSibling(ctx);
        if (leftSibling != null && (leftSibling instanceof ParenthesizedExpressionContext
            || leftSibling instanceof PostfixExpressionContext)) {
            Token leftToken = ((ParserRuleContext) leftSibling).getStop();
            verifySingleSpaceBeforeOpenBrace(ctx, leftToken);
        }

    }

    void verifyExtensionBraceStyle(ExtensionBodyContext ctx) {
        verifyBodyOpenBraceStyle(ctx, Messages.EXTENSION);
        verifyBodyCloseBraceStyle(ctx, Messages.EXTENSION);
    }

    void verifyGetterBraceStyle(GetterClauseContext ctx) {
        TerminalNodeImpl get = (TerminalNodeImpl) ParseTreeUtil.getLeftSibling(ctx.codeBlock());
        verifyCodeBlockOpenBraceStyle(ctx.codeBlock(), get.getSymbol(), Messages.GETTER);
        verifyBodyCloseBraceStyle(ctx.codeBlock(), Messages.GETTER);
    }

    void verifySetterBraceStyle(SetterClauseContext ctx) {
        ParseTree leftSibling = ParseTreeUtil.getLeftSibling(ctx.codeBlock());
        Token set = ParseTreeUtil.getStopTokenForNode(leftSibling);
        verifyCodeBlockOpenBraceStyle(ctx.codeBlock(), set, Messages.SETTER);
        verifyBodyCloseBraceStyle(ctx.codeBlock(), Messages.SETTER);
    }

    private void verifySingleSpaceBeforeOpenBrace(ParserRuleContext codeBlockCtx, Token left) {
        Token openBrace = codeBlockCtx.getStart();
        if (checkLeftSpaces(left, openBrace, 1)) {
            printer.error(Messages.OPEN_BRACE + Messages.SPACE_BEFORE, ListenerUtil.getTokenLocation(openBrace));
        }
    }

    private void verifyCodeBlockOpenBraceIsInline(ParserRuleContext codeBlockCtx, Location constructLocation,
                                                  String constructName) {
        Location openBraceLocation = ListenerUtil.getLocationOfChildToken(codeBlockCtx, 0);
        if (constructLocation.line != openBraceLocation.line) {
            this.printer.warn(constructName + Messages.OPEN_BRACKET_STYLE, openBraceLocation);
        }
    }

    private void verifyCodeBlockOpenBraceStyle(ParserRuleContext codeBlockCtx, Token construct, String constructName) {
        verifyCodeBlockOpenBraceIsInline(codeBlockCtx, ListenerUtil.getTokenLocation(construct), constructName);
        verifySingleSpaceBeforeOpenBrace(codeBlockCtx, construct);
    }

    private void verifyBodyOpenBraceStyle(ParserRuleContext ctx, String constructName) {
        ParserRuleContext leftSibling = (ParserRuleContext) ParseTreeUtil.getLeftSibling(ctx);
        Location constructLocation = ListenerUtil.getContextStopLocation(leftSibling);
        verifyCodeBlockOpenBraceIsInline(ctx, constructLocation, constructName);
        verifySingleSpaceBeforeOpenBrace(ctx, leftSibling.getStop());
    }

    private void verifyCloseBraceStyle(ParseTree closeBrace, ParseTree closeBraceLeftSibling, String constructName) {
        Token closeBraceToken = ((TerminalNodeImpl)closeBrace).getSymbol();
        Location closeBraceLocation = ListenerUtil.getTokenLocation(closeBraceToken);

        if (commentLeftOfCloseBrace(closeBraceToken)) {
            this.printer.warn(constructName + Messages.CLOSE_BRACKET_STYLE, closeBraceLocation);
            return;
        }

        Location closeBraceLeftSiblingLocation = ListenerUtil.getParseTreeStopLocation(closeBraceLeftSibling);
        if (closeBraceLocation.line == closeBraceLeftSiblingLocation.line) {
            if (!closeBraceLeftSibling.getText().equals("{")) {
                this.printer.warn(constructName + Messages.CLOSE_BRACKET_STYLE, closeBraceLocation);
            } else if (closeBraceLocation.column - closeBraceLeftSiblingLocation.column != 1) {
                this.printer.warn(Messages.EMPTY_BODY, closeBraceLeftSiblingLocation);
            }
        }
    }

    private void verifyClosureCloseBraceStyle(ClosureExpressionContext ctx) {
        ParseTree closeBrace = ParseTreeUtil.getLastChild(ctx);
        Token closeBraceToken = ((TerminalNodeImpl) closeBrace).getSymbol();
        Location closeBraceLocation = ListenerUtil.getTokenLocation(closeBraceToken);
        Location openBraceLocation = ListenerUtil.getLocationOfChildToken(ctx, 0);

        if (openBraceLocation.line != closeBraceLocation.line && commentLeftOfCloseBrace(closeBraceToken)) {
            this.printer.warn(Messages.CLOSURE + Messages.CLOSE_BRACKET_STYLE, closeBraceLocation);
            return;
        }

        Location leftSiblingLocation = ListenerUtil.getParseTreeStopLocation(ParseTreeUtil.getLeftSibling(closeBrace));
        if (leftSiblingLocation.line == closeBraceLocation.line && openBraceLocation.line != closeBraceLocation.line) {
            this.printer.warn(Messages.CLOSURE + Messages.CLOSE_BRACKET_STYLE, closeBraceLocation);
        }
    }

    private boolean commentLeftOfCloseBrace(Token closeBraceToken) {
        Location closeBraceLocation = ListenerUtil.getTokenLocation(closeBraceToken);
        List<Token> tokens = tokenStream.getHiddenTokensToLeft(closeBraceToken.getTokenIndex());
        // if comments are to the left of }
        if (tokens != null) {
            Token commentToken = getLastCommentToken(tokens);
            if (commentToken != null) {
                int commentEndLine = ListenerUtil.getEndLineOfToken(commentToken);
                if (commentEndLine == closeBraceLocation.line) {
                    return true;
                }
            }
        }
        return false;
    }

    private void verifyBodyCloseBraceStyle(ParserRuleContext bodyCtx, String constructName) {
        ParseTree closeBrace = ParseTreeUtil.getLastChild(bodyCtx);
        ParseTree closeBraceLeftSibling = ParseTreeUtil.getLeftSibling(closeBrace);
        verifyCloseBraceStyle(closeBrace, closeBraceLeftSibling, constructName);
    }

    private Token getLastCommentToken(List<Token> tokens) {
        for (int i = tokens.size() - 1; i >= 0; i--) {
            if (ListenerUtil.isComment(tokens.get(i))) {
                return tokens.get(i);
            }
        }

        return null;
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

    //region Force type casting check
    void verifyForceTypeCasting(TypeCastingOperatorContext ctx) {
        ParseTree secondChild = ctx.getChild(1);
        if (secondChild.getText().equals("!")) {
            // TODO: use util method that returns location of parse tree once {} check gets merged into master
            Location exclamationLocation = ListenerUtil.getTokenLocation(((TerminalNodeImpl) secondChild).getSymbol());
            printer.warn(Messages.FORCE_CAST, exclamationLocation);
        }
    }
    //endregion
}
