package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import com.sleekbyte.tailor.utils.ParseTreeUtil;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.List;

/**
 * Parse tree listener for brace style checks.
 */
public class BraceStyleListener extends SwiftBaseListener {

    private Printer printer;
    private BufferedTokenStream tokenStream;

    public BraceStyleListener(Printer printer, BufferedTokenStream tokenStream) {
        this.printer = printer;
        this.tokenStream = tokenStream;
    }

    @Override
    public void enterClassBody(SwiftParser.ClassBodyContext ctx) {
        verifyClassBraceStyle(ctx);
    }

    @Override
    public void enterClosureExpression(SwiftParser.ClosureExpressionContext ctx) {
        verifyClosureExpressionBraceStyle(ctx);
    }

    @Override
    public void enterStructBody(SwiftParser.StructBodyContext ctx) {
        verifyStructBraceStyle(ctx);
    }

    @Override
    public void enterSwitchStatement(SwiftParser.SwitchStatementContext ctx) {
        verifySwitchStatementBraceStyle(ctx);
    }

    @Override
    public void enterFunctionDeclaration(SwiftParser.FunctionDeclarationContext ctx) {
        verifyFunctionBraceStyle(ctx);
    }

    @Override
    public void enterElseClause(SwiftParser.ElseClauseContext ctx) {
        verifyElseClauseBraceStyle(ctx);
    }

    @Override
    public void enterIfStatement(SwiftParser.IfStatementContext ctx) {
        verifyIfStatementBraceStyle(ctx);
    }

    @Override
    public void enterWhileStatement(SwiftParser.WhileStatementContext ctx) {
        verifyWhileLoopBraceStyle(ctx);
    }

    @Override
    public void enterRepeatWhileStatement(SwiftParser.RepeatWhileStatementContext ctx) {
        verifyRepeatWhileLoopBraceStyle(ctx);
    }

    @Override
    public void enterInitializerDeclaration(SwiftParser.InitializerDeclarationContext ctx) {
        verifyInitializerBraceStyle(ctx);
    }

    @Override
    public void enterForInStatement(SwiftParser.ForInStatementContext ctx) {
        verifyForInStatementBraceStyle(ctx);
    }

    @Override
    public void enterProtocolBody(SwiftParser.ProtocolBodyContext ctx) {
        verifyProtocolBraceStyle(ctx);
    }

    @Override
    public void enterUnionStyleEnum(SwiftParser.UnionStyleEnumContext ctx) {
        verifyEnumBraceStyle(ctx);
    }

    @Override
    public void enterRawValueStyleEnum(SwiftParser.RawValueStyleEnumContext ctx) {
        verifyEnumBraceStyle(ctx);
    }

    @Override
    public void enterExtensionBody(SwiftParser.ExtensionBodyContext ctx) {
        verifyExtensionBraceStyle(ctx);
    }

    @Override
    public void enterGetterClause(SwiftParser.GetterClauseContext ctx) {
        verifyGetterBraceStyle(ctx);
    }

    @Override
    public void enterSetterClause(SwiftParser.SetterClauseContext ctx) {
        verifySetterBraceStyle(ctx);
    }

    @Override
    public void enterSubscriptDeclaration(SwiftParser.SubscriptDeclarationContext ctx) {
        verifySubscriptBraceStyle(ctx);
    }

    @Override
    public void enterGetterSetterBlock(SwiftParser.GetterSetterBlockContext ctx) {
        verifyGetterSetterBraceStyle(ctx);
    }

    @Override
    public void enterWillSetClause(SwiftParser.WillSetClauseContext ctx) {
        verifyWillSetClauseBraceStyle(ctx);
    }

    @Override
    public void enterDidSetClause(SwiftParser.DidSetClauseContext ctx) {
        verifyDidSetClauseBraceStyle(ctx);
    }

    @Override
    public void enterWillSetDidSetBlock(SwiftParser.WillSetDidSetBlockContext ctx) {
        verifyWillSetDidSetBlockBraceStyle(ctx);
    }

    private void verifySwitchStatementBraceStyle(SwiftParser.SwitchStatementContext ctx) {
        // Open brace
        Location switchExpLocation = ListenerUtil.getTokenLocation(ctx.expression().getStop());
        Location openBraceLocation = ListenerUtil.getLocationOfChildToken(ctx, 2);

        if (switchExpLocation.line != openBraceLocation.line) {
            this.printer.warn(Rules.BRACE_STYLE, Messages.SWITCH_STATEMENT + Messages.OPEN_BRACE_STYLE,
                openBraceLocation);
        }

        // Close brace
        verifyBodyCloseBraceStyle(ctx, Messages.SWITCH_STATEMENT);

    }

    private void verifyForInStatementBraceStyle(SwiftParser.ForInStatementContext ctx) {
        Token leftOfCodeBlock = ctx.whereClause() == null ? ctx.expression().getStop() : ctx.whereClause().getStop();
        verifyCodeBlockOpenBraceStyle(ctx.codeBlock(), leftOfCodeBlock, Messages.FOR_IN_LOOP);
        verifyBodyCloseBraceStyle(ctx.codeBlock(), Messages.FOR_IN_LOOP);
    }

    private void verifyInitializerBraceStyle(SwiftParser.InitializerDeclarationContext ctx) {
        // Check if there is an element between the parameter clause and open brace (i.e. 'throws' or 'rethrows')
        // (Issue #405)
        ParseTree leftSibling = ParseTreeUtil.getLeftSibling(ctx.initializerBody());
        if (leftSibling instanceof TerminalNodeImpl) {
            verifyCodeBlockOpenBraceStyle(
                ctx.initializerBody().codeBlock(),
                ((TerminalNodeImpl) leftSibling).getSymbol(),
                Messages.INITIALIZER_BODY);
        } else {
            verifyCodeBlockOpenBraceStyle(ctx.initializerBody().codeBlock(), ctx.parameterClause().getStop(),
                Messages.INITIALIZER_BODY);
        }

        verifyBodyCloseBraceStyle(ctx.initializerBody().codeBlock(), Messages.INITIALIZER_BODY);
    }

    private void verifyRepeatWhileLoopBraceStyle(SwiftParser.RepeatWhileStatementContext ctx) {
        verifyCodeBlockOpenBraceStyle(ctx.codeBlock(), ctx.getStart(), Messages.REPEAT_WHILE_STATEMENT);
        verifyBodyCloseBraceStyle(ctx.codeBlock(), Messages.REPEAT_WHILE_STATEMENT);
    }

    private void verifyWhileLoopBraceStyle(SwiftParser.WhileStatementContext ctx) {
        verifyCodeBlockOpenBraceStyle(ctx.codeBlock(), ctx.conditionList().getStop(), Messages.WHILE_STATEMENT);
        verifyBodyCloseBraceStyle(ctx.codeBlock(), Messages.WHILE_STATEMENT);
    }

    private void verifyIfStatementBraceStyle(SwiftParser.IfStatementContext ctx) {
        verifyCodeBlockOpenBraceStyle(ctx.codeBlock(), ctx.conditionList().getStop(), Messages.IF_STATEMENT);
        verifyBodyCloseBraceStyle(ctx.codeBlock(), Messages.IF_STATEMENT);
    }

    private void verifyElseClauseBraceStyle(SwiftParser.ElseClauseContext ctx) {
        if (ctx.codeBlock() == null) {
            return;
        }
        verifyCodeBlockOpenBraceStyle(ctx.codeBlock(), ctx.getStart(), Messages.ELSE_CLAUSE);
        verifyBodyCloseBraceStyle(ctx.codeBlock(), Messages.ELSE_CLAUSE);
    }

    private void verifyFunctionBraceStyle(SwiftParser.FunctionDeclarationContext ctx) {
        if (ctx.functionBody() == null) {
            return;
        }
        Token left = ParseTreeUtil.getStopTokenForNode(ParseTreeUtil.getLeftSibling(ctx.functionBody()));
        verifyCodeBlockOpenBraceStyle(ctx.functionBody().codeBlock(), left, Messages.FUNCTION);
        verifyBodyCloseBraceStyle(ctx.functionBody().codeBlock(), Messages.FUNCTION);
    }

    private void verifyClassBraceStyle(SwiftParser.ClassBodyContext ctx) {
        verifyBodyOpenBraceStyle(ctx, Messages.CLASS);
        verifyBodyCloseBraceStyle(ctx, Messages.CLASS);
    }

    private void verifyStructBraceStyle(SwiftParser.StructBodyContext ctx) {
        verifyBodyOpenBraceStyle(ctx, Messages.STRUCT);
        verifyBodyCloseBraceStyle(ctx, Messages.STRUCT);
    }

    private void verifyProtocolBraceStyle(SwiftParser.ProtocolBodyContext ctx) {
        verifyBodyOpenBraceStyle(ctx, Messages.PROTOCOL);
        verifyBodyCloseBraceStyle(ctx, Messages.PROTOCOL);
    }

    private void verifyEnumBraceStyle(ParserRuleContext ctx) {
        for (ParseTree child : ctx.children) {
            if (child instanceof TerminalNodeImpl && child.getText().equals("{")) {
                Token openBrace = ((TerminalNodeImpl) child).getSymbol();
                Location openBraceLocation = ListenerUtil.getTokenLocation(openBrace);
                ParserRuleContext leftSibling = (ParserRuleContext) ParseTreeUtil.getLeftSibling(child);
                Location leftSiblingLocation = ListenerUtil.getContextStopLocation(leftSibling);

                if (openBraceLocation.line != leftSiblingLocation.line) {
                    printer.warn(Rules.BRACE_STYLE, Messages.ENUM + Messages.OPEN_BRACE_STYLE, openBraceLocation);
                } else if (checkLeftSpaces(leftSibling.getStop(), openBrace, 1)) {
                    printer.error(Rules.BRACE_STYLE, Messages.OPEN_BRACE + Messages.SPACE_BEFORE, openBraceLocation);
                }
                break;
            }
        }

        ParseTree lastChild = ParseTreeUtil.getLastChild(ctx);
        verifyCloseBraceStyle(lastChild, ParseTreeUtil.getLeftSibling(lastChild), Messages.ENUM);
    }

    private void verifyClosureExpressionBraceStyle(SwiftParser.ClosureExpressionContext ctx) {
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
        if (leftSibling != null && (leftSibling instanceof SwiftParser.FunctionCallArgumentClauseContext
            || leftSibling instanceof SwiftParser.PostfixExpressionContext)) {
            Token leftToken = ((ParserRuleContext) leftSibling).getStop();
            verifySingleSpaceBeforeOpenBrace(ctx, leftToken);
        }

    }

    private void verifyExtensionBraceStyle(SwiftParser.ExtensionBodyContext ctx) {
        verifyBodyOpenBraceStyle(ctx, Messages.EXTENSION);
        verifyBodyCloseBraceStyle(ctx, Messages.EXTENSION);
    }

    private void verifyGetterBraceStyle(SwiftParser.GetterClauseContext ctx) {
        TerminalNodeImpl get = (TerminalNodeImpl) ParseTreeUtil.getLeftSibling(ctx.codeBlock());
        verifyCodeBlockOpenBraceStyle(ctx.codeBlock(), get.getSymbol(), Messages.GETTER);
        verifyBodyCloseBraceStyle(ctx.codeBlock(), Messages.GETTER);
    }

    private void verifySetterBraceStyle(SwiftParser.SetterClauseContext ctx) {
        ParseTree leftSibling = ParseTreeUtil.getLeftSibling(ctx.codeBlock());
        Token set = ParseTreeUtil.getStopTokenForNode(leftSibling);
        verifyCodeBlockOpenBraceStyle(ctx.codeBlock(), set, Messages.SETTER);
        verifyBodyCloseBraceStyle(ctx.codeBlock(), Messages.SETTER);
    }

    private void verifySubscriptBraceStyle(SwiftParser.SubscriptDeclarationContext ctx) {
        verifyBodyOpenBraceStyle((ParserRuleContext) ParseTreeUtil.getLastChild(ctx), Messages.SUBSCRIPT);
        verifyBodyCloseBraceStyle((ParserRuleContext) ParseTreeUtil.getLastChild(ctx), Messages.SUBSCRIPT);
    }

    private void verifyGetterSetterBraceStyle(SwiftParser.GetterSetterBlockContext ctx) {
        verifyBodyOpenBraceStyle(ctx, Messages.GETTER_SETTER_BLOCK);
        verifyBodyCloseBraceStyle(ctx, Messages.GETTER_SETTER_BLOCK);
    }

    private void verifyWillSetClauseBraceStyle(SwiftParser.WillSetClauseContext ctx) {
        Token left = ParseTreeUtil.getStopTokenForNode(ParseTreeUtil.getLeftSibling(ctx.codeBlock()));
        verifyCodeBlockOpenBraceStyle(ctx.codeBlock(), left, Messages.WILL_SET_CLAUSE);
        verifyBodyCloseBraceStyle(ctx.codeBlock(), Messages.WILL_SET_CLAUSE);
    }

    private void verifyDidSetClauseBraceStyle(SwiftParser.DidSetClauseContext ctx) {
        Token left = ParseTreeUtil.getStopTokenForNode(ParseTreeUtil.getLeftSibling(ctx.codeBlock()));
        verifyCodeBlockOpenBraceStyle(ctx.codeBlock(), left, Messages.DID_SET_CLAUSE);
        verifyBodyCloseBraceStyle(ctx.codeBlock(), Messages.DID_SET_CLAUSE);
    }

    private void verifyWillSetDidSetBlockBraceStyle(SwiftParser.WillSetDidSetBlockContext ctx) {
        verifyBodyOpenBraceStyle(ctx, Messages.WILLSET_DIDSET_BLOCK);
        verifyBodyCloseBraceStyle(ctx, Messages.WILLSET_DIDSET_BLOCK);
    }

    private void verifySingleSpaceBeforeOpenBrace(ParserRuleContext codeBlockCtx, Token left) {
        Token openBrace = codeBlockCtx.getStart();
        if (checkLeftSpaces(left, openBrace, 1)) {
            printer.error(Rules.BRACE_STYLE, Messages.OPEN_BRACE + Messages.SPACE_BEFORE,
                ListenerUtil.getTokenLocation(openBrace));
        }
    }

    private void verifyCodeBlockOpenBraceIsInline(ParserRuleContext codeBlockCtx, Location constructLocation,
                                                  String constructName) {
        Location openBraceLocation = ListenerUtil.getLocationOfChildToken(codeBlockCtx, 0);
        if (constructLocation.line != openBraceLocation.line) {
            this.printer.warn(Rules.BRACE_STYLE, constructName + Messages.OPEN_BRACE_STYLE, openBraceLocation);
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
            this.printer.warn(Rules.BRACE_STYLE, constructName + Messages.CLOSE_BRACE_STYLE, closeBraceLocation);
            return;
        }

        Location closeBraceLeftSiblingLocation = ListenerUtil.getParseTreeStopLocation(closeBraceLeftSibling);
        if (closeBraceLocation.line == closeBraceLeftSiblingLocation.line) {
            if (!closeBraceLeftSibling.getText().equals("{")) {
                this.printer.warn(Rules.BRACE_STYLE, constructName + Messages.CLOSE_BRACE_STYLE, closeBraceLocation);
            } else if (closeBraceLocation.column - closeBraceLeftSiblingLocation.column != 1) {
                this.printer.warn(Rules.BRACE_STYLE, Messages.EMPTY_BODY, closeBraceLeftSiblingLocation);
            }
        }
    }

    private void verifyClosureCloseBraceStyle(SwiftParser.ClosureExpressionContext ctx) {
        ParseTree closeBrace = ParseTreeUtil.getLastChild(ctx);
        Token closeBraceToken = ((TerminalNodeImpl) closeBrace).getSymbol();
        Location closeBraceLocation = ListenerUtil.getTokenLocation(closeBraceToken);
        Location openBraceLocation = ListenerUtil.getLocationOfChildToken(ctx, 0);

        if (openBraceLocation.line != closeBraceLocation.line && commentLeftOfCloseBrace(closeBraceToken)) {
            this.printer.warn(Rules.BRACE_STYLE, Messages.CLOSURE + Messages.CLOSE_BRACE_STYLE, closeBraceLocation);
            return;
        }

        Location leftSiblingLocation = ListenerUtil.getParseTreeStopLocation(ParseTreeUtil.getLeftSibling(closeBrace));
        if (leftSiblingLocation.line == closeBraceLocation.line && openBraceLocation.line != closeBraceLocation.line) {
            this.printer.warn(Rules.BRACE_STYLE, Messages.CLOSURE + Messages.CLOSE_BRACE_STYLE, closeBraceLocation);
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

    private boolean checkLeftSpaces(Token left, Token op, int numSpaces) {
        return op.getLine() == left.getLine()
            && op.getCharPositionInLine() - ListenerUtil.getLastCharPositionInLine(left) != numSpaces + 1;
    }
}
