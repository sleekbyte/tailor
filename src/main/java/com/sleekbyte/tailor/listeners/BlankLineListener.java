package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import com.sleekbyte.tailor.utils.ParseTreeUtil;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.List;

/**
 * Parse tree listener for blank line checks.
 */
public class BlankLineListener extends SwiftBaseListener {

    private Printer printer;
    private BufferedTokenStream tokenStream;

    public BlankLineListener(Printer printer, BufferedTokenStream tokenStream) {
        this.printer = printer;
        this.tokenStream = tokenStream;
    }

    @Override
    public void enterFunctionDeclaration(SwiftParser.FunctionDeclarationContext ctx) {
        verifyBlankLinesAroundFunction(ctx);
    }

    private void verifyBlankLinesAroundFunction(SwiftParser.FunctionDeclarationContext ctx) {
        SwiftParser.DeclarationContext declCtx = (SwiftParser.DeclarationContext) ctx.getParent();

        ParseTree left = ParseTreeUtil.getLeftNode(declCtx);
        if (left != null) {
            Token start = declCtx.getStart();
            List<Token> tokens = tokenStream.getHiddenTokensToLeft(start.getTokenIndex());
            if (getNumberOfBlankLines(tokens) < 1) {
                printer.error(Rules.FUNCTION_WHITESPACE, Messages.FUNCTION + Messages.BLANK_LINE_BEFORE,
                    ListenerUtil.getTokenLocation(start));
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
                printer.error(Rules.FUNCTION_WHITESPACE, Messages.FUNCTION + Messages.BLANK_LINE_AFTER,
                    ListenerUtil.getTokenEndLocation(end));
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

}
