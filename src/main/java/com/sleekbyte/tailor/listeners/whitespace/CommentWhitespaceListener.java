package com.sleekbyte.tailor.listeners.whitespace;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.listeners.CommentAnalyzer;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import org.antlr.v4.runtime.Token;

import java.util.List;

/**
 * Class to analyze whitespace in comments.
 */
public final class CommentWhitespaceListener extends CommentAnalyzer {
    /**
     * Create instance of CommentWhitespaceListener.
     *
     * @param printer     An instance of Printer
     * @param singleLineComments List of // comments
     * @param multilineComments List of /* comments
     */
    public CommentWhitespaceListener(Printer printer, List<Token> singleLineComments, List<Token> multilineComments) {
        super(printer, singleLineComments, multilineComments);
    }

    @Override
    public void analyze() {
        checkWhitespaceInSingleLineComments();
        checkWhitespaceInMultilineComments();
    }

    private void checkWhitespaceInSingleLineComments() {
        // Matches single-line comments that start with at least one whitespace or are empty
        String startingSpaceRegex = "(?s)(^///?\\s.*$)|(^///?$)"; // (?s) activates Pattern.DOTALL flag
        singleLineComments.stream()
            .filter(token -> !token.getText().matches(startingSpaceRegex))
            .forEach(token -> startingSpaceWarning(token, Messages.SINGLE_LINE_COMMENT));
    }

    private void checkWhitespaceInMultilineComments() {
        // Matches multiline comments that start with at least one whitespace or are empty
        String startingSpaceRegex = "(?s)(^/\\*\\*?\\s.*$)|(^/\\*\\*\\*?/$)";
        // Matches multiline comments that end with at least one whitespace character or are empty
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
        printer.warn(Rules.COMMENT_WHITESPACE, commentType + Messages.START_SPACE, commentStart);
    }

    private void endingSpaceWarning(Token token, String commentType) {
        Location commentEnd = ListenerUtil.getEndOfMultilineComment(token);
        printer.warn(Rules.COMMENT_WHITESPACE, commentType + Messages.END_SPACE, commentEnd);
    }
}
