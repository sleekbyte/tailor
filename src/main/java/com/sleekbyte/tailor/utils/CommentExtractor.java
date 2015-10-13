package com.sleekbyte.tailor.utils;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Extract comments from Swift files.
 */
public final class CommentExtractor {

    private CommonTokenStream tokenStream;
    private List<Token> singleLineComments = new ArrayList<>();
    private List<Token> multilineComments = new ArrayList<>();

    /**
     * Create instance of CommentExtractor.
     *
     * @param tokenStream Token stream obtained from lexer
     */
    public CommentExtractor(CommonTokenStream tokenStream) {
        this.tokenStream = tokenStream;
        extractComments();
    }

    public List<Token> getSingleLineComments() {
        return singleLineComments;
    }

    public List<Token> getMultilineComments() {
        return multilineComments;
    }

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
