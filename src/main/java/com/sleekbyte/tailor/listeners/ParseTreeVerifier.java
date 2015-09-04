package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;

/**
 * Verifier class for listeners that extend {@link SwiftBaseListener}.
 */
class ParseTreeVerifier {

    static final ParseTreeVerifier INSTANCE = new ParseTreeVerifier();

    private ParseTreeVerifier() {
        // Exists only to defeat instantiation.
    }

}
