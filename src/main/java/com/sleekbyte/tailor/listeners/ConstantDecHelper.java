package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.utils.ParseTreeUtil;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Helper methods for constant declaration checks.
 */
public class ConstantDecHelper {

    /**
     * Finds and returns constant declaration from ParserRuleContext object.
     *
     * @param ctx context object
     */
    public static ParserRuleContext getConstantDeclaration(ParserRuleContext ctx) {
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

    /**
     * Finds and returns declaration root from ParserRuleContext object.
     *
     * @param ctx context object
     */
    public static ParserRuleContext getDeclarationRoot(ParserRuleContext ctx) {
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

    public static boolean isGlobal(ParserRuleContext ctx) {
        ParserRuleContext parentCtx = ParseTreeUtil.getNthParent(ctx, 4);
        return parentCtx != null && (parentCtx instanceof SwiftParser.TopLevelContext);
    }

    public static boolean insideClass(ParserRuleContext ctx) {
        ParserRuleContext rootCtx = ParseTreeUtil.getNthParent(getDeclarationRoot(ctx), 3);
        return rootCtx != null && (rootCtx instanceof SwiftParser.ClassDeclarationContext);
    }

    public static boolean insideStruct(ParserRuleContext ctx) {
        ParserRuleContext rootCtx = ParseTreeUtil.getNthParent(getDeclarationRoot(ctx), 3);
        return rootCtx != null && (rootCtx instanceof SwiftParser.StructDeclarationContext);
    }
}
