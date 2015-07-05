package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * Parse tree listener for verifying Swift constructs.
 */
public class MainListener extends SwiftBaseListener {

    private static final String LET = "let";
    private static final String VAR = "var";
    private MainListenerHelper listenerHelper = new MainListenerHelper();
    private MaxLengths maxLengths;

    public MainListener(Printer printer, MaxLengths maxLengths) {
        listenerHelper.setPrinter(printer);
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

    @Override
    public void enterSetterName(SwiftParser.SetterNameContext ctx) {
        listenerHelper.verifyNameLength(Messages.SETTER + Messages.NAME, maxLengths.maxNameLength, ctx.identifier());
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
            listenerHelper.evaluatePattern(pattern, walker);
        }
    }

    @Override
    public void enterValueBindingPattern(SwiftParser.ValueBindingPatternContext ctx) {
        if (ctx.getStart().getText().equals(LET)) {
            ParseTreeWalker walker = new ParseTreeWalker();
            listenerHelper.evaluatePattern(ctx.pattern(), walker);
        }
    }

    @Override
    public void enterOptionalBindingCondition(SwiftParser.OptionalBindingConditionContext ctx) {
        /*  Optional Binding is tricky. Consider the following Swift code:
            if let const1 = foo?.bar, const2 = foo?.bar, var var1 = foo?.bar, var2 = foo?.bar {
                ...
            }
            const1 and const2 are both constants even though only const1 has 'let' before it.
            var1 and var2 are both variables as there is a 'var' before var1 and no 'let' before var2.

            The code below iterates through each declared variable and applies rules based on the last seen binding,
            'let' or 'var'.
         */
        String currentBinding = listenerHelper.letOrVar(ctx.optionalBindingHead());
        if (currentBinding.equals(LET)) {
            listenerHelper.evaluateOptionalBindingHead(ctx.optionalBindingHead());
        }
        SwiftParser.OptionalBindingContinuationListContext continuationList = ctx.optionalBindingContinuationList();
        if (continuationList != null) {
            for (SwiftParser.OptionalBindingContinuationContext continuation :
                    continuationList.optionalBindingContinuation()) {
                if (continuation.optionalBindingHead() != null) {
                    currentBinding = listenerHelper.letOrVar(continuation.optionalBindingHead());
                }
                if (currentBinding.equals(LET)) {
                    listenerHelper.evaluateOptionalBindingContinuation(continuation);
                }
            }
        }
    }

    @Override
    public void enterParameter(SwiftParser.ParameterContext ctx) {
        for (ParseTree child : ctx.children) {
            if (child.getText().equals(VAR)) {
                return;
            }
        }
        ParseTreeWalker walker = new ParseTreeWalker();
        if (ctx.externalParameterName() != null) {
            listenerHelper.walkConstantDecListener(walker, ctx.externalParameterName());
        }
        listenerHelper.walkConstantDecListener(walker, ctx.localParameterName());
    }

    @Override
    public void enterConditionClause(SwiftParser.ConditionClauseContext ctx) {
        listenerHelper.verifyRedundantExpressionParenthesis(Messages.CONDITIONAL_CLAUSE, ctx.expression());
    }

    @Override
    public void enterSwitchStatement(SwiftParser.SwitchStatementContext ctx) {
        listenerHelper.verifyRedundantExpressionParenthesis(Messages.SWITCH_EXPRESSION, ctx.expression());
    }

    @Override
    public void enterForStatement(SwiftParser.ForStatementContext ctx) {
        listenerHelper.verifyRedundantForLoopParenthesis(ctx);
    }

    @Override
    public void enterThrowStatement(SwiftParser.ThrowStatementContext ctx) {
        listenerHelper.verifyRedundantExpressionParenthesis(Messages.THROW_STATEMENT, ctx.expression());
    }

    @Override
    public void enterCatchClause(SwiftParser.CatchClauseContext ctx) {
        listenerHelper.verifyRedundantCatchParentheses(ctx.pattern());
    }

    @Override
    public void enterInitializer(SwiftParser.InitializerContext ctx) {
        listenerHelper.verifyRedundantExpressionParenthesis(Messages.INITIALIZER_EXPRESSION, ctx.expression());
    }

    @Override
    public void enterArrayLiteralItem(SwiftParser.ArrayLiteralItemContext ctx) {
        listenerHelper.verifyRedundantExpressionParenthesis(Messages.ARRAY_LITERAL, ctx.expression());
    }

    @Override
    public void enterDictionaryLiteralItem(SwiftParser.DictionaryLiteralItemContext ctx) {
        for (SwiftParser.ExpressionContext expressionContext : ctx.expression()) {
            listenerHelper.verifyRedundantExpressionParenthesis(Messages.DICTIONARY_LITERAL, expressionContext);
        }
    }

    @Override
    public void enterImportDeclaration(SwiftParser.ImportDeclarationContext ctx) {
        listenerHelper.verifyMultipleImports(ctx);
    }

    @Override
    public void enterFunctionDeclaration(SwiftParser.FunctionDeclarationContext ctx) {
        Token functionSignatureStop = ctx.functionSignature().getStop();
        Location functionDeclarationLocation = new Location(functionSignatureStop.getLine(),
                                                               functionSignatureStop.getCharPositionInLine() + 1);
        listenerHelper.verifyCodeBlockBracketStyle(Messages.FUNCTION, functionDeclarationLocation,
                                                      ctx.functionBody().codeBlock());
    }

    @Override
    public void enterElseClause(SwiftParser.ElseClauseContext ctx) {
        Location elseClauseLocation = new Location(ctx.getStart().getLine(),
                                                      ctx.getStart().getCharPositionInLine() + 1);
        listenerHelper.verifyCodeBlockBracketStyle(Messages.ELSE_CLAUSE, elseClauseLocation, ctx.codeBlock());
    }

    @Override
    public void enterIfStatement(SwiftParser.IfStatementContext ctx) {
        Location conditionClauseLocation = new Location(ctx.conditionClause().getStop().getLine(),
                                                           ctx.conditionClause().getStop().getCharPositionInLine() + 1);
        listenerHelper.verifyCodeBlockBracketStyle(Messages.IF_STATEMENT, conditionClauseLocation, ctx.codeBlock());
    }
}
