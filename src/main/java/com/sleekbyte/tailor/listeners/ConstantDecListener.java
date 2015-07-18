package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.utils.CharFormatUtil;
import com.sleekbyte.tailor.utils.ListenerUtil;
import com.sleekbyte.tailor.utils.ParseTreeUtil;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Listener for constant declarations.
 */
public class ConstantDecListener extends SwiftBaseListener {

    private ListenerHelper helper;

    /**
     * Creates a ConstantDecListener object and retrieves the listener helper singleton.
     */
    ConstantDecListener() {
        this.helper = ListenerHelper.INSTANCE;
    }

    @Override
    public void enterIdentifier(SwiftParser.IdentifierContext ctx) {
        String constantName = ctx.getText();
        ParserRuleContext constantDecContext = getConstantDeclaration(ctx);
        Location location = ListenerUtil.getContextStartLocation(ctx);

        if (isGlobal(constantDecContext) || insideClass(constantDecContext) || insideStruct(constantDecContext)) {
            if (!CharFormatUtil.isUpperCamelCase(constantName) && !CharFormatUtil.isLowerCamelCase(constantName)) {
                helper.printer.error(Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING, location);
            } else if (CharFormatUtil.isKPrefixed(constantName)) {
                helper.printer.warn(Messages.CONSTANT + Messages.NAME + Messages.K_PREFIXED, location);
            }
        } else {
            if (!CharFormatUtil.isLowerCamelCase(constantName)) {
                helper.printer.error(Messages.CONSTANT + Messages.LOWER_CAMEL_CASE, location);
            } else if (CharFormatUtil.isKPrefixed(constantName)) {
                helper.printer.warn(Messages.CONSTANT + Messages.NAME + Messages.K_PREFIXED, location);
            }
        }
        helper.verifyNameLength(Messages.CONSTANT + Messages.NAME, helper.maxLengths.maxNameLength, ctx);
    }

    private ParserRuleContext getConstantDeclaration(ParserRuleContext ctx) {
        if (ctx.getParent() != null) {
            if (ctx.getParent() instanceof SwiftParser.ConstantDeclarationContext) {
                return ctx.getParent();
            } else {
                return getConstantDeclaration(ctx.getParent());
            }
        } else {
            return null;
        }
    }

    private boolean isGlobal(ParserRuleContext ctx) {
        ParserRuleContext parentCtx = ParseTreeUtil.getNthParent(ctx, 3);
        return parentCtx != null && (parentCtx instanceof SwiftParser.TopLevelContext);
    }

    private boolean insideClass(ParserRuleContext ctx) {
        ParserRuleContext rootCtx = ParseTreeUtil.getNthParent(ConstantDecListener.getDeclarationRoot(ctx), 1);
        return rootCtx != null && (rootCtx instanceof SwiftParser.ClassDeclarationContext);
    }

    private boolean insideStruct(ParserRuleContext ctx) {
        ParserRuleContext rootCtx = ParseTreeUtil.getNthParent(ConstantDecListener.getDeclarationRoot(ctx), 1);
        return rootCtx != null && (rootCtx instanceof SwiftParser.StructDeclarationContext);
    }

    private static ParserRuleContext getDeclarationRoot(ParserRuleContext ctx) {
        if (ctx == null) {
            return null;
        }
        while (true) {
            ctx = ctx.getParent();
            if (!(ctx instanceof SwiftParser.DeclarationsContext)
                && !(ctx instanceof SwiftParser.DeclarationContext)) {
                break;
            }
        }
        return ctx;
    }

}
