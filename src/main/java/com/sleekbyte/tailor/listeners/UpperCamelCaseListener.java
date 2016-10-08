package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.CharFormatUtil;
import com.sleekbyte.tailor.utils.ListenerUtil;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Parse tree listener for verifying UpperCamelCase rule.
 */
public class UpperCamelCaseListener extends SwiftBaseListener {

    private Printer printer;

    public UpperCamelCaseListener(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void enterClassName(SwiftParser.ClassNameContext ctx) {
        verifyUpperCamelCase(Messages.CLASS + Messages.NAMES, ctx);
    }

    @Override
    public void enterEnumName(SwiftParser.EnumNameContext ctx) {
        verifyUpperCamelCase(Messages.ENUM + Messages.NAMES, ctx);
    }

    @Override
    public void enterStructName(SwiftParser.StructNameContext ctx) {
        verifyUpperCamelCase(Messages.STRUCT + Messages.NAMES, ctx);
    }

    @Override
    public void enterProtocolName(SwiftParser.ProtocolNameContext ctx) {
        verifyUpperCamelCase(Messages.PROTOCOL + Messages.NAMES, ctx);
    }

    @Override
    public void enterGenericParameter(SwiftParser.GenericParameterContext ctx) {
        verifyUpperCamelCase(Messages.GENERIC_PARAMETERS + Messages.NAMES, ctx.typeName());
    }

    private void verifyUpperCamelCase(String constructType, ParserRuleContext ctx) {
        String constructName = ctx.getText();
        if (!CharFormatUtil.isUpperCamelCase(constructName)) {
            Location location = ListenerUtil.getContextStartLocation(ctx);
            this.printer.error(Rules.UPPER_CAMEL_CASE, constructType + Messages.UPPER_CAMEL_CASE, location);
        }
    }

}
