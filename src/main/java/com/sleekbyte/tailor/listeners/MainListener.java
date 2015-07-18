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
    private ListenerHelper helper;

    /**
     * Creates a MainListener object and a new listener helper.
     *
     * @param printer    {@link Printer} used for outputting messages to user
     * @param maxLengths {@link MaxLengths} stores numbers for max length restrictions
     */
    public MainListener(Printer printer, MaxLengths maxLengths) {
        this.helper = ListenerHelper.INSTANCE;
        helper.reset();
        helper.setPrinter(printer);
        helper.setMaxLengths(maxLengths);
    }

    @Override
    public void enterClassName(SwiftParser.ClassNameContext ctx) {
        helper.verifyUpperCamelCase(Messages.CLASS + Messages.NAMES, ctx);
        helper.verifyNameLength(Messages.CLASS + Messages.NAME, helper.getMaxLengths().maxNameLength, ctx);
    }

    @Override
    public void enterEnumName(SwiftParser.EnumNameContext ctx) {
        helper.verifyUpperCamelCase(Messages.ENUM + Messages.NAMES, ctx);
        helper.verifyNameLength(Messages.ENUM + Messages.NAME, helper.getMaxLengths().maxNameLength, ctx);
    }

    @Override
    public void enterEnumCaseName(SwiftParser.EnumCaseNameContext ctx) {
        helper.verifyUpperCamelCase(Messages.ENUM_CASE + Messages.NAMES, ctx);
        helper.verifyNameLength(Messages.ENUM_CASE + Messages.NAME, helper.getMaxLengths().maxNameLength, ctx);
    }

    @Override
    public void enterStructName(SwiftParser.StructNameContext ctx) {
        helper.verifyUpperCamelCase(Messages.STRUCT + Messages.NAMES, ctx);
        helper.verifyNameLength(Messages.STRUCT + Messages.NAME, helper.getMaxLengths().maxNameLength, ctx);
    }

    @Override
    public void enterProtocolName(SwiftParser.ProtocolNameContext ctx) {
        helper.verifyUpperCamelCase(Messages.PROTOCOL + Messages.NAMES, ctx);
        helper.verifyNameLength(Messages.PROTOCOL + Messages.NAME, helper.getMaxLengths().maxNameLength, ctx);
    }

    @Override
    public void enterStatement(SwiftParser.StatementContext ctx) {
        helper.verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterDeclaration(SwiftParser.DeclarationContext ctx) {
        helper.verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterLoopStatement(SwiftParser.LoopStatementContext ctx) {
        helper.verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterBranchStatement(SwiftParser.BranchStatementContext ctx) {
        helper.verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterLabeledStatement(SwiftParser.LabeledStatementContext ctx) {
        helper.verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterControlTransferStatement(SwiftParser.ControlTransferStatementContext ctx) {
        helper.verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterUnionStyleEnumMember(SwiftParser.UnionStyleEnumMemberContext ctx) {
        helper.verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterProtocolMemberDeclaration(SwiftParser.ProtocolMemberDeclarationContext ctx) {
        helper.verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterClassBody(SwiftParser.ClassBodyContext ctx) {
        helper.verifyConstructLength(Messages.CLASS, helper.getMaxLengths().maxClassLength, ctx);
        helper.verifyClassOpenBraceStyle(ctx);
    }

    @Override
    public void enterClosureExpression(SwiftParser.ClosureExpressionContext ctx) {
        helper.verifyConstructLength(Messages.CLOSURE, helper.getMaxLengths().maxClosureLength, ctx);
        helper.verifyClosureExpressionOpenBraceStyle(ctx);
    }

    @Override
    public void enterFunctionBody(SwiftParser.FunctionBodyContext ctx) {
        helper.verifyConstructLength(Messages.FUNCTION, helper.getMaxLengths().maxFunctionLength, ctx);
    }

    @Override
    public void enterStructBody(SwiftParser.StructBodyContext ctx) {
        helper.verifyConstructLength(Messages.STRUCT, helper.getMaxLengths().maxStructLength, ctx);
        helper.verifyStructOpenBraceStyle(ctx);
    }

    @Override
    public void enterElementName(SwiftParser.ElementNameContext ctx) {
        helper.verifyNameLength(Messages.ELEMENT + Messages.NAME, helper.getMaxLengths().maxNameLength, ctx);
    }

    @Override
    public void enterFunctionName(SwiftParser.FunctionNameContext ctx) {
        helper.verifyNameLength(Messages.FUNCTION + Messages.NAME, helper.getMaxLengths().maxNameLength, ctx);
        helper.verifyLowerCamelCase(Messages.FUNCTION + Messages.NAMES, ctx);
    }

    @Override
    public void enterLabelName(SwiftParser.LabelNameContext ctx) {
        helper.verifyNameLength(Messages.LABEL + Messages.NAME, helper.getMaxLengths().maxNameLength, ctx);
    }

    @Override
    public void enterSetterName(SwiftParser.SetterNameContext ctx) {
        helper.verifyNameLength(Messages.SETTER + Messages.NAME, helper.getMaxLengths().maxNameLength,
            ctx.identifier());
    }

    @Override
    public void enterTypeName(SwiftParser.TypeNameContext ctx) {
        helper.verifyNameLength(Messages.TYPE + Messages.NAME, helper.getMaxLengths().maxNameLength, ctx);
    }

    @Override
    public void enterTypealiasName(SwiftParser.TypealiasNameContext ctx) {
        helper.verifyNameLength(Messages.TYPEALIAS + Messages.NAME, helper.getMaxLengths().maxNameLength, ctx);
    }

    @Override
    public void enterVariableName(SwiftParser.VariableNameContext ctx) {
        helper.verifyNameLength(Messages.VARIABLE + Messages.NAME, helper.getMaxLengths().maxNameLength, ctx);
        helper.verifyLowerCamelCase(Messages.VARIABLE + Messages.NAMES, ctx);
    }

    @Override
    public void enterConstantDeclaration(SwiftParser.ConstantDeclarationContext ctx) {
        helper.evaluatePatternInitializerList(ctx.patternInitializerList(), new ConstantDecListener(this.helper));
    }

    @Override
    public void enterVariableDeclaration(SwiftParser.VariableDeclarationContext ctx) {
        if (ctx.patternInitializerList() == null) {
            return;
        }
        helper.evaluatePatternInitializerList(ctx.patternInitializerList(), new VariableDecListener(this.helper));
    }

    @Override
    public void enterValueBindingPattern(SwiftParser.ValueBindingPatternContext ctx) {
        ParseTreeWalker walker = new ParseTreeWalker();
        if (ctx.getStart().getText().equals(LET)) {
            helper.evaluatePattern(ctx.pattern(), walker, new ConstantDecListener(this.helper));
        } else if (ctx.getStart().getText().equals(VAR)) {
            helper.evaluatePattern(ctx.pattern(), walker, new VariableDecListener(this.helper));
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
        String currentBinding = helper.letOrVar(ctx.optionalBindingHead());
        if (currentBinding.equals(LET)) {
            helper.evaluateOptionalBindingHead(ctx.optionalBindingHead(), new ConstantDecListener(this.helper));
        } else if (currentBinding.equals(VAR)) {
            helper.evaluateOptionalBindingHead(ctx.optionalBindingHead(), new VariableDecListener(this.helper));
        }
        SwiftParser.OptionalBindingContinuationListContext continuationList = ctx.optionalBindingContinuationList();
        if (continuationList != null) {
            for (SwiftParser.OptionalBindingContinuationContext continuation :
                    continuationList.optionalBindingContinuation()) {
                if (continuation.optionalBindingHead() != null) {
                    currentBinding = helper.letOrVar(continuation.optionalBindingHead());
                }
                if (currentBinding.equals(LET)) {
                    helper.evaluateOptionalBindingContinuation(continuation, new ConstantDecListener(this.helper));
                } else if (currentBinding.equals(VAR)) {
                    helper.evaluateOptionalBindingContinuation(continuation, new VariableDecListener(this.helper));
                }
            }
        }
    }

    @Override
    public void enterParameter(SwiftParser.ParameterContext ctx) {
        SwiftBaseListener listener = null;
        for (ParseTree child : ctx.children) {
            if (child.getText().equals(VAR)) {
                listener = new VariableDecListener(this.helper);
                break;
            }
        }
        if (listener == null) {
            listener = new ConstantDecListener(this.helper);
        }
        ParseTreeWalker walker = new ParseTreeWalker();
        if (ctx.externalParameterName() != null) {
            helper.walkListener(walker, ctx.externalParameterName(), listener);
        }
        helper.walkListener(walker, ctx.localParameterName(), listener);
    }

    @Override
    public void enterConditionClause(SwiftParser.ConditionClauseContext ctx) {
        helper.verifyRedundantExpressionParenthesis(Messages.CONDITIONAL_CLAUSE, ctx.expression());
    }

    @Override
    public void enterSwitchStatement(SwiftParser.SwitchStatementContext ctx) {
        helper.verifyRedundantExpressionParenthesis(Messages.SWITCH_EXPRESSION, ctx.expression());
        helper.verifySwitchStatementOpenBraceStyle(ctx);
    }

    @Override
    public void enterForStatement(SwiftParser.ForStatementContext ctx) {
        helper.verifyRedundantForLoopParenthesis(ctx);
        helper.verifyForLoopOpenBraceStyle(ctx);
    }

    @Override
    public void enterThrowStatement(SwiftParser.ThrowStatementContext ctx) {
        helper.verifyRedundantExpressionParenthesis(Messages.THROW_STATEMENT, ctx.expression());
    }

    @Override
    public void enterCatchClause(SwiftParser.CatchClauseContext ctx) {
        helper.verifyRedundantCatchParentheses(ctx.pattern());
    }

    @Override
    public void enterInitializer(SwiftParser.InitializerContext ctx) {
        helper.verifyRedundantExpressionParenthesis(Messages.INITIALIZER_EXPRESSION, ctx.expression());
    }

    @Override
    public void enterArrayLiteralItem(SwiftParser.ArrayLiteralItemContext ctx) {
        helper.verifyRedundantExpressionParenthesis(Messages.ARRAY_LITERAL, ctx.expression());
    }

    @Override
    public void enterDictionaryLiteralItem(SwiftParser.DictionaryLiteralItemContext ctx) {
        for (SwiftParser.ExpressionContext expressionContext : ctx.expression()) {
            helper.verifyRedundantExpressionParenthesis(Messages.DICTIONARY_LITERAL, expressionContext);
        }
        helper.checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterImportDeclaration(SwiftParser.ImportDeclarationContext ctx) {
        helper.verifyMultipleImports(ctx);
    }

    @Override
    public void enterFunctionDeclaration(SwiftParser.FunctionDeclarationContext ctx) {
        helper.verifyFunctionOpenBraceStyle(ctx);
    }

    @Override
    public void enterElseClause(SwiftParser.ElseClauseContext ctx) {
        helper.verifyElseClauseOpenBraceStyle(ctx);
    }

    @Override
    public void enterIfStatement(SwiftParser.IfStatementContext ctx) {
        helper.verifyIfStatementOpenBraceStyle(ctx);
    }

    @Override
    public void enterWhileStatement(SwiftParser.WhileStatementContext ctx) {
        helper.verifyWhileLoopOpenBraceStyle(ctx);
    }

    @Override
    public void enterRepeatWhileStatement(SwiftParser.RepeatWhileStatementContext ctx) {
        helper.verifyRepeatWhileLoopOpenBraceStyle(ctx);
    }

    @Override
    public void enterInitializerDeclaration(SwiftParser.InitializerDeclarationContext ctx) {
        helper.verifyInitializerOpenBraceStyle(ctx);
    }

    @Override
    public void enterForInStatement(SwiftParser.ForInStatementContext ctx) {
        helper.verifyForInStatementOpenBraceStyle(ctx);
    }

    @Override
    public void enterOperatorDeclaration(SwiftParser.OperatorDeclarationContext ctx) {
        helper.checkWhitespaceAroundOperator(ctx);
    }

    @Override
    public void enterTypeAnnotation(SwiftParser.TypeAnnotationContext ctx) {
        helper.checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterProtocolBody(SwiftParser.ProtocolBodyContext ctx) {
        helper.verifyProtocolOpenBraceStyle(ctx);
    }

    @Override
    public void enterUnionStyleEnum(SwiftParser.UnionStyleEnumContext ctx) {
        helper.verifyEnumOpenBraceStyle(ctx);
    }

    @Override
    public void enterRawValueStyleEnum(SwiftParser.RawValueStyleEnumContext ctx) {
        helper.verifyEnumOpenBraceStyle(ctx);
    }

    @Override
    public void enterDictionaryType(SwiftParser.DictionaryTypeContext ctx) {
        helper.checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterSwitchCase(SwiftParser.SwitchCaseContext ctx) {
        helper.checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterTypeInheritanceClause(SwiftParser.TypeInheritanceClauseContext ctx) {
        helper.checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterConditionalOperator(SwiftParser.ConditionalOperatorContext ctx) {
        helper.checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterExpressionElement(SwiftParser.ExpressionElementContext ctx) {
        helper.checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterGenericParameter(SwiftParser.GenericParameterContext ctx) {
        helper.checkWhitespaceAroundColon(ctx);
    }

    @Override
    public void enterExtensionBody(SwiftParser.ExtensionBodyContext ctx) {
        helper.verifyExtensionOpenBraceStyle(ctx);
    }

    @Override
    public void enterGetterClause(SwiftParser.GetterClauseContext ctx) {
        helper.verifyGetterOpenBraceStyle(ctx);
    }

    @Override
    public void enterSetterClause(SwiftParser.SetterClauseContext ctx) {
        helper.verifySetterOpenBraceStyle(ctx);
    }
}
