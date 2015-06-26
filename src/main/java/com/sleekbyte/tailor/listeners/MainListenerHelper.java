package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.CharFormatUtil;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Helper class for {@link MainListener}
 */
public class MainListenerHelper {

    private Printer printer;

    public MainListenerHelper(Printer printer) {
        this.printer = printer;
    }

    public void verifyUpperCamelCase(String constructType, ParserRuleContext ctx) {
        String constructName = ctx.getText();
        if (!CharFormatUtil.isUpperCamelCase(constructName)) {
            Location location = new Location(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1);
            this.printer.error(constructType + Messages.UPPER_CAMEL_CASE, location);
        }
    }

    public void verifyNotSemicolonTerminated(String constructType, ParserRuleContext ctx) {
        String construct = ctx.getText();
        if (construct.endsWith(";")) {
            Location location = new Location(ctx.getStop().getLine(), ctx.getStop().getCharPositionInLine() + 1);
            this.printer.error(constructType + Messages.SEMICOLON, location);
        }
    }

}
