package com.sleekbyte.tailor.output;

import com.sleekbyte.tailor.common.Location;
import org.antlr.v4.runtime.ParserRuleContext;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * Generates and outputs formatted analysis messages for Xcode
 */
public class Printer {

    private static final Location DEFAULT_LOCATION = new Location(1, 1);
    private File inputFile;

    public Printer(File inputFile) {
        this.inputFile = inputFile;
    }

    /**
     * Prints warning message
     * @param warningMsg warning message to print
     * @param ctx context object containing line and column number for printing [optional]
     * @param location location object containing line and column number for printing [optional]
     */
    public void warn(String warningMsg, Optional<ParserRuleContext> ctx, Optional<Location> location) {
        print("warning", warningMsg, ctx, location);
    }

    /**
     * Prints error message
     * @param errorMsg error message to print
     * @param ctx context object containing line and column number for printing [optional]
     * @param location location object containing line and column number for printing [optional]
     */
    public void error(String errorMsg, Optional<ParserRuleContext> ctx, Optional<Location> location) {
        print("error", errorMsg, ctx, location);
    }

    private void print(String classification, String msg, Optional<ParserRuleContext> ctx,
                       Optional<Location> location) {
        String outputString;
        if(ctx.isPresent()) {
            outputString = genOutputString(classification, msg, ctx.get().getStart().getLine(),
                (ctx.get().getStart().getCharPositionInLine() + 1));
        } else {
            outputString = genOutputString(classification, msg, location.orElse(DEFAULT_LOCATION).line,
                location.orElse(DEFAULT_LOCATION).column);
        }
        System.out.println(outputString);
    }

    private String genOutputString(String classification, String msg, int line, int column) {
        String outputString = "";
        try {
            outputString = inputFile.getCanonicalPath() + ":" + line + ":" + column + ": " +
                classification + ": " + msg;
        } catch (IOException e) {
            System.err.println("Error in getting canonical path of input file: " + e.getMessage());
        }
        return outputString;
    }
}
