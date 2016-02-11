package com.sleekbyte.tailor.listeners.lengths;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.ConstructLengths;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Set;

/**
 * Base class for length listeners.
 */
public abstract class LengthListener extends SwiftBaseListener {

    protected ConstructLengths constructLengths;
    protected Printer printer;
    protected Set<Rules> enabledRules;

    @Override
    public abstract void enterTopLevel(SwiftParser.TopLevelContext ctx);

    @Override
    public abstract void enterClassName(SwiftParser.ClassNameContext ctx);

    @Override
    public abstract void enterEnumName(SwiftParser.EnumNameContext ctx);

    @Override
    public abstract void enterStructName(SwiftParser.StructNameContext ctx);

    @Override
    public abstract void enterProtocolName(SwiftParser.ProtocolNameContext ctx);

    @Override
    public abstract void enterElementName(SwiftParser.ElementNameContext ctx);

    @Override
    public abstract void enterFunctionName(SwiftParser.FunctionNameContext ctx);

    @Override
    public abstract void enterLabelName(SwiftParser.LabelNameContext ctx);

    @Override
    public abstract void enterSetterName(SwiftParser.SetterNameContext ctx);

    @Override
    public abstract void enterTypeName(SwiftParser.TypeNameContext ctx);

    @Override
    public abstract void enterTypealiasName(SwiftParser.TypealiasNameContext ctx);

    @Override
    public abstract void enterVariableName(SwiftParser.VariableNameContext ctx);

    @Override
    public abstract void enterRawValueStyleEnumCase(SwiftParser.RawValueStyleEnumCaseContext ctx);

    @Override
    public abstract void enterUnionStyleEnumCase(SwiftParser.UnionStyleEnumCaseContext ctx);

    protected abstract void verifyNameLength(String constructType, int length, ParserRuleContext ctx);

    protected void createErrorMessage(Rules rule, int constructLength, ParserRuleContext ctx, String constructType,
                                      int length, String msg) {
        String lengthVersusLimit = " (" + constructLength + "/" + length + ")";
        Location location = ListenerUtil.getContextStartLocation(ctx);
        printer.error(rule, constructType + msg + lengthVersusLimit, location);

    }
}
