package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.antlr.SwiftParser.IdentifierContext;
import com.sleekbyte.tailor.antlr.SwiftParser.TopLevelContext;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.CharFormatUtil;
import com.sleekbyte.tailor.utils.ListenerUtil;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

/**
 * Parse tree listener for lower camel case checks.
 */
public class LowerCamelCaseListener extends SwiftBaseListener {

    private Printer printer;

    public LowerCamelCaseListener(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void enterTopLevel(TopLevelContext topLevelCtx) {
        List<IdentifierContext> names = DeclarationListener.getVariableNames(topLevelCtx);
        names.forEach(ctx -> verifyLowerCamelCase(Messages.VARIABLE + Messages.NAMES, ctx));
    }

    @Override
    public void enterFunctionName(SwiftParser.FunctionNameContext ctx) {
        if (ctx.operator() == null) {
            verifyLowerCamelCase(Messages.FUNCTION + Messages.NAMES, ctx);
        }
    }

    @Override
    public void enterRawValueStyleEnumCase(SwiftParser.RawValueStyleEnumCaseContext ctx) {
        verifyLowerCamelCase(Messages.ENUM_CASE + Messages.NAMES, ctx.enumCaseName());
    }

    @Override
    public void enterUnionStyleEnumCase(SwiftParser.UnionStyleEnumCaseContext ctx) {
        verifyLowerCamelCase(Messages.ENUM_CASE + Messages.NAMES, ctx.enumCaseName());
    }

    private void verifyLowerCamelCase(String constructType, ParserRuleContext ctx) {
        String constructName = CharFormatUtil.unescapeIdentifier(ctx.getText());
        if (!CharFormatUtil.isLowerCamelCaseOrAcronym(constructName)) {
            Location location = ListenerUtil.getIdentifierStartLocation(ctx);
            this.printer.error(Rules.LOWER_CAMEL_CASE, constructType + Messages.LOWER_CAMEL_CASE, location);
        }
    }
}
