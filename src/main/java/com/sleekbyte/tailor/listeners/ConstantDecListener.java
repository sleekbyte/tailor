package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Location;
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

    public void enterIdentifier(ParserRuleContext ctx) {
        String constantName = ctx.getText();
        ParserRuleContext constantDeclContext = getConstantDeclaration(ctx);
        if (isGlobal(constantDeclContext) || insideClass(constantDeclContext) || insideStruct(constantDeclContext)) {
            if (!CharFormatUtil.isUpperCamelCase(constantName) && !CharFormatUtil.isLowerCamelCase(constantName)) {
                Location location = new Location(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1);
                this.printer.error("Global constant should be either lowerCamelCase or UpperCamelCase", location);
            }
        } else {
            if (!CharFormatUtil.isLowerCamelCase(constantName)) {
                Location location = new Location(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine() + 1);
                this.printer.error("Constant should in lowerCamelCase", location);
            }
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

}
