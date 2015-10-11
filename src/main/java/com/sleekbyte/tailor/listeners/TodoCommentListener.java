package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import org.antlr.v4.runtime.Token;

import java.util.List;

/**
 * Class to analyze TODO_SYNTAX comments.
 */
public final class TodoCommentListener extends CommentAnalyzer {
    /**
     * Create instance of TodoCommentListener.
     *
     * @param printer     An instance of Printer
     * @param singleLineComments List of // comments
     * @param multilineComments List of /* comments
     */
    public TodoCommentListener(Printer printer, List<Token> singleLineComments, List<Token> multilineComments) {
        super(printer, singleLineComments, multilineComments);
    }

    @Override
    public void analyze() {
        checkTodoSyntaxInSingleLineComments();
        checkTodoSyntaxInMultiLineComments();
    }

    private void checkTodoSyntaxInSingleLineComments() {
        String todoRegex = "(?s:// TODO: \\S.+|// TODO\\(\\S+\\): \\S.+)";
        filterTodoSyntax(todoRegex, singleLineComments);
    }

    private void checkTodoSyntaxInMultiLineComments() {
        String todoRegex = "(?s:/\\*\\s*TODO: [\\S\\*].+|/\\*\\s*TODO\\(\\S+\\): [\\S\\*].+)";
        filterTodoSyntax(todoRegex, multilineComments);
    }

    private void filterTodoSyntax(String todoRegex, List<Token> comments) {
        comments.stream()
            .filter(token -> token.getText().toLowerCase().contains("todo"))
            .filter(token -> !token.getText().matches(todoRegex))
            .forEach(token -> todoContentWarning(token, Messages.TODOS));
    }

    private void todoContentWarning(Token token, String commentType) {
        Location todoLocation = new Location(ListenerUtil.getTokenLocation(token).line);
        printer.warn(Rules.TODO_SYNTAX, commentType + Messages.TODO_SYNTAX, todoLocation);
    }
}
