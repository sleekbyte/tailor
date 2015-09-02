package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;

/**
 * Parse tree listener for verifying maximum lengths of Swift constructs.
 */
public class MaxLengthListener extends SwiftBaseListener {

    private final MaxLengths maxLengths;
    private MaxLengthVerifier verifier;

    public MaxLengthListener(Printer printer, MaxLengths maxLengths) {
        this.maxLengths = maxLengths;
        this.verifier = new MaxLengthVerifier(printer);
    }

    @Override
    public void enterClassName(SwiftParser.ClassNameContext ctx) {
        this.verifier.verifyNameLength(Messages.CLASS + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterEnumName(SwiftParser.EnumNameContext ctx) {
        this.verifier.verifyNameLength(Messages.ENUM + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterStructName(SwiftParser.StructNameContext ctx) {
        this.verifier.verifyNameLength(Messages.STRUCT + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterProtocolName(SwiftParser.ProtocolNameContext ctx) {
        this.verifier.verifyNameLength(Messages.PROTOCOL + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterClassBody(SwiftParser.ClassBodyContext ctx) {
        this.verifier.verifyConstructLength(Messages.CLASS, maxLengths.maxClassLength, ctx);
    }

    @Override
    public void enterClosureExpression(SwiftParser.ClosureExpressionContext ctx) {
        this.verifier.verifyConstructLength(Messages.CLOSURE, maxLengths.maxClosureLength, ctx);
    }

    @Override
    public void enterFunctionBody(SwiftParser.FunctionBodyContext ctx) {
        this.verifier.verifyConstructLength(Messages.FUNCTION, maxLengths.maxFunctionLength, ctx);
    }

    @Override
    public void enterStructBody(SwiftParser.StructBodyContext ctx) {
        this.verifier.verifyConstructLength(Messages.STRUCT, maxLengths.maxStructLength, ctx);
    }

    @Override
    public void enterElementName(SwiftParser.ElementNameContext ctx) {
        this.verifier.verifyNameLength(Messages.ELEMENT + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterFunctionName(SwiftParser.FunctionNameContext ctx) {
        this.verifier.verifyNameLength(Messages.FUNCTION + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterLabelName(SwiftParser.LabelNameContext ctx) {
        this.verifier.verifyNameLength(Messages.LABEL + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterSetterName(SwiftParser.SetterNameContext ctx) {
        this.verifier.verifyNameLength(Messages.SETTER + Messages.NAME, maxLengths.maxNameLength, ctx.identifier());
    }

    @Override
    public void enterTypeName(SwiftParser.TypeNameContext ctx) {
        this.verifier.verifyNameLength(Messages.TYPE + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterTypealiasName(SwiftParser.TypealiasNameContext ctx) {
        this.verifier.verifyNameLength(Messages.TYPEALIAS + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterVariableName(SwiftParser.VariableNameContext ctx) {
        this.verifier.verifyNameLength(Messages.VARIABLE + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterRawValueStyleEnumCase(SwiftParser.RawValueStyleEnumCaseContext ctx) {
        this.verifier.verifyNameLength(Messages.ENUM_CASE + Messages.NAME, maxLengths.maxNameLength,
            ctx.enumCaseName());
    }

    @Override
    public void enterUnionStyleEnumCase(SwiftParser.UnionStyleEnumCaseContext ctx) {
        this.verifier.verifyNameLength(Messages.ENUM_CASE + Messages.NAME, maxLengths.maxNameLength,
            ctx.enumCaseName());
    }

}
