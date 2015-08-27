package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.utils.CharFormatUtil;
import com.sleekbyte.tailor.utils.ListenerUtil;

/**
 * Listener for variable declarations.
 */
public class VariableDecListener extends SwiftBaseListener {

    private ConstructListener listener;

    /**
     * Creates a VariableDecListener object and saves a ConstructListener object.
     */
    VariableDecListener(ConstructListener listener) {
        this.listener = listener;
    }

    @Override
    public void enterIdentifier(SwiftParser.IdentifierContext ctx) {
        String variableName = ctx.getText();
        Location location = ListenerUtil.getContextStartLocation(ctx);

        if (listener.ruleEnabled(Rules.LOWER_CAMEL_CASE) && !CharFormatUtil.isLowerCamelCase(variableName)) {
            listener.printer.error(Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE, location);
        }
        (new MaxLengthVerifier(listener.printer)).verifyNameLength(Messages.VARIABLE + Messages.NAME,
            listener.maxLengths.maxNameLength, ctx);
    }
}
