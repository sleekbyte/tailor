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
 * Parse tree listener for lower camel case checks.
 */
public class LowerCamelCaseListener extends SwiftBaseListener {

    private Printer printer;
    private boolean traversedTreeForVarDeclaration = false;

    public LowerCamelCaseListener(Printer printer) {
        this.printer = printer;
    }

    public void setTraversedTreeForVarDeclaration(boolean traversedTreeForVarDeclaration) {
        this.traversedTreeForVarDeclaration = traversedTreeForVarDeclaration;
    }

    @Override
    public void enterFunctionName(SwiftParser.FunctionNameContext ctx) {
        if (ctx.operator() == null) {
            verifyLowerCamelCase(Messages.FUNCTION + Messages.NAMES, ctx);
        }
    }

    @Override
    public void enterVariableName(SwiftParser.VariableNameContext ctx) {
        verifyLowerCamelCase(Messages.VARIABLE + Messages.NAMES, ctx);
    }

    @Override
    public void enterIdentifier(SwiftParser.IdentifierContext ctx) {
        if (traversedTreeForVarDeclaration) {
            verifyLowerCamelCase(Messages.VARIABLE + Messages.NAMES, ctx);
            traversedTreeForVarDeclaration = false;
        }
    }

    private void verifyLowerCamelCase(String constructType, ParserRuleContext ctx) {
        String constructName = ctx.getText();
        if (!CharFormatUtil.startsWithAcronym(constructName) && !CharFormatUtil.isLowerCamelCase(constructName)) {
            Location location = ListenerUtil.getContextStartLocation(ctx);
            this.printer.error(Rules.LOWER_CAMEL_CASE, constructType + Messages.LOWER_CAMEL_CASE, location);
        }
    }
}
