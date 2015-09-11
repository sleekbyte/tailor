package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;

import java.util.HashSet;
import java.util.Set;

/**
 * Parse tree listener for multiple import style check.
 */
public class MultipleImportListener extends SwiftBaseListener {

    private Set<Integer> importLineNumbers = new HashSet<>();
    private Printer printer;

    public MultipleImportListener(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void enterImportDeclaration(SwiftParser.ImportDeclarationContext ctx) {
        verifyMultipleImports(ctx);
    }

    private void verifyMultipleImports(SwiftParser.ImportDeclarationContext ctx) {
        int lineNum = ctx.getStart().getLine();
        if (importLineNumbers.contains(lineNum)) {
            Location location = new Location(lineNum);
            this.printer.warn(Rules.MULTIPLE_IMPORTS, Messages.IMPORTS + Messages.MULTIPLE_IMPORTS, location);
        } else {
            importLineNumbers.add(lineNum);
        }
    }

}
