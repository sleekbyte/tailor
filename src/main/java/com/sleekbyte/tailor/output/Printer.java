package com.sleekbyte.tailor.output;

import com.sleekbyte.tailor.common.Location;
import org.antlr.v4.runtime.ParserRuleContext;

import java.io.File;
import java.util.Optional;

public class Printer {

    private static final Location DEFAULT_LOCATION = new Location(1, 1);
    private static File inputFile;

    public Printer(File inputFile) {
        this.inputFile = inputFile;
    }

    public void warn(String warnMsg, Optional<ParserRuleContext> ctx, Optional<Location> location) {
        print("warning", warnMsg, ctx, location);
    }

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
        return inputFile.getName() + ":" + line + ":" + column + ": " + classification + ": " + msg;
    }
}
