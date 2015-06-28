package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.CharFormatUtil;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Listener for constant declarations
 */
public class ConstantDecListener extends SwiftBaseListener {

    private Printer printer;

    public ConstantDecListener(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void enterIdentifier(SwiftParser.IdentifierContext ctx) {
        String constantName = ctx.getText();
        ParserRuleContext constantDecContext = getConstantDeclaration(ctx);
        Location location = new Location(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1);

        if (isGlobal(constantDecContext) || insideClass(constantDecContext) || insideStruct(constantDecContext)) {
            if (!CharFormatUtil.isUpperCamelCase(constantName) && !CharFormatUtil.isLowerCamelCase(constantName)) {
                this.printer.error(Messages.GLOBAL + Messages.CONSTANT + Messages.GLOBAL_CONSTANT_NAMING, location);
            }
        } else {
            if (!CharFormatUtil.isLowerCamelCase(constantName)) {
                this.printer.error(Messages.CONSTANT + Messages.CONSTANT_NAMING, location);
            }
        }
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
        ParserRuleContext parentCtx = ConstantDecListener.genNthParent(ctx, 3);
        return parentCtx != null && (parentCtx instanceof SwiftParser.TopLevelContext);
    }

    private boolean insideClass(ParserRuleContext ctx) {
        ParserRuleContext rootCtx = ConstantDecListener.genNthParent(ConstantDecListener.getDeclarationRoot(ctx), 1);
        return rootCtx != null && (rootCtx instanceof SwiftParser.ClassDeclarationContext);
    }

    private boolean insideStruct(ParserRuleContext ctx) {
        ParserRuleContext rootCtx = ConstantDecListener.genNthParent(ConstantDecListener.getDeclarationRoot(ctx), 1);
        return rootCtx != null && (rootCtx instanceof SwiftParser.StructDeclarationContext);
    }

    private static ParserRuleContext genNthParent(ParserRuleContext ctx, int n) {
        if (ctx == null) {
            return null;
        }
        while (n != 0) {
            n--;
            ctx = ctx.getParent();
            if (ctx == null) {
                return null;
            }
        }
        return ctx;
    }

    private static ParserRuleContext getDeclarationRoot(ParserRuleContext ctx) {
        if (ctx == null) {
            return null;
        }
        while (true) {
            ctx = ctx.getParent();
            if (!(ctx instanceof SwiftParser.DeclarationsContext) &&
                !(ctx instanceof SwiftParser.DeclarationContext)) {
                break;
            }
        }
        return ctx;
    }

}
