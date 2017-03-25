package com.sleekbyte.tailor.listeners.whitespace;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.antlr.SwiftParser.ClosureParameterClauseContext;
import com.sleekbyte.tailor.antlr.SwiftParser.FunctionNameContext;
import com.sleekbyte.tailor.antlr.SwiftParser.FunctionSignatureContext;
import com.sleekbyte.tailor.antlr.SwiftParser.GenericParameterClauseContext;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.WhitespaceVerifier;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Parse tree listener to flag illegal whitespace in parentheses.
 */
public final class ParenthesisWhitespaceListener extends SwiftBaseListener {
    private WhitespaceVerifier verifier;

    public ParenthesisWhitespaceListener(Printer printer) {
        this.verifier = new WhitespaceVerifier(printer, Rules.PARENTHESIS_WHITESPACE);
    }

    @Override
    public void enterParameterClause(SwiftParser.ParameterClauseContext ctx) {
        verifier.verifyBracketContentWhitespace(ctx, Messages.PARENTHESES);

        ParserRuleContext parent = ctx.getParent();
        if (parent instanceof FunctionSignatureContext) {
            ParseTree declaration = parent.getParent();
            // 1st child is functionHead
            // 2nd child is functionName
            FunctionNameContext functionName = (FunctionNameContext) declaration.getChild(1);
            // Check for operator overloaded methods
            if (functionName.operator() != null
                && !(declaration.getChild(2) instanceof GenericParameterClauseContext)) {
                verifier.verifyLeadingWhitespaceBeforeBracket(ctx, Messages.OPERATOR_OVERLOADING_ONE_SPACE, 1);
                return;
            }
        }

        verifier.verifyLeadingWhitespaceBeforeBracket(ctx, Messages.PARENTHESES, Messages.NO_WHITESPACE_BEFORE, 0);
    }

    @Override
    public void enterClosureParameterClause(ClosureParameterClauseContext ctx) {
        // (Issue #400): Check for one leading whitespace for parameter clauses inside a closure signature
        verifier.verifyLeadingWhitespaceBeforeBracket(ctx, Messages.CLOSURE_PARENTHESES_ONE_SPACE, 1);
    }

    @Override
    public void enterTupleType(SwiftParser.TupleTypeContext ctx) {
        verifier.verifyBracketContentWhitespace(ctx, Messages.PARENTHESES);
    }

    @Override
    public void enterParenthesizedExpression(SwiftParser.ParenthesizedExpressionContext ctx) {
        verifier.verifyBracketContentWhitespace(ctx, Messages.PARENTHESES);
    }

    @Override
    public void enterTupleExpression(SwiftParser.TupleExpressionContext ctx) {
        verifier.verifyBracketContentWhitespace(ctx, Messages.PARENTHESES);
    }

    @Override
    public void enterFunctionCallArgumentClause(SwiftParser.FunctionCallArgumentClauseContext ctx) {
        verifier.verifyBracketContentWhitespace(ctx, Messages.PARENTHESES);
    }

    @Override
    public void enterInitializerDeclaration(SwiftParser.InitializerDeclarationContext ctx) {
        verifier.verifyLeadingWhitespaceBeforeBracket(ctx.parameterClause(), Messages.PARENTHESES,
            Messages.NO_WHITESPACE_BEFORE, 0);
    }

}
