package com.sleekbyte.tailor.listeners;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * Listens for errors during parsing.
 */
public class ErrorListener extends BaseErrorListener {

    /**
     * Exception thrown when a parse error occurs.
     */
    public static class ParseException extends RuntimeException {}

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
                            String msg, RecognitionException ex) {
        throw new ParseException();
    }
}
