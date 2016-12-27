package com.sleekbyte.tailor.listeners.whitespace;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.antlr.SwiftParser.ArrayLiteralItemsContext;
import com.sleekbyte.tailor.antlr.SwiftParser.AvailabilityArgumentsContext;
import com.sleekbyte.tailor.antlr.SwiftParser.CaptureListItemsContext;
import com.sleekbyte.tailor.antlr.SwiftParser.CaseItemListContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ClosureParameterListContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ConditionListContext;
import com.sleekbyte.tailor.antlr.SwiftParser.DictionaryLiteralItemsContext;
import com.sleekbyte.tailor.antlr.SwiftParser.FunctionCallArgumentListContext;
import com.sleekbyte.tailor.antlr.SwiftParser.GenericArgumentListContext;
import com.sleekbyte.tailor.antlr.SwiftParser.IdentifierListContext;
import com.sleekbyte.tailor.antlr.SwiftParser.ParameterListContext;
import com.sleekbyte.tailor.antlr.SwiftParser.PatternInitializerListContext;
import com.sleekbyte.tailor.antlr.SwiftParser.RawValueStyleEnumCaseListContext;
import com.sleekbyte.tailor.antlr.SwiftParser.TupleElementListContext;
import com.sleekbyte.tailor.antlr.SwiftParser.TuplePatternElementListContext;
import com.sleekbyte.tailor.antlr.SwiftParser.TupleTypeElementListContext;
import com.sleekbyte.tailor.antlr.SwiftParser.UnionStyleEnumCaseListContext;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ParseTreeUtil;
import com.sleekbyte.tailor.utils.WhitespaceVerifier;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

/**
 * Flags commas that are not left associated.
 */
public final class CommaWhitespaceListener extends SwiftBaseListener {

    private WhitespaceVerifier verifier;

    public CommaWhitespaceListener(Printer printer) {
        this.verifier = new WhitespaceVerifier(printer, Rules.COMMA_WHITESPACE);
    }

    @Override
    public void enterTypeInheritanceClause(SwiftParser.TypeInheritanceClauseContext ctx) {
        if (ctx.classRequirement() != null && ctx.typeInheritanceList() != null) {
            Token left = ParseTreeUtil.getStopTokenForNode(ctx.classRequirement());
            Token right = ParseTreeUtil.getStartTokenForNode(ctx.typeInheritanceList());
            Token comma = ((TerminalNodeImpl) ctx.getChild(2)).getSymbol();

            verifyCommaLeftAssociation(left, right, comma);
        }

        if (ctx.typeInheritanceList() != null) {
            checkWhitespaceAroundCommaSeparatedList(ctx.typeInheritanceList());
        }
    }

    @Override
    public void enterGenericParameterList(SwiftParser.GenericParameterListContext ctx) {
        checkWhitespaceAroundCommaSeparatedList(ctx);
    }

    @Override
    public void enterRequirementList(SwiftParser.RequirementListContext ctx) {
        checkWhitespaceAroundCommaSeparatedList(ctx);
    }


    @Override
    public void enterConditionList(ConditionListContext ctx) {
        checkWhitespaceAroundCommaSeparatedList(ctx);
    }

    @Override
    public void enterAvailabilityArguments(AvailabilityArgumentsContext ctx) {
        checkWhitespaceAroundCommaSeparatedList(ctx);
    }

    @Override
    public void enterGenericArgumentList(GenericArgumentListContext ctx) {
        checkWhitespaceAroundCommaSeparatedList(ctx);
    }

    @Override
    public void enterPatternInitializerList(PatternInitializerListContext ctx) {
        checkWhitespaceAroundCommaSeparatedList(ctx);
    }

    @Override
    public void enterParameterList(ParameterListContext ctx) {
        checkWhitespaceAroundCommaSeparatedList(ctx);
    }

    @Override
    public void enterClosureParameterList(ClosureParameterListContext ctx) {
        checkWhitespaceAroundCommaSeparatedList(ctx);
    }

    @Override
    public void enterUnionStyleEnumCaseList(UnionStyleEnumCaseListContext ctx) {
        checkWhitespaceAroundCommaSeparatedList(ctx);
    }

    @Override
    public void enterRawValueStyleEnumCaseList(RawValueStyleEnumCaseListContext ctx) {
        checkWhitespaceAroundCommaSeparatedList(ctx);
    }

    @Override
    public void enterTuplePatternElementList(TuplePatternElementListContext ctx) {
        checkWhitespaceAroundCommaSeparatedList(ctx);
    }

    @Override
    public void enterTupleTypeElementList(TupleTypeElementListContext ctx) {
        checkWhitespaceAroundCommaSeparatedList(ctx);
    }

    @Override
    public void enterArrayLiteralItems(ArrayLiteralItemsContext ctx) {
        checkWhitespaceAroundCommaSeparatedList(ctx);
    }

    @Override
    public void enterDictionaryLiteralItems(DictionaryLiteralItemsContext ctx) {
        checkWhitespaceAroundCommaSeparatedList(ctx);
    }

    @Override
    public void enterCaptureListItems(CaptureListItemsContext ctx) {
        checkWhitespaceAroundCommaSeparatedList(ctx);
    }

    @Override
    public void enterTupleElementList(TupleElementListContext ctx) {
        checkWhitespaceAroundCommaSeparatedList(ctx);
    }

    @Override
    public void enterFunctionCallArgumentList(FunctionCallArgumentListContext ctx) {
        checkWhitespaceAroundCommaSeparatedList(ctx);
    }

    @Override
    public void enterIdentifierList(IdentifierListContext ctx) {
        checkWhitespaceAroundCommaSeparatedList(ctx);
    }

    @Override
    public void enterCaseItemList(CaseItemListContext ctx) {
        checkWhitespaceAroundCommaSeparatedList(ctx);
    }

    private void checkWhitespaceAroundCommaSeparatedList(ParserRuleContext ctx) {
        int numChildren = ctx.children.size();

        for (int i = 0; i < numChildren - 2; i += 2) {
            Token left = ParseTreeUtil.getStopTokenForNode(ctx.getChild(i));
            Token right = ParseTreeUtil.getStartTokenForNode(ctx.getChild(i + 2));
            Token comma = ((TerminalNodeImpl) ctx.getChild(i + 1)).getSymbol();

            verifyCommaLeftAssociation(left, right, comma);
        }

        // For constructs that allow trailing commas (example: array-literal)
        Token last = ParseTreeUtil.getStartTokenForNode(ctx.getChild(numChildren - 1));
        if (last.getText().equals(",")) {
            Token left = ParseTreeUtil.getStopTokenForNode(ctx.getChild(numChildren - 2));
            verifyCommaLeftAssociation(left, last);
        }
    }

    private void verifyCommaLeftAssociation(Token left, Token right, Token comma) {
        verifier.verifyPunctuationLeftAssociation(left, right, comma, Messages.COMMA);
    }

    private void verifyCommaLeftAssociation(Token left, Token comma) {
        verifier.verifyPunctuationLeftAssociation(left, comma, Messages.COMMA);
    }
}
