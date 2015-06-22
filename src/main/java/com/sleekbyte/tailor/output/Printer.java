package com.sleekbyte.tailor.output;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.swift.SwiftParser;

import java.io.File;
import java.util.Optional;

public class Printer {

    private static File inputFile;

    public Printer(File inputFile) {
        this.inputFile = inputFile;
    }

    public void warn(String warnMsg, Optional<SwiftParser.TopLevelContext> ctx, Optional<Location> location) {
        print("warning", warnMsg, ctx, location);
    }

    public void error(String errorMsg, Optional<SwiftParser.TopLevelContext> ctx, Optional<Location> location) {
        print("error", errorMsg, ctx, location);
    }

    private void print(String classification, String msg, Optional<SwiftParser.TopLevelContext> ctx,
                       Optional<Location> location) {
        if(ctx.isPresent()) {
            System.out.println(inputFile.getName() + ":" +
                ctx.get().getStart().getLine() + ":" +
                (ctx.get().getStart().getCharPositionInLine() + 1) + ": " +
                classification + ": " +
                msg);
        } else {
            Location defaultLoc = new Location(1, 1);
            System.out.println(inputFile.getName() + ":" +
                location.orElse(defaultLoc).line + ":" +
                location.orElse(defaultLoc).column + ": " +
                classification + ": " +
                msg);
        }
    }


}
