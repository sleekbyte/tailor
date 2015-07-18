package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.utils.CharFormatUtil;
import com.sleekbyte.tailor.utils.ListenerUtil;

/**
 * Listener for variable declarations.
 */
public class VariableDecListener extends SwiftBaseListener {

    private ListenerHelper helper;

    /**
     * Creates a VariableDecListener object and retrieves the listener helper singleton.
     */
    VariableDecListener() {
        this.helper = ListenerHelper.INSTANCE;
    }

    @Override
    public void enterIdentifier(SwiftParser.IdentifierContext ctx) {
        String variableName = ctx.getText();
        Location location = ListenerUtil.getContextStartLocation(ctx);

        if (!CharFormatUtil.isLowerCamelCase(variableName)) {
            helper.printer.error(Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE, location);
        }
        helper.verifyNameLength(Messages.VARIABLE + Messages.NAME, helper.maxLengths.maxNameLength, ctx);
    }
}
