package com.sleekbyte.tailor.listeners.whitespace;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ParseTreeUtil;
import com.sleekbyte.tailor.utils.WhitespaceVerifier;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

/**
 * Flags invalid whitespace around colons.
 */
public final class ColonWhitespaceListener extends SwiftBaseListener {

    private WhitespaceVerifier verifier;

    public ColonWhitespaceListener(Printer printer) {
        verifier = new WhitespaceVerifier(printer, Rules.COLON_WHITESPACE);
    }

    @Override
    public void enterDictionaryLiteralItem(SwiftParser.DictionaryLiteralItemContext ctx) {
        Token left = ctx.expression(0).getStop();
        Token right = ctx.expression(1).getStart();
        Token colon = ((TerminalNodeImpl) ctx.getChild(1)).getSymbol();

        verifyColonLeftAssociation(left, right, colon);
    }

    @Override
    public void enterTypeAnnotation(SwiftParser.TypeAnnotationContext ctx) {
        TerminalNodeImpl colon = (TerminalNodeImpl) ctx.getChild(0);
        ParseTree parentLeftSibling = ParseTreeUtil.getLeftSibling(colon.getParent());
        ParseTree rightSibling = ctx.getChild(1);

        Token left = ParseTreeUtil.getStopTokenForNode(parentLeftSibling);
        Token right = ParseTreeUtil.getStartTokenForNode(rightSibling);
        Token colonToken = colon.getSymbol();

        verifyColonLeftAssociation(left, right, colonToken);
    }

    @Override
    public void enterDictionaryType(SwiftParser.DictionaryTypeContext ctx) {
        Token left = ctx.sType(0).getStop();
        Token right = ctx.sType(1).getStart();
        Token colon = ((TerminalNodeImpl) ctx.getChild(2)).getSymbol();

        verifyColonLeftAssociation(left, right, colon);
    }

    @Override
    public void enterSwitchCase(SwiftParser.SwitchCaseContext ctx) {
        Token left = null;
        Token right = null;
        Token colon = null;

        if (ctx.caseLabel() != null) {
            left = ctx.caseLabel().caseItemList().getStop();
            ParseTree rightChild = ctx.getChild(1);
            // right child can be statements or a semi colon
            right = ParseTreeUtil.getStartTokenForNode(rightChild);
            colon = ((TerminalNodeImpl) ctx.caseLabel().getChild(2)).getSymbol();
        } else {
            left = ((TerminalNodeImpl) ctx.defaultLabel().getChild(0)).getSymbol();
            ParseTree rightChild = ctx.getChild(1);
            right = ParseTreeUtil.getStartTokenForNode(rightChild);
            colon = ((TerminalNodeImpl) ctx.defaultLabel().getChild(1)).getSymbol();
        }

        verifyColonLeftAssociation(left, right, colon);
    }

    @Override
    public void enterTypeInheritanceClause(SwiftParser.TypeInheritanceClauseContext ctx) {
        Token colon = ((TerminalNodeImpl) ctx.getChild(0)).getSymbol();
        Token right = ((ParserRuleContext) ctx.getChild(1)).getStart();
        Token left = ((ParserRuleContext) ParseTreeUtil.getLeftSibling(ctx)).getStop();

        verifyColonLeftAssociation(left, right, colon);
    }

    @Override
    public void enterConditionalOperator(SwiftParser.ConditionalOperatorContext ctx) {
        Token colon = ((TerminalNodeImpl) ctx.getChild(ctx.getChildCount() - 1)).getSymbol();
        Token left = ctx.expression().getStop();
        Token right = ((ParserRuleContext) ParseTreeUtil.getRightSibling(ctx)).getStart();

        verifyColonIsSpaceDelimited(left, right, colon);
    }

    @Override
    public void enterTupleElement(SwiftParser.TupleElementContext ctx) {
        if (ctx.identifier() != null) {
            Token colon = ((TerminalNodeImpl) ctx.getChild(1)).getSymbol();
            Token left = ctx.identifier().getStop();

            if (ctx.expression() != null) {
                Token right = ctx.expression().getStart();
                verifyColonLeftAssociation(left, right, colon);
            } else {
                verifier.verifyPunctuationLeftAssociation(left, colon, Messages.COLON);
            }
        }
    }

    @Override
    public void enterFunctionCallArgument(SwiftParser.FunctionCallArgumentContext ctx) {
        if (ctx.functionCallIdentifier() != null) {
            Token colon = ((TerminalNodeImpl) ctx.getChild(1)).getSymbol();
            Token left = ctx.functionCallIdentifier().getStop();
            if (ctx.expression() == null && ctx.operator() == null) {
                verifier.verifyPunctuationLeftAssociation(left, colon, Messages.COLON);
                return;
            }
            ParserRuleContext rightCtx = ctx.expression() == null ? ctx.operator() : ctx.expression();
            Token right = rightCtx.getStart();

            verifyColonLeftAssociation(left, right, colon);
        }
    }

    @Override
    public void enterGenericParameter(SwiftParser.GenericParameterContext ctx) {
        if (ctx.getChildCount() == 3) {
            Token colon = ((TerminalNodeImpl) ctx.getChild(1)).getSymbol();
            Token left = ctx.typeName().getStop();
            Token right = ((ParserRuleContext) ctx.getChild(2)).getStart();

            verifyColonLeftAssociation(left, right, colon);
        }
    }

    @Override
    public void enterArgumentName(SwiftParser.ArgumentNameContext ctx) {
        Token colon = ((TerminalNodeImpl) ctx.getChild(1)).getSymbol();
        Token left = ctx.identifier() == null
            ? ((TerminalNodeImpl) ctx.getChild(0)).getSymbol()
            : ctx.identifier().getStop();
        verifier.verifyPunctuationLeftAssociation(left, colon, Messages.COLON);
    }

    private void verifyColonIsSpaceDelimited(Token left, Token right, Token colon) {
        verifier.verifyPunctuationIsSpaceDelimited(left, right, colon, Messages.COLON);
    }

    private void verifyColonLeftAssociation(Token left, Token right, Token colon) {
        verifier.verifyPunctuationLeftAssociation(left, right, colon, Messages.COLON);
    }


}
