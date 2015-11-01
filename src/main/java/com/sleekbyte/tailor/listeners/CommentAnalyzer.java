package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.output.Printer;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

/**
 *  Class for analyzing and verifying comments.
 */
public abstract class CommentAnalyzer {

    protected Printer printer;
    protected List<Token> singleLineComments = new ArrayList<>();
    protected List<Token> multilineComments = new ArrayList<>();

    /**
     * Create instance of CommentAnalyzer.
     *
     * @param printer     An instance of Printer
     * @param singleLineComments List of // comments
     * @param multilineComments List of /* comments
     */
    public CommentAnalyzer(Printer printer, List<Token> singleLineComments, List<Token> multilineComments) {
        this.printer = printer;
        this.singleLineComments = singleLineComments;
        this.multilineComments = multilineComments;
    }

    public abstract void analyze();
}
