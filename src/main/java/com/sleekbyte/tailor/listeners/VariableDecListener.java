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

    private ParseTreeVerifier verifier;

    /**
     * Creates a VariableDecListener object and retrieves the listener verifier singleton.
     */
    VariableDecListener() {
        this.verifier = ParseTreeVerifier.INSTANCE;
    }

    @Override
    public void enterIdentifier(SwiftParser.IdentifierContext ctx) {
        String variableName = ctx.getText();
        Location location = ListenerUtil.getContextStartLocation(ctx);

        if (!CharFormatUtil.isLowerCamelCase(variableName)) {
            verifier.printer.error(Messages.VARIABLE + Messages.NAMES + Messages.LOWER_CAMEL_CASE, location);
        }
        (new MaxLengthVerifier(verifier.printer)).verifyNameLength(Messages.VARIABLE + Messages.NAME,
            verifier.maxLengths.maxNameLength, ctx);
    }
}
