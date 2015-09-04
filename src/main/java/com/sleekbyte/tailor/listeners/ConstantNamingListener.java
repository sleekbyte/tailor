package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.CharFormatUtil;
import com.sleekbyte.tailor.utils.ListenerUtil;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Parse tree listener for constant naming style checks.
 */
public class ConstantNamingListener extends SwiftBaseListener {

    private Printer printer;

    public ConstantNamingListener(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void enterIdentifier(SwiftParser.IdentifierContext ctx) {
        String constantName = ctx.getText();
        ParserRuleContext constantDecContext = ConstantDecHelper.getConstantDeclaration(ctx);
        Location location = ListenerUtil.getContextStartLocation(ctx);

        if (ConstantDecHelper.isGlobal(constantDecContext)
                || ConstantDecHelper.insideClass(constantDecContext)
                || ConstantDecHelper.insideStruct(constantDecContext)) {
            if (!CharFormatUtil.isUpperCamelCase(constantName) && !CharFormatUtil.isLowerCamelCase(constantName)) {
                printer.error(Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING, location);
            }
        } else {
            if (!CharFormatUtil.isLowerCamelCase(constantName)) {
                printer.error(Messages.CONSTANT + Messages.LOWER_CAMEL_CASE, location);
            }
        }
    }

}
