package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

public class CommentAnalyzer {

    private CommonTokenStream tokenStream;
    private Printer printer;
    private List<Token> singleLineComments = new ArrayList<>();
    private List<Token> multilineComments = new ArrayList<>();

    public CommentAnalyzer(CommonTokenStream tokenStream, Printer printer) {
        this.tokenStream = tokenStream;
        this.printer = printer;
        extractComments();
    }

    public void extractComments() {
        for (int i = 0; i < tokenStream.size(); i++) {
            Token token = tokenStream.get(i);
            if (token.getChannel() != Token.HIDDEN_CHANNEL) continue;
            if (ListenerUtil.isSingleLineComment(token)) {
                singleLineComments.add(token);
            }
            if (ListenerUtil.isMultilineComment(token)) {
                multilineComments.add(token);
            }
        }
    }

    public void analyze() {
        checkWhitespaceForInlineComments();
    }

    private void checkWhitespaceForInlineComments() {
        String singleSpaceRegex = "(?s)(^//\\s.*$)|(^//$)";

        singleLineComments.stream()
            .filter(token -> !token.getText().matches(singleSpaceRegex))
            .forEach(token -> {
                Location commentEnd = new Location(token.getLine(), token.getCharPositionInLine() + 3);
                printer.warn(Messages.SINGLE_LINE_COMMENT + Messages.START_SPACE, commentEnd);
            });
    }

}
