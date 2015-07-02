package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.CharFormatUtil;

public class VariableDecListener extends SwiftBaseListener {

    private Printer printer;

    public VariableDecListener(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void enterIdentifier(SwiftParser.IdentifierContext ctx) {
        String variableName = ctx.getText();
        Location location = new Location(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1);

        if (!CharFormatUtil.isLowerCamelCase(variableName)) {
            this.printer.error(Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE, location);
        }
    }
}
