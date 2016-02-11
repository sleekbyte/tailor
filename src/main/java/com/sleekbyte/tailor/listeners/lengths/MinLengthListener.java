package com.sleekbyte.tailor.listeners.lengths;

import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.antlr.SwiftParser.ClassNameContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ElementNameContext;
import com.sleekbyte.tailor.antlr.SwiftParser.EnumNameContext;
import com.sleekbyte.tailor.antlr.SwiftParser.FunctionNameContext;
import com.sleekbyte.tailor.antlr.SwiftParser.IdentifierContext;
import com.sleekbyte.tailor.antlr.SwiftParser.LabelNameContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ProtocolNameContext;
import com.sleekbyte.tailor.antlr.SwiftParser.RawValueStyleEnumCaseContext;
import com.sleekbyte.tailor.antlr.SwiftParser.SetterNameContext;
import com.sleekbyte.tailor.antlr.SwiftParser.StructNameContext;
import com.sleekbyte.tailor.antlr.SwiftParser.TypeNameContext;
import com.sleekbyte.tailor.antlr.SwiftParser.TypealiasNameContext;
import com.sleekbyte.tailor.antlr.SwiftParser.UnionStyleEnumCaseContext;
import com.sleekbyte.tailor.antlr.SwiftParser.VariableNameContext;
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
 * Parse tree listener for verifying minimum lengths of Swift constructs.
 */
public final class MinLengthListener extends LengthListener {

    /**
     * Listener that verifies min name and construct lengths.
     *
     * @param printer Printer object
     * @param constructLengths ConstructLengths object
     * @param enabledRules Set of enabled rules
     */
    public MinLengthListener(Printer printer, ConstructLengths constructLengths, Set<Rules> enabledRules) {
        this.constructLengths = constructLengths;
        this.printer = printer;
        this.enabledRules = enabledRules;
    }

    @Override
    public void enterTopLevel(SwiftParser.TopLevelContext topLevelCtx) {
        List<IdentifierContext> constants = DeclarationListener.getConstantNames(topLevelCtx);
        constants.forEach(ctx ->
            verifyNameLength(Messages.CONSTANT + Messages.NAME, constructLengths.minNameLength, ctx));

        List<IdentifierContext> variables = DeclarationListener.getVariableNames(topLevelCtx);
        variables.forEach(ctx ->
            verifyNameLength(Messages.VARIABLE + Messages.NAME, constructLengths.minNameLength, ctx));
    }

    @Override
    public void enterClassName(ClassNameContext ctx) {
        verifyNameLength(Messages.CLASS + Messages.NAME, constructLengths.minNameLength, ctx);
    }

    @Override
    public void enterEnumName(EnumNameContext ctx) {
        verifyNameLength(Messages.ENUM + Messages.NAME, constructLengths.minNameLength, ctx);
    }

    @Override
    public void enterStructName(StructNameContext ctx) {
        verifyNameLength(Messages.STRUCT + Messages.NAME, constructLengths.minNameLength, ctx);
    }

    @Override
    public void enterProtocolName(ProtocolNameContext ctx) {
        verifyNameLength(Messages.PROTOCOL + Messages.NAME, constructLengths.minNameLength, ctx);
    }

    @Override
    public void enterElementName(ElementNameContext ctx) {
        verifyNameLength(Messages.ELEMENT + Messages.NAME, constructLengths.minNameLength, ctx);
    }

    @Override
    public void enterFunctionName(FunctionNameContext ctx) {
        verifyNameLength(Messages.FUNCTION + Messages.NAME, constructLengths.minNameLength, ctx);
    }

    @Override
    public void enterLabelName(LabelNameContext ctx) {
        verifyNameLength(Messages.LABEL + Messages.NAME, constructLengths.minNameLength, ctx);
    }

    @Override
    public void enterSetterName(SetterNameContext ctx) {
        verifyNameLength(Messages.SETTER + Messages.NAME, constructLengths.minNameLength, ctx);
    }

    @Override
    public void enterTypeName(TypeNameContext ctx) {
        verifyNameLength(Messages.TYPE + Messages.NAME, constructLengths.minNameLength, ctx);
    }

    @Override
    public void enterTypealiasName(TypealiasNameContext ctx) {
        verifyNameLength(Messages.TYPEALIAS + Messages.NAME, constructLengths.minNameLength, ctx);
    }

    @Override
    public void enterVariableName(VariableNameContext ctx) {
        verifyNameLength(Messages.VARIABLE + Messages.NAME, constructLengths.minNameLength, ctx);
    }

    @Override
    public void enterRawValueStyleEnumCase(RawValueStyleEnumCaseContext ctx) {
        verifyNameLength(Messages.ENUM_CASE + Messages.NAME, constructLengths.minNameLength, ctx);
    }

    @Override
    public void enterUnionStyleEnumCase(UnionStyleEnumCaseContext ctx) {
        verifyNameLength(Messages.ENUM_CASE + Messages.NAME, constructLengths.minNameLength, ctx);
    }

    @Override
    protected void verifyNameLength(String constructType, int length, ParserRuleContext ctx) {
        if (enabledRules.contains(Rules.MIN_NAME_LENGTH) && SourceFileUtil.nameTooShort(ctx, length)) {
            createErrorMessage(Rules.MIN_NAME_LENGTH, ctx.getText().length(), ctx, constructType, length,
                Messages.VIOLATES_MINIMUM_CHARACTER_LIMIT);
        }
    }

}
