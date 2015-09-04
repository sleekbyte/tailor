package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.output.Printer;
import org.antlr.v4.runtime.BufferedTokenStream;

/**
 * Parse tree listener for verifying Swift constructs.
 */
public class MainListener extends SwiftBaseListener {

    /**
     * Creates a MainListener object and a new parse tree verifier.
     *
     * @param printer    {@link Printer} used for outputting messages to user
     * @param maxLengths {@link MaxLengths} stores numbers for max length restrictions
     */
    public MainListener(Printer printer, MaxLengths maxLengths, BufferedTokenStream tokenStream) {
    }

}
