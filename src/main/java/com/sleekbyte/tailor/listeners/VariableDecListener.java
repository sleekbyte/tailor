package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.CharFormatUtil;
import com.sleekbyte.tailor.utils.ListenerUtil;

/**
 * Listener for variable declarations.
 */
public class VariableDecListener extends SwiftBaseListener {

    private MaxLengths maxLengths;
    private Printer printer;
    private MainListenerHelper listenerHelper = new MainListenerHelper();

    /**
     * Creates a VariableDecListener object and sets the printer in listenerHelper.
     *
     * @param printer    {@link Printer} used for outputting messages to user
     * @param maxLengths {@link MaxLengths} stores numbers for max length restrictions
     */
    public VariableDecListener(Printer printer, MaxLengths maxLengths) {
        listenerHelper.setPrinter(printer);
        this.printer = printer;
        this.maxLengths = maxLengths;
    }

    @Override
    public void enterIdentifier(SwiftParser.IdentifierContext ctx) {
        String variableName = ctx.getText();
        Location location = ListenerUtil.getContextStartLocation(ctx);

        if (!CharFormatUtil.isLowerCamelCase(variableName)) {
            this.printer.error(Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE, location);
        }
        listenerHelper.verifyNameLength(Messages.VARIABLE + Messages.NAME, maxLengths.maxNameLength, ctx);
    }
}
