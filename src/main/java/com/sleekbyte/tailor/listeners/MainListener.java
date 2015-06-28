package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.List;

/**
 * Parse tree listener for verifying Swift constructs
 */
public class MainListener extends SwiftBaseListener {

    private static MainListenerHelper listenerHelper;
    private MaxLengths maxLengths;
    private Printer printer;

    public MainListener(Printer printer, MaxLengths maxLengths) {
        this.printer = printer;
        listenerHelper = new MainListenerHelper(printer);
        this.maxLengths = maxLengths;
    }

    @Override
    public void enterClassName(SwiftParser.ClassNameContext ctx) {
        listenerHelper.verifyUpperCamelCase(Messages.CLASS + Messages.NAMES, ctx);
        listenerHelper.verifyNameLength(Messages.CLASS + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterEnumName(SwiftParser.EnumNameContext ctx) {
        listenerHelper.verifyUpperCamelCase(Messages.ENUM + Messages.NAMES, ctx);
        listenerHelper.verifyNameLength(Messages.ENUM + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterEnumCaseName(SwiftParser.EnumCaseNameContext ctx) {
        listenerHelper.verifyUpperCamelCase(Messages.ENUM_CASE + Messages.NAMES, ctx);
        listenerHelper.verifyNameLength(Messages.ENUM_CASE + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterStructName(SwiftParser.StructNameContext ctx) {
        listenerHelper.verifyUpperCamelCase(Messages.STRUCT + Messages.NAMES, ctx);
        listenerHelper.verifyNameLength(Messages.STRUCT + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterProtocolName(SwiftParser.ProtocolNameContext ctx) {
        listenerHelper.verifyUpperCamelCase(Messages.PROTOCOL + Messages.NAMES, ctx);
        listenerHelper.verifyNameLength(Messages.PROTOCOL + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterStatement(SwiftParser.StatementContext ctx) {
        listenerHelper.verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterDeclaration(SwiftParser.DeclarationContext ctx) {
        listenerHelper.verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterLoopStatement(SwiftParser.LoopStatementContext ctx) {
        listenerHelper.verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterBranchStatement(SwiftParser.BranchStatementContext ctx) {
        listenerHelper.verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterLabeledStatement(SwiftParser.LabeledStatementContext ctx) {
        listenerHelper.verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterControlTransferStatement(SwiftParser.ControlTransferStatementContext ctx) {
        listenerHelper.verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterUnionStyleEnumMember(SwiftParser.UnionStyleEnumMemberContext ctx) {
        listenerHelper.verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterProtocolMemberDeclaration(SwiftParser.ProtocolMemberDeclarationContext ctx) {
        listenerHelper.verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterClassBody(SwiftParser.ClassBodyContext ctx) {
        listenerHelper.verifyConstructLength(Messages.CLASS, this.maxLengths.maxClassLength, ctx);
    }

    @Override
    public void enterClosureExpression(SwiftParser.ClosureExpressionContext ctx) {
        listenerHelper.verifyConstructLength(Messages.CLOSURE, this.maxLengths.maxClosureLength, ctx);
    }

    @Override
    public void enterFunctionBody(SwiftParser.FunctionBodyContext ctx) {
        listenerHelper.verifyConstructLength(Messages.FUNCTION, this.maxLengths.maxFunctionLength, ctx);
    }

    @Override
    public void enterStructBody(SwiftParser.StructBodyContext ctx) {
        listenerHelper.verifyConstructLength(Messages.STRUCT, this.maxLengths.maxStructLength, ctx);
    }

    @Override
    public void enterElementName(SwiftParser.ElementNameContext ctx) {
        listenerHelper.verifyNameLength(Messages.ELEMENT + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterExternalParameterName(SwiftParser.ExternalParameterNameContext ctx) {
        listenerHelper.verifyNameLength(Messages.EXTERNAL_PARAMETER + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterFunctionName(SwiftParser.FunctionNameContext ctx) {
        listenerHelper.verifyNameLength(Messages.FUNCTION + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterLabelName(SwiftParser.LabelNameContext ctx) {
        listenerHelper.verifyNameLength(Messages.LABEL + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterLocalParameterName(SwiftParser.LocalParameterNameContext ctx) {
        listenerHelper.verifyNameLength(Messages.LOCAL_PARAMETER + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    // TODO: #41: Fix grammar for setter declarations
    @Override
    public void enterSetterName(SwiftParser.SetterNameContext ctx) {
        listenerHelper.verifyNameLength(Messages.SETTER + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterTypeName(SwiftParser.TypeNameContext ctx) {
        listenerHelper.verifyNameLength(Messages.TYPE + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterTypealiasName(SwiftParser.TypealiasNameContext ctx) {
        listenerHelper.verifyNameLength(Messages.TYPEALIAS + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    // TODO: #13: Handle all cases for variable name declaration
    @Override
    public void enterVariableName(SwiftParser.VariableNameContext ctx) {
        listenerHelper.verifyNameLength(Messages.VARIABLE + Messages.NAME, maxLengths.maxNameLength, ctx);
    }

    @Override
    public void enterConstantDeclaration(SwiftParser.ConstantDeclarationContext ctx) {
        ParseTreeWalker walker = new ParseTreeWalker();
        for (SwiftParser.PatternInitializerContext context : ctx.patternInitializerList().patternInitializer()) {
            SwiftParser.PatternContext pattern = context.pattern();
            evaluatePattern(pattern, walker);
        }
    }

    @Override
    public void enterValueBindingPattern(SwiftParser.ValueBindingPatternContext ctx) {
        if (ctx.getStart().getText().equals("let")) {
            ParseTreeWalker walker = new ParseTreeWalker();
            evaluatePattern(ctx.pattern(), walker);
        }
    }

    @Override
    public void enterParameter(SwiftParser.ParameterContext ctx) {
        for (ParseTree child : ctx.children) {
            if (child.getText().equals("var")) {
                return;
            }
        }
        ParseTreeWalker walker = new ParseTreeWalker();
        if (ctx.externalParameterName() != null) {
            walkConstantDecListener(walker, ctx.externalParameterName());
        }
        walkConstantDecListener(walker, ctx.localParameterName());
    }

    private void walkConstantDecListener(ParseTreeWalker walker, ParserRuleContext tree) {
        walker.walk(new ConstantDecListener(this.printer), tree);
    }

    private void evaluatePattern(SwiftParser.PatternContext pattern, ParseTreeWalker walker) {
        if (pattern.identifierPattern() != null) {
            this.walkConstantDecListener(walker, pattern.identifierPattern());

        } else if (pattern.tuplePattern() != null && pattern.tuplePattern().tuplePatternElementList() != null) {
            evaluateTuplePattern(pattern.tuplePattern(), walker);

        } else if (pattern.enumCasePattern() != null && pattern.enumCasePattern().tuplePattern() != null) {
            evaluateTuplePattern(pattern.enumCasePattern().tuplePattern(), walker);

        } else if (pattern.pattern() != null) {
            evaluatePattern(pattern.pattern(), walker);

        } else if (pattern.expressionPattern() != null) {
            walkConstantDecListener(walker, pattern.expressionPattern().expression().prefixExpression());
        }
    }

    private void evaluateTuplePattern(SwiftParser.TuplePatternContext tuplePatternContext, ParseTreeWalker walker) {
        List<SwiftParser.TuplePatternElementContext> tuplePatternElementContexts = tuplePatternContext.tuplePatternElementList().tuplePatternElement();
        for (SwiftParser.TuplePatternElementContext tuplePatternElement : tuplePatternElementContexts) {
            evaluatePattern(tuplePatternElement.pattern(), walker);
        }
    }
}
