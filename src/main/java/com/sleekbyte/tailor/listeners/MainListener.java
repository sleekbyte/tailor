package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
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
    private Printer printer;

    /**
     * Creates a MainListener object and sets the printer in listenerHelper.
     *
     * @param printer    {@link Printer} used for outputting messages to user
     * @param maxLengths {@link MaxLengths} stores numbers for max length restrictions
     */
    public MainListener(Printer printer, MaxLengths maxLengths) {
        listenerHelper.setPrinter(printer);
        this.printer = printer;
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
    public void enterFunctionName(SwiftParser.FunctionNameContext ctx) {
        listenerHelper.verifyNameLength(Messages.FUNCTION + Messages.NAME, maxLengths.maxNameLength, ctx);
        listenerHelper.verifyLowerCamelCase(Messages.FUNCTION + Messages.NAMES, ctx);
    }

    @Override
    public void enterLabelName(SwiftParser.LabelNameContext ctx) {
        listenerHelper.verifyNameLength(Messages.LABEL + Messages.NAME, maxLengths.maxNameLength, ctx);
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

    @Override
    public void enterVariableName(SwiftParser.VariableNameContext ctx) {
        listenerHelper.verifyNameLength(Messages.VARIABLE + Messages.NAME, maxLengths.maxNameLength, ctx);
        listenerHelper.verifyLowerCamelCase(Messages.VARIABLE + Messages.NAMES, ctx);
    }

    @Override
    public void enterConstantDeclaration(SwiftParser.ConstantDeclarationContext ctx) {
        listenerHelper.evaluatePatternInitializerList(ctx.patternInitializerList(),
            new ConstantDecListener(this.printer, this.maxLengths));
    }

    @Override
    public void enterVariableDeclaration(SwiftParser.VariableDeclarationContext ctx) {
        if (ctx.patternInitializerList() == null) {
            return;
        }
        listenerHelper.evaluatePatternInitializerList(ctx.patternInitializerList(),
            new VariableDecListener(this.printer, this.maxLengths));
    }

    @Override
    public void enterValueBindingPattern(SwiftParser.ValueBindingPatternContext ctx) {
        ParseTreeWalker walker = new ParseTreeWalker();
        if (ctx.getStart().getText().equals(LET)) {
            listenerHelper.evaluatePattern(ctx.pattern(), walker,
                new ConstantDecListener(this.printer, this.maxLengths));
        } else if (ctx.getStart().getText().equals(VAR)) {
            listenerHelper.evaluatePattern(ctx.pattern(), walker,
                new VariableDecListener(this.printer, this.maxLengths));
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
            listenerHelper.evaluateOptionalBindingHead(ctx.optionalBindingHead(),
                new ConstantDecListener(this.printer, this.maxLengths));
        } else if (currentBinding.equals(VAR)) {
            listenerHelper.evaluateOptionalBindingHead(ctx.optionalBindingHead(),
                new VariableDecListener(this.printer, this.maxLengths));
        }
        SwiftParser.OptionalBindingContinuationListContext continuationList = ctx.optionalBindingContinuationList();
        if (continuationList != null) {
            for (SwiftParser.OptionalBindingContinuationContext continuation :
                    continuationList.optionalBindingContinuation()) {
                if (continuation.optionalBindingHead() != null) {
                    currentBinding = listenerHelper.letOrVar(continuation.optionalBindingHead());
                }
                if (currentBinding.equals(LET)) {
                    listenerHelper.evaluateOptionalBindingContinuation(continuation,
                        new ConstantDecListener(this.printer, this.maxLengths));
                } else if (currentBinding.equals(VAR)) {
                    listenerHelper.evaluateOptionalBindingContinuation(continuation,
                        new VariableDecListener(this.printer, this.maxLengths));
                }
            }
        }
    }

    @Override
    public void enterParameter(SwiftParser.ParameterContext ctx) {
        SwiftBaseListener listener = null;
        for (ParseTree child : ctx.children) {
            if (child.getText().equals(VAR)) {
                listener = new VariableDecListener(this.printer, this.maxLengths);
                break;
            }
        }
        if (listener == null) {
            listener = new ConstantDecListener(this.printer, this.maxLengths);
        }
        ParseTreeWalker walker = new ParseTreeWalker();
        if (ctx.externalParameterName() != null) {
            listenerHelper.walkListener(walker, ctx.externalParameterName(), listener);
        }
        listenerHelper.walkListener(walker, ctx.localParameterName(), listener);
    }

    @Override
    public void enterConditionClause(SwiftParser.ConditionClauseContext ctx) {
        listenerHelper.verifyRedundantExpressionParenthesis(Messages.CONDITIONAL_CLAUSE, ctx.expression());
    }

    @Override
    public void enterSwitchStatement(SwiftParser.SwitchStatementContext ctx) {
        listenerHelper.verifyRedundantExpressionParenthesis(Messages.SWITCH_EXPRESSION, ctx.expression());
        listenerHelper.verifySwitchStatementOpenBraceStyle(ctx);
    }

    @Override
    public void enterForStatement(SwiftParser.ForStatementContext ctx) {
        listenerHelper.verifyRedundantForLoopParenthesis(ctx);
        listenerHelper.verifyForLoopOpenBraceStyle(ctx);
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
        listenerHelper.verifyFunctionOpenBraceStyle(ctx);
    }

    @Override
    public void enterElseClause(SwiftParser.ElseClauseContext ctx) {
        listenerHelper.verifyElseClauseOpenBraceStyle(ctx);
    }

    @Override
    public void enterIfStatement(SwiftParser.IfStatementContext ctx) {
        listenerHelper.verifyIfStatementOpenBraceStyle(ctx);
    }

    @Override
    public void enterWhileStatement(SwiftParser.WhileStatementContext ctx) {
        listenerHelper.verifyWhileLoopOpenBraceStyle(ctx);
    }

    @Override
    public void enterRepeatWhileStatement(SwiftParser.RepeatWhileStatementContext ctx) {
        listenerHelper.verifyRepeatWhileLoopOpenBraceStyle(ctx);
    }

    @Override
    public void enterInitializerDeclaration(SwiftParser.InitializerDeclarationContext ctx) {
        listenerHelper.verifyInitializerOpenBraceStyle(ctx);
    }

    @Override
    public void enterForInStatement(SwiftParser.ForInStatementContext ctx) {
        listenerHelper.verifyForInStatementOpenBraceStyle(ctx);
    }

    @Override
    public void enterClassDeclaration(SwiftParser.ClassDeclarationContext ctx) {
        listenerHelper.verifyClassOpenBraceStyle(ctx);
    }

    @Override
    public void enterStructDeclaration(SwiftParser.StructDeclarationContext ctx) {
        listenerHelper.verifyStructOpenBraceStyle(ctx);
    }

    @Override
    public void enterProtocolDeclaration(SwiftParser.ProtocolDeclarationContext ctx) {
        listenerHelper.verifyProtocolOpenBraceStyle(ctx);
    }

    @Override
    public void enterUnionStyleEnum(SwiftParser.UnionStyleEnumContext ctx) {
        listenerHelper.verifyUnionStyleEnumOpenBraceStyle(ctx);
    }
}
