package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.CharFormatUtil;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Optional;

public class MainListenerHelper {

    private Printer printer;

    public MainListenerHelper(Printer printer) {
        this.printer = printer;
    }

    public void verifyUpperCamelCase(String errorMsg, ParserRuleContext ctx) {
        String constructName = ctx.getText();
        if (!CharFormatUtil.isUpperCamelCase(constructName)) {
            this.printer.error(errorMsg, Optional.of(ctx), Optional.empty());
        }
    }

}
