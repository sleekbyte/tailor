package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import com.sleekbyte.tailor.utils.SourceFileUtil;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Verifier for maximum length checks.
 */
class MaxLengthVerifier {

    private Printer printer;

    MaxLengthVerifier(Printer printer) {
        this.printer = printer;
    }

    void verifyConstructLength(String constructType, int maxLength, ParserRuleContext ctx) {
        if (SourceFileUtil.constructTooLong(ctx, maxLength)) {
            int constructLength = ctx.getStop().getLine() - ctx.getStart().getLine();
            String lengthVersusLimit = " (" + constructLength + "/" + maxLength + ")";
            Location location = ListenerUtil.getContextStartLocation(ctx);
            this.printer.error(constructType + Messages.EXCEEDS_LINE_LIMIT + lengthVersusLimit, location);
        }
    }

    void verifyNameLength(String constructType, int maxLength, ParserRuleContext ctx) {
        if (SourceFileUtil.nameTooLong(ctx, maxLength)) {
            String lengthVersusLimit = " (" + ctx.getText().length() + "/" + maxLength + ")";
            Location location = ListenerUtil.getContextStartLocation(ctx);
            this.printer.error(constructType + Messages.EXCEEDS_CHARACTER_LIMIT + lengthVersusLimit, location);
        }
    }

}
