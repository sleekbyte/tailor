package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

/**
 *  Class for analyzing and verifying comments.
 */
public abstract class CommentAnalyzer {

    private CommonTokenStream tokenStream;
    protected Printer printer;
    protected List<Token> singleLineComments = new ArrayList<>();
    protected List<Token> multilineComments = new ArrayList<>();

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

    public abstract void analyze();

    private void extractComments() {
        for (Token token : tokenStream.getTokens()) {
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
}
