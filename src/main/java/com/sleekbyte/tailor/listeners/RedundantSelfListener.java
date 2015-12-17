package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.antlr.SwiftParser.ClosureExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.DynamicTypeExpressionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.FunctionDeclarationContext;
import com.sleekbyte.tailor.antlr.SwiftParser.InitializerDeclarationContext;
import com.sleekbyte.tailor.antlr.SwiftParser.LocalParameterNameContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ParameterClauseContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ParameterContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ParameterListContext;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import com.sleekbyte.tailor.utils.ParseTreeUtil;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Parse tree listener for redundant self keyword usage.
 */
public final class RedundantSelfListener extends SwiftBaseListener {

    private Printer printer;

    public RedundantSelfListener(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void enterSelfExpression(SwiftParser.SelfExpressionContext ctx) {
        Location location = ListenerUtil.getContextStartLocation(ctx);
        // Do not flag
        // 1. standalone self usages
        // 2. self usage in initializer(s) and closures
        ParseTree dot = ParseTreeUtil.getRightNode(ctx);
        if (dot != null && dot.getText().equals(".") && !insideInitializerOrClosureOrDynamicType(ctx)) {
            // Extract function parameter names
            List<String> parameterNames = getFunctionParameters(getParentFunction(ctx));
            ParseTree property = ParseTreeUtil.getRightSibling(dot);
            if (property != null && parameterNames.contains(property.getText())) {
                return;
            }
            // Flag usage of self
            // 1. outside closures and initializer(s)
            // 2. inside methods whose parameters don't have the same name as the property
            printer.warn(Rules.REDUNDANT_SELF, Messages.EXPLICIT_CALL_TO_SELF, location);

        }
    }

    private static boolean insideInitializerOrClosureOrDynamicType(ParserRuleContext ctx) {
        if (ctx == null) {
            return false;
        }
        while (ctx != null) {
            ctx = ctx.getParent();
            if (ctx instanceof ClosureExpressionContext
                || ctx instanceof InitializerDeclarationContext
                || ctx instanceof DynamicTypeExpressionContext) {
                return true;
            }
        }
        return false;
    }

    private static FunctionDeclarationContext getParentFunction(ParserRuleContext ctx) {
        if (ctx == null) {
            return null;
        }
        while (ctx != null) {
            ctx = ctx.getParent();
            if (ctx instanceof FunctionDeclarationContext) {
                return (FunctionDeclarationContext) ctx;
            }
        }
        return null;
    }

    private List<String> getFunctionParameters(FunctionDeclarationContext ctx) {
        ArrayList<String> parameterNames = new ArrayList<>();

        if (ctx == null) {
            return parameterNames;
        }

        List<ParameterClauseContext> parameterClauses = ctx.functionSignature().parameterClauses().parameterClause();
        for (ParameterClauseContext parameterClause : parameterClauses) {
            ParameterListContext parameterList = parameterClause.parameterList();
            if (parameterList != null) {
                for (ParseTree item : parameterList.children) {
                    ParameterContext parameter;
                    if (item.getText().equals(",")) {
                        continue;
                    } else if (item instanceof ParameterListContext) {
                        parameter = ((ParameterListContext) item).parameter();
                    } else {
                        parameter = (ParameterContext) item;
                    }
                    LocalParameterNameContext localParameterName = parameter.localParameterName();
                    if (localParameterName != null) {
                        parameterNames.add(parameter.localParameterName().getText());
                    }
                }
            }
        }
        return parameterNames;
    }
}
