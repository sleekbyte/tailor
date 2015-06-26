package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.antlr.SwiftBaseListener;

/**
 * Parse tree listener for verifying Swift constructs
 */
public class MainListener extends SwiftBaseListener {

    private static MainListenerHelper listenerHelper;

    public MainListener(Printer printer) {
        listenerHelper = new MainListenerHelper(printer);
    }

    @Override
    public void enterClassName(SwiftParser.ClassNameContext ctx) {
        listenerHelper.verifyUpperCamelCase(Messages.CLASS_NAME, ctx);
    }

    @Override
    public void enterEnumName(SwiftParser.EnumNameContext ctx) {
        listenerHelper.verifyUpperCamelCase(Messages.ENUM_NAME, ctx);
    }

    @Override
    public void enterEnumCaseName(SwiftParser.EnumCaseNameContext ctx) {
        listenerHelper.verifyUpperCamelCase(Messages.ENUM_CASE_NAME, ctx);
    }

    @Override
    public void enterStructName(SwiftParser.StructNameContext ctx) {
        listenerHelper.verifyUpperCamelCase(Messages.STRUCT_NAME, ctx);
    }

    @Override
    public void enterProtocolName(SwiftParser.ProtocolNameContext ctx) {
        listenerHelper.verifyUpperCamelCase(Messages.PROTOCOL_NAME, ctx);
    }

    @Override
    public void enterStatement(SwiftParser.StatementContext ctx) {
        listenerHelper.verifyNotSemicolonTerminated(Messages.STATEMENT, ctx);
    }

    @Override
    public void enterDeclaration(SwiftParser.DeclarationContext ctx) {
        listenerHelper.verifyNotSemicolonTerminated(Messages.STATEMENT, ctx);
    }

    @Override
    public void enterLoopStatement(SwiftParser.LoopStatementContext ctx) {
        listenerHelper.verifyNotSemicolonTerminated(Messages.STATEMENT, ctx);
    }

    @Override
    public void enterBranchStatement(SwiftParser.BranchStatementContext ctx) {
        listenerHelper.verifyNotSemicolonTerminated(Messages.STATEMENT, ctx);
    }

    @Override
    public void enterLabeledStatement(SwiftParser.LabeledStatementContext ctx) {
        listenerHelper.verifyNotSemicolonTerminated(Messages.STATEMENT, ctx);
    }

    @Override
    public void enterControlTransferStatement(SwiftParser.ControlTransferStatementContext ctx) {
        listenerHelper.verifyNotSemicolonTerminated(Messages.STATEMENT, ctx);
    }

    @Override
    public void enterUnionStyleEnumMember(SwiftParser.UnionStyleEnumMemberContext ctx) {
        listenerHelper.verifyNotSemicolonTerminated(Messages.STATEMENT, ctx);
    }

    @Override
    public void enterProtocolMemberDeclaration(SwiftParser.ProtocolMemberDeclarationContext ctx) {
        listenerHelper.verifyNotSemicolonTerminated(Messages.STATEMENT, ctx);
    }

}
