package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.CharFormatUtil;
import com.sleekbyte.tailor.utils.SourceFileUtil;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Helper class for {@link MainListener}
 */
class MainListenerHelper {

    private Printer printer;

    MainListenerHelper(Printer printer) {
        this.printer = printer;
    }

    void verifyUpperCamelCase(String constructType, ParserRuleContext ctx) {
        String constructName = ctx.getText();
        if (!CharFormatUtil.isUpperCamelCase(constructName)) {
            Location location = new Location(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1);
            this.printer.error(constructType + Messages.UPPER_CAMEL_CASE, location);
        }
    }

    void verifyNotSemicolonTerminated(String constructType, ParserRuleContext ctx) {
        String construct = ctx.getText();
        if (construct.endsWith(";")) {
            Location location = new Location(ctx.getStop().getLine(), ctx.getStop().getCharPositionInLine() + 1);
            this.printer.error(constructType + Messages.SEMICOLON, location);
        }
    }

    void verifyConstructLength(String constructType, int maxLength, ParserRuleContext ctx) {
        if (SourceFileUtil.constructTooLong(ctx, maxLength)) {
            int constructLength = ctx.getStop().getLine() - ctx.getStart().getLine();
            String lengthVersusLimit = " (" + constructLength + "/" + maxLength + ")";
            Location location = new Location(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1);
            this.printer.error(constructType + Messages.EXCEEDS_LINE_LIMIT + lengthVersusLimit, location);
        }
    }

    void verifyNameLength(String constructType, int maxLength, ParserRuleContext ctx) {
        if (SourceFileUtil.nameTooLong(ctx, maxLength)) {
            String lengthVersusLimit = " (" + ctx.getText().length() + "/" + maxLength + ")";
            Location location = new Location(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1);
            this.printer.error(constructType + Messages.EXCEEDS_CHARACTER_LIMIT + lengthVersusLimit, location);
        }
    }

}
