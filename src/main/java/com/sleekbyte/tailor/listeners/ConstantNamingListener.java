package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
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
 * Parse tree listener for constant naming style checks.
 */
public class ConstantNamingListener extends SwiftBaseListener {

    private Printer printer;

    public ConstantNamingListener(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void enterTopLevel(TopLevelContext topLevelContext) {
        List<IdentifierContext> names =  DeclarationListener.getConstantNames(topLevelContext);

        names.forEach(
            ctx -> {
                String constantName = CharFormatUtil.unescapeIdentifier(ctx.getText());
                ParserRuleContext constantDecContext = ConstantDecHelper.getConstantDeclaration(ctx);
                Location location = ListenerUtil.getIdentifierStartLocation(ctx);

                if (ConstantDecHelper.isGlobal(constantDecContext)
                    || ConstantDecHelper.insideClass(constantDecContext)
                    || ConstantDecHelper.insideStruct(constantDecContext)) {
                    if (!CharFormatUtil.isUpperCamelCase(constantName)
                        && !CharFormatUtil.isLowerCamelCaseOrAcronym(constantName)) {
                        printer.error(Rules.CONSTANT_NAMING, Messages.GLOBAL + Messages.CONSTANT
                            + Messages.GLOBAL_CONSTANT_NAMING, location);
                    }
                } else {
                    if (!CharFormatUtil.isLowerCamelCaseOrAcronym(constantName)) {
                        printer.error(Rules.CONSTANT_NAMING, Messages.CONSTANT + Messages.LOWER_CAMEL_CASE, location);
                    }
                }
            }
        );
    }
}
