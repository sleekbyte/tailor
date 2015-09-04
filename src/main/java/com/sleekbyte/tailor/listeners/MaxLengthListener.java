package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import com.sleekbyte.tailor.utils.SourceFileUtil;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Parse tree listener for verifying maximum lengths of Swift constructs.
 */
public class MaxLengthListener extends SwiftBaseListener {

    private final MaxLengths maxLengths;
    private Printer printer;
    private boolean traversedTreeForConstantDeclaration = false;
    private boolean traversedTreeForVarDeclaration = false;

    public MaxLengthListener(Printer printer, MaxLengths maxLengths) {
        this.maxLengths = maxLengths;
        this.printer = printer;
    }

    public void setTraversedTreeForConstantDeclaration(boolean traversedTree) {
        this.traversedTreeForConstantDeclaration = traversedTree;
    }

    public void setTraversedTreeForVarDeclaration(boolean traversedTree) {
        this.traversedTreeForVarDeclaration = traversedTree;
    }

    @Override
    public void enterClassName(SwiftParser.ClassNameContext ctx) {
        verifyNameLength(Messages.CLASS + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterEnumName(SwiftParser.EnumNameContext ctx) {
        verifyNameLength(Messages.ENUM + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterStructName(SwiftParser.StructNameContext ctx) {
        verifyNameLength(Messages.STRUCT + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterProtocolName(SwiftParser.ProtocolNameContext ctx) {
        verifyNameLength(Messages.PROTOCOL + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterClassBody(SwiftParser.ClassBodyContext ctx) {
        verifyConstructLength(Messages.CLASS, maxLengths.maxClassLength, ctx);
    }

    @Override
    public void enterClosureExpression(SwiftParser.ClosureExpressionContext ctx) {
        verifyConstructLength(Messages.CLOSURE, maxLengths.maxClosureLength, ctx);
    }

    @Override
    public void enterFunctionBody(SwiftParser.FunctionBodyContext ctx) {
        verifyConstructLength(Messages.FUNCTION, maxLengths.maxFunctionLength, ctx);
    }

    @Override
    public void enterStructBody(SwiftParser.StructBodyContext ctx) {
        verifyConstructLength(Messages.STRUCT, maxLengths.maxStructLength, ctx);
    }

    @Override
    public void enterElementName(SwiftParser.ElementNameContext ctx) {
        verifyNameLength(Messages.ELEMENT + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterFunctionName(SwiftParser.FunctionNameContext ctx) {
        verifyNameLength(Messages.FUNCTION + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterLabelName(SwiftParser.LabelNameContext ctx) {
        verifyNameLength(Messages.LABEL + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterSetterName(SwiftParser.SetterNameContext ctx) {
        verifyNameLength(Messages.SETTER + Messages.NAME, maxLengths.maxNameLength, ctx.identifier());
    }

    @Override
    public void enterTypeName(SwiftParser.TypeNameContext ctx) {
        verifyNameLength(Messages.TYPE + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterTypealiasName(SwiftParser.TypealiasNameContext ctx) {
        verifyNameLength(Messages.TYPEALIAS + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterVariableName(SwiftParser.VariableNameContext ctx) {
        verifyNameLength(Messages.VARIABLE + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterRawValueStyleEnumCase(SwiftParser.RawValueStyleEnumCaseContext ctx) {
        verifyNameLength(Messages.ENUM_CASE + Messages.NAME, maxLengths.maxNameLength,
            ctx.enumCaseName());
    }

    @Override
    public void enterUnionStyleEnumCase(SwiftParser.UnionStyleEnumCaseContext ctx) {
        verifyNameLength(Messages.ENUM_CASE + Messages.NAME, maxLengths.maxNameLength,
            ctx.enumCaseName());
    }

    @Override
    public void enterIdentifier(SwiftParser.IdentifierContext ctx) {
        if (traversedTreeForConstantDeclaration) {
            verifyNameLength(Messages.CONSTANT + Messages.NAME, maxLengths.maxNameLength, ctx);
            traversedTreeForConstantDeclaration = false;
        }
        if (traversedTreeForVarDeclaration) {
            verifyNameLength(Messages.VARIABLE + Messages.NAME, maxLengths.maxNameLength, ctx);
            traversedTreeForVarDeclaration = false;
        }
    }

    private void verifyConstructLength(String constructType, int maxLength, ParserRuleContext ctx) {
        if (SourceFileUtil.constructTooLong(ctx, maxLength)) {
            int constructLength = ctx.getStop().getLine() - ctx.getStart().getLine();
            createErrorMessage(constructLength, ctx, constructType, maxLength, Messages.EXCEEDS_LINE_LIMIT);
        }
    }

    private void verifyNameLength(String constructType, int maxLength, ParserRuleContext ctx) {
        if (SourceFileUtil.nameTooLong(ctx, maxLength)) {
            createErrorMessage(ctx.getText().length(), ctx, constructType, maxLength, Messages.EXCEEDS_CHARACTER_LIMIT);
        }
    }

    private void createErrorMessage(int constructLength, ParserRuleContext ctx, String constructType, int maxLength,
                                    String msg) {
        String lengthVersusLimit = " (" + constructLength + "/" + maxLength + ")";
        Location location = ListenerUtil.getContextStartLocation(ctx);
        printer.error(constructType + msg + lengthVersusLimit, location);
    }

}
