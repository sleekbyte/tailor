package com.sleekbyte.tailor.output;

import com.sleekbyte.tailor.listeners.CommentAnalyzer;
import com.sleekbyte.tailor.utils.Pair;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Ignore analysis on lines that are marked "tailor:disable".
 */
public final class SuppressViolationsManager extends CommentAnalyzer {
    private static final String DISABLE_LINE_PATTERN = "// tailor:disable";
    private static final String DISABLE_REGION_BEGIN_PATTERN = "// tailor:disable-region-begin";
    private static final String DISABLE_REGION_END_PATTERN = "// tailor:disable-region-end";

    /**
     * Create instance of SuppressViolationsManager.
     *
     * @param printer            An instance of Printer
     * @param singleLineComments List of // comments
     * @param multilineComments  List of /* comments
     */
    public SuppressViolationsManager(Printer printer, List<Token> singleLineComments, List<Token> multilineComments) {
        super(printer, singleLineComments, multilineComments);
    }

    @Override
    public void analyze() {
        List<Pair<Integer, Integer>> ignoreRegionList = new ArrayList<>();
        Stack<Integer> ignoreBlockBeginStack = new Stack<>();

        for (Token comment : singleLineComments) {
            int lineNumber = comment.getLine();
            String trimmedComment = comment.getText().trim();

            // Ignore lines that end with the following pattern
            if (trimmedComment.equals(DISABLE_LINE_PATTERN)) {
                printer.ignoreRegion(lineNumber, lineNumber);
            }

            // Gather ignore regions
            if (trimmedComment.equals(DISABLE_REGION_BEGIN_PATTERN)) {
                ignoreBlockBeginStack.push(lineNumber);
            } else if (trimmedComment.equals(DISABLE_REGION_END_PATTERN)) {
                if (ignoreBlockBeginStack.empty()) {
                    // TODO: print error message when "start" and "end" tags do not matched
                }
                ignoreRegionList.add(new Pair<>(ignoreBlockBeginStack.pop(), lineNumber));
            }
        }

        // Ignore lines from analysis that fall inside the ignore region
        for (Pair<Integer, Integer> region : ignoreRegionList) {
            printer.ignoreRegion(region.getFirst(), region.getSecond());
        }
    }
}
