package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

/**
 *  Class for analyzing and verifying comments.
 */
public class CommentAnalyzer {

    private CommonTokenStream tokenStream;
    private Printer printer;
    private List<Token> singleLineComments = new ArrayList<>();
    private List<Token> multilineComments = new ArrayList<>();

    /**
     * Create instance of CommentAnalyzer.
     *
     * @param tokenStream Token stream obtained from lexer
     * @param printer An instance of Printer
     */
    public CommentAnalyzer(CommonTokenStream tokenStream, Printer printer) {
        this.tokenStream = tokenStream;
        this.printer = printer;
        extractComments();
    }

    private void extractComments() {
        for (int i = 0; i < tokenStream.size(); i++) {
            Token token = tokenStream.get(i);
            if (token.getChannel() != Token.HIDDEN_CHANNEL) {
                continue;
            }
            if (ListenerUtil.isSingleLineComment(token)) {
                singleLineComments.add(token);
            }
            if (ListenerUtil.isMultilineComment(token)) {
                multilineComments.add(token);
            }
        }
    }

    public void analyze() {
        checkWhitespaceInSingleLineComments();
        checkWhitespaceInMultilineComments();
    }

    private void checkWhitespaceInSingleLineComments() {
        String startingSpaceRegex = "(?s)(^//\\s.*$)|(^//$)";

        singleLineComments.stream()
            .filter(token -> !token.getText().matches(startingSpaceRegex))
            .forEach(token -> startingSpaceWarning(token, Messages.SINGLE_LINE_COMMENT));
    }

    private void checkWhitespaceInMultilineComments() {
        String startingSpaceRegex = "(?s)(^/\\*\\s.*$)|(^/\\*\\*/$)";
        String endSpaceRegex = "(?s)(^.*\\s\\*/\\n?$)|(^/\\*\\*/\\n?$)";

        multilineComments.stream()
            .filter(token -> !token.getText().matches(startingSpaceRegex))
            .forEach(token -> startingSpaceWarning(token, Messages.MULTILINE_COMMENT));

        multilineComments.stream()
            .filter(token -> !token.getText().matches(endSpaceRegex))
            .forEach(token -> this.endingSpaceWarning(token, Messages.MULTILINE_COMMENT));
    }

    private void startingSpaceWarning(Token token, String commentType) {
        Location commentStart = new Location(token.getLine(), token.getCharPositionInLine() + 3);
        printer.warn(commentType + Messages.START_SPACE, commentStart);
    }

    private void endingSpaceWarning(Token token, String commentType) {
        Location commentEnd = ListenerUtil.getEndOfMultilineComment(token);
        printer.warn(commentType + Messages.END_SPACE, commentEnd);
    }

}
