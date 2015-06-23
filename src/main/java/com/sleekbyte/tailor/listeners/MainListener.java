package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.swift.SwiftBaseListener;
import com.sleekbyte.tailor.swift.SwiftParser;

public class MainListener extends SwiftBaseListener {

    private static MainListenerHelper listenerHelper;

    public MainListener(Printer printer) {
        listenerHelper = new MainListenerHelper(printer);
    }

    @Override
    public void enterClassName(SwiftParser.ClassNameContext ctx) {
        listenerHelper.verifyUpperCamelCase("Class names should be in UpperCamelCase", ctx);
    }

    @Override
    public void enterEnumName(SwiftParser.EnumNameContext ctx) {
        listenerHelper.verifyUpperCamelCase("Enum names should be in UpperCamelCase", ctx);
    }

    @Override
    public void enterEnumCaseName(SwiftParser.EnumCaseNameContext ctx) {
        listenerHelper.verifyUpperCamelCase("Enum case names should be in UpperCamelCase", ctx);
    }

    @Override
    public void enterStructName(SwiftParser.StructNameContext ctx) {
        listenerHelper.verifyUpperCamelCase("Struct names should be in UpperCamelCase", ctx);
    }

    @Override
    public void enterProtocolName(SwiftParser.ProtocolNameContext ctx) {
        listenerHelper.verifyUpperCamelCase("Protocol names should be in UpperCamelCase", ctx);
    }

}
