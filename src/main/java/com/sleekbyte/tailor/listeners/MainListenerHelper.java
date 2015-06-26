package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.CharFormatUtil;
import org.antlr.v4.runtime.ParserRuleContext;

public class MainListenerHelper {

    private Printer printer;

    public MainListenerHelper(Printer printer) {
        this.printer = printer;
    }

    public void verifyUpperCamelCase(String errorMsg, ParserRuleContext ctx) {
        String constructName = ctx.getText();
        if (!CharFormatUtil.isUpperCamelCase(constructName)) {
            this.printer.error(
                errorMsg, new Location(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1));
        }
    }

}
