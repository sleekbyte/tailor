package com.sleekbyte.tailor.listeners.lengths;

import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.antlr.SwiftParser.IdentifierContext;
import com.sleekbyte.tailor.common.ConstructLengths;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.listeners.DeclarationListener;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.SourceFileUtil;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;
import java.util.Set;

/**
 * Parse tree listener for verifying maximum lengths of Swift constructs.
 */
public final class MaxLengthListener extends LengthListener {

    /**
     * Listener that verifies max name and construct lengths.
     *
     * @param printer Printer object
     * @param constructLengths ConstructLengths object
     * @param enabledRules Set of enabled rules
     */
    public MaxLengthListener(Printer printer, ConstructLengths constructLengths, Set<Rules> enabledRules) {
        this.constructLengths = constructLengths;
        this.printer = printer;
        this.enabledRules = enabledRules;
    }

    @Override
    public void enterTopLevel(SwiftParser.TopLevelContext topLevelCtx) {
        List<IdentifierContext> constants = DeclarationListener.getConstantNames(topLevelCtx);
        constants.forEach(ctx ->
            verifyNameLength(Messages.CONSTANT + Messages.NAME, constructLengths.maxNameLength, ctx));

        List<IdentifierContext> variables = DeclarationListener.getVariableNames(topLevelCtx);
        variables.forEach(ctx ->
            verifyNameLength(Messages.VARIABLE + Messages.NAME, constructLengths.maxNameLength, ctx));
    }

    @Override
    public void enterClassName(SwiftParser.ClassNameContext ctx) {
        verifyNameLength(Messages.CLASS + Messages.NAME, constructLengths.maxNameLength, ctx);
    }

    @Override
    public void enterEnumName(SwiftParser.EnumNameContext ctx) {
        verifyNameLength(Messages.ENUM + Messages.NAME, constructLengths.maxNameLength, ctx);
    }

    @Override
    public void enterStructName(SwiftParser.StructNameContext ctx) {
        verifyNameLength(Messages.STRUCT + Messages.NAME, constructLengths.maxNameLength, ctx);
    }

    @Override
    public void enterProtocolName(SwiftParser.ProtocolNameContext ctx) {
        verifyNameLength(Messages.PROTOCOL + Messages.NAME, constructLengths.maxNameLength, ctx);
    }

    @Override
    public void enterClassBody(SwiftParser.ClassBodyContext ctx) {
        if (enabledRules.contains(Rules.MAX_CLASS_LENGTH)) {
            verifyConstructLength(Rules.MAX_CLASS_LENGTH, Messages.CLASS, constructLengths.maxClassLength, ctx);
        }
    }

    @Override
    public void enterClosureExpression(SwiftParser.ClosureExpressionContext ctx) {
        if (enabledRules.contains(Rules.MAX_CLOSURE_LENGTH)) {
            verifyConstructLength(Rules.MAX_CLOSURE_LENGTH, Messages.CLOSURE, constructLengths.maxClosureLength, ctx);
        }
    }

    @Override
    public void enterFunctionBody(SwiftParser.FunctionBodyContext ctx) {
        if (enabledRules.contains(Rules.MAX_FUNCTION_LENGTH)) {
            verifyConstructLength(Rules.MAX_FUNCTION_LENGTH, Messages.FUNCTION, constructLengths.maxFunctionLength,
                ctx);
        }
    }

    @Override
    public void enterStructBody(SwiftParser.StructBodyContext ctx) {
        if (enabledRules.contains(Rules.MAX_STRUCT_LENGTH)) {
            verifyConstructLength(Rules.MAX_STRUCT_LENGTH, Messages.STRUCT, constructLengths.maxStructLength, ctx);
        }
    }

    @Override
    public void enterElementName(SwiftParser.ElementNameContext ctx) {
        verifyNameLength(Messages.ELEMENT + Messages.NAME, constructLengths.maxNameLength, ctx);
    }

    @Override
    public void enterFunctionName(SwiftParser.FunctionNameContext ctx) {
        verifyNameLength(Messages.FUNCTION + Messages.NAME, constructLengths.maxNameLength, ctx);
    }

    @Override
    public void enterLabelName(SwiftParser.LabelNameContext ctx) {
        verifyNameLength(Messages.LABEL + Messages.NAME, constructLengths.maxNameLength, ctx);
    }

    @Override
    public void enterSetterName(SwiftParser.SetterNameContext ctx) {
        verifyNameLength(Messages.SETTER + Messages.NAME, constructLengths.maxNameLength, ctx.identifier());
    }

    @Override
    public void enterTypeName(SwiftParser.TypeNameContext ctx) {
        verifyNameLength(Messages.TYPE + Messages.NAME, constructLengths.maxNameLength, ctx);
    }

    @Override
    public void enterTypealiasName(SwiftParser.TypealiasNameContext ctx) {
        verifyNameLength(Messages.TYPEALIAS + Messages.NAME, constructLengths.maxNameLength, ctx);
    }

    @Override
    public void enterVariableName(SwiftParser.VariableNameContext ctx) {
        verifyNameLength(Messages.VARIABLE + Messages.NAME, constructLengths.maxNameLength, ctx);
    }

    @Override
    public void enterRawValueStyleEnumCase(SwiftParser.RawValueStyleEnumCaseContext ctx) {
        verifyNameLength(Messages.ENUM_CASE + Messages.NAME, constructLengths.maxNameLength,
            ctx.enumCaseName());
    }

    @Override
    public void enterUnionStyleEnumCase(SwiftParser.UnionStyleEnumCaseContext ctx) {
        verifyNameLength(Messages.ENUM_CASE + Messages.NAME, constructLengths.maxNameLength,
            ctx.enumCaseName());
    }

    protected void verifyConstructLength(Rules rule, String constructType, int length, ParserRuleContext ctx) {
        if (SourceFileUtil.constructTooLong(ctx, length)) {
            int constructLength = ctx.getStop().getLine() - ctx.getStart().getLine();
            createErrorMessage(rule, constructLength, ctx, constructType, length, Messages.EXCEEDS_LINE_LIMIT);
        }
    }

    protected void verifyNameLength(String constructType, int length, ParserRuleContext ctx) {
        if (enabledRules.contains(Rules.MAX_NAME_LENGTH) && SourceFileUtil.nameTooLong(ctx, length)) {
            createErrorMessage(Rules.MAX_NAME_LENGTH, ctx.getText().length(), ctx, constructType, length,
                Messages.EXCEEDS_CHARACTER_LIMIT);
        }
    }

}
