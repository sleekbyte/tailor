package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.antlr.SwiftParser.ClosureParameterNameContext;
import com.sleekbyte.tailor.antlr.SwiftParser.IdentifierContext;
import com.sleekbyte.tailor.antlr.SwiftParser.TopLevelContext;
import com.sleekbyte.tailor.antlr.SwiftParser.VariableNameContext;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.ArrayList;
import java.util.List;

/**
 * Parse tree listener for declaration constructs.
 */
public class DeclarationListener extends SwiftBaseListener {

    private static final String LET = "let";
    private static final String VAR = "var";
    private Declaration declarationType;
    private List<IdentifierContext> declarations;

    /**
     * Extracts identifier that's in the subtree of a given context.
     */
    private static class IdentifierExtractor extends SwiftBaseListener {

        private IdentifierContext ctx;

        @Override
        public void enterIdentifier(IdentifierContext ctx) {
            this.ctx = ctx;
        }

        public IdentifierContext getIdentifier() {
            return ctx;
        }
    }

    /**
     * Declaration types handled by this listener.
     */
    public enum Declaration { VARIABLE_DEC, CONSTANT_DEC }

    /**
     * Creates a DeclarationListener object.
     *
     * @param decl Type of declarations to search for
     */
    private DeclarationListener(Declaration decl) {
        this.declarationType = decl;
        declarations = new ArrayList<>();
    }

    public List<IdentifierContext> getDeclarations() {
        return declarations;
    }

    private static List<IdentifierContext> getDeclarationNames(TopLevelContext ctx, Declaration decl) {
        DeclarationListener listener = new DeclarationListener(decl);
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, ctx);
        return listener.getDeclarations();
    }

    public static List<IdentifierContext> getConstantNames(TopLevelContext ctx) {
        return getDeclarationNames(ctx, Declaration.CONSTANT_DEC);
    }

    public static List<IdentifierContext> getVariableNames(TopLevelContext ctx) {
        return getDeclarationNames(ctx, Declaration.VARIABLE_DEC);
    }

    private String getKeyword() {
        switch (declarationType) {
            case CONSTANT_DEC: return LET;
            case VARIABLE_DEC: return VAR;
            default: return "";
        }
    }

    @Override
    public void enterConstantDeclaration(SwiftParser.ConstantDeclarationContext ctx) {
        if (declarationType.equals(Declaration.CONSTANT_DEC)) {
            evaluatePatternInitializerList(ctx.patternInitializerList());
        }
    }

    @Override
    public void enterVariableDeclaration(SwiftParser.VariableDeclarationContext ctx) {
        if (!declarationType.equals(Declaration.VARIABLE_DEC) || ctx.patternInitializerList() == null) {
            return;
        }
        evaluatePatternInitializerList(ctx.patternInitializerList());
    }

    @Override
    public void enterClosureParameterName(ClosureParameterNameContext ctx) {
        if (declarationType.equals(Declaration.CONSTANT_DEC)) {
            extractIdentifier(ctx);
        }
    }

    @Override
    public void enterVariableName(VariableNameContext ctx) {
        if (declarationType.equals(Declaration.VARIABLE_DEC)) {
            extractIdentifier(ctx);
        }
    }

    @Override
    public void enterValueBindingPattern(SwiftParser.ValueBindingPatternContext ctx) {
        if (ctx.getStart().getText().equals(getKeyword())) {
            evaluatePattern(ctx.pattern());
        }
    }

    @Override
    public void enterParameter(SwiftParser.ParameterContext ctx) {
        if (!declarationType.equals(Declaration.CONSTANT_DEC)) {
            return;
        }

        if (ctx.externalParameterName() != null) {
            extractIdentifier(ctx.externalParameterName());
        }
        extractIdentifier(ctx.localParameterName());
    }

    @Override
    public void enterOptionalBindingCondition(SwiftParser.OptionalBindingConditionContext ctx) {
        String currentBinding = ctx.getChild(0).getText();
        if (currentBinding.equals(getKeyword())) {
            evaluatePattern(ctx.pattern());
        }
    }

    private void extractIdentifier(ParserRuleContext ctx) {
        IdentifierExtractor extractor = new IdentifierExtractor();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(extractor, ctx);
        if (extractor.getIdentifier() != null) {
            declarations.add(extractor.getIdentifier());
        }
    }

    private void evaluatePatternInitializerList(SwiftParser.PatternInitializerListContext ctx) {
        for (SwiftParser.PatternInitializerContext context : ctx.patternInitializer()) {
            SwiftParser.PatternContext pattern = context.pattern();
            evaluatePattern(pattern);
        }
    }

    private void evaluatePattern(SwiftParser.PatternContext pattern) {
        if (pattern.identifierPattern() != null) {
            extractIdentifier(pattern.identifierPattern());

        } else if (pattern.tuplePattern() != null && pattern.tuplePattern().tuplePatternElementList() != null) {
            evaluateTuplePattern(pattern.tuplePattern());

        } else if (pattern.enumCasePattern() != null && pattern.enumCasePattern().tuplePattern() != null) {
            evaluateTuplePattern(pattern.enumCasePattern().tuplePattern());

        } else if (pattern.pattern() != null) {
            evaluatePattern(pattern.pattern());

        } else if (pattern.expressionPattern() != null) {
            extractIdentifier(pattern.expressionPattern().expression().prefixExpression());
        }
    }

    private void evaluateTuplePattern(SwiftParser.TuplePatternContext tuplePatternContext) {
        List<SwiftParser.TuplePatternElementContext> tuplePatternElementContexts =
            tuplePatternContext.tuplePatternElementList().tuplePatternElement();

        for (SwiftParser.TuplePatternElementContext tuplePatternElement : tuplePatternElementContexts) {
            evaluatePattern(tuplePatternElement.pattern());
        }
    }
}
