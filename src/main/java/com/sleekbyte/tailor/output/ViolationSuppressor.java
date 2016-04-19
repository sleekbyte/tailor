package com.sleekbyte.tailor.output;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.listeners.CommentAnalyzer;
import org.antlr.v4.runtime.Token;

import java.util.List;
import java.util.Stack;

/**
 * Ignore analysis on lines that are marked "tailor:disable".
 */
public final class ViolationSuppressor extends CommentAnalyzer {
    private static final String DISABLE_LINE_PATTERN = "// tailor:disable";
    private static final String TAILOR_OFF = "// tailor:off";
    private static final String TAILOR_ON = "// tailor:on";

    /**
     * Create instance of ViolationSuppressor.
     *
     * @param printer            An instance of Printer
     * @param singleLineComments List of // comments
     * @param multilineComments  List of /* comments
     */
    public ViolationSuppressor(Printer printer, List<Token> singleLineComments, List<Token> multilineComments) {
        super(printer, singleLineComments, multilineComments);
    }

    @Override
    public void analyze() {
        Stack<Integer> ignoreBlockBeginStack = new Stack<>();
        Token lastSuppressViolationComment = null;

        for (Token comment : singleLineComments) {
            int lineNumber = comment.getLine();
            String trimmedComment = comment.getText().trim();

            // Ignore lines that end with the following pattern
            if (trimmedComment.equals(DISABLE_LINE_PATTERN)) {
                printer.ignoreRegion(lineNumber, lineNumber);
            }

            // Gather ignore regions
            if (trimmedComment.equals(TAILOR_OFF)) {
                ignoreBlockBeginStack.push(lineNumber);
                lastSuppressViolationComment = comment;
            } else if (trimmedComment.equals(TAILOR_ON)) {
                if (ignoreBlockBeginStack.empty()) {
                    // Print warning message when "off" and "on" tags are not matched
                    printOnOffMismatchWarning(comment);
                    return;
                }

                // Ignore lines from analysis that fall inside the ignore region
                printer.ignoreRegion(ignoreBlockBeginStack.pop(), lineNumber);
                lastSuppressViolationComment = comment;
            }
        }

        // Print warning message when "off" and "on" stack is not empty
        if (!ignoreBlockBeginStack.isEmpty() && lastSuppressViolationComment != null) {
            printOnOffMismatchWarning(lastSuppressViolationComment);
        }
    }

    private void printOnOffMismatchWarning(Token comment) {
        printer.warn(Messages.ON_OFF_MISMATCH,
            new Location(comment.getLine(),
                comment.getCharPositionInLine() + 1));
    }
}
