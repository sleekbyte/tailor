package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Parse tree listener for verifying semicolon termination.
 */
public class SemicolonTerminatedListener extends SwiftBaseListener {

    private Printer printer;

    public SemicolonTerminatedListener(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void enterStatement(SwiftParser.StatementContext ctx) {
        verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterDeclaration(SwiftParser.DeclarationContext ctx) {
        verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterLoopStatement(SwiftParser.LoopStatementContext ctx) {
        verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterBranchStatement(SwiftParser.BranchStatementContext ctx) {
        verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterLabeledStatement(SwiftParser.LabeledStatementContext ctx) {
        verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterControlTransferStatement(SwiftParser.ControlTransferStatementContext ctx) {
        verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterProtocolMemberDeclaration(SwiftParser.ProtocolMemberDeclarationContext ctx) {
        verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    @Override
    public void enterUnionStyleEnumMember(SwiftParser.UnionStyleEnumMemberContext ctx) {
        verifyNotSemicolonTerminated(Messages.STATEMENTS, ctx);
    }

    private void verifyNotSemicolonTerminated(String constructType, ParserRuleContext ctx) {
        String construct = ctx.getText();
        if (construct.endsWith(";")) {
            Location location = ListenerUtil.getContextStopLocation(ctx);
            this.printer.error(Rules.TERMINATING_SEMICOLON, constructType + Messages.SEMICOLON, location);
        }
    }
}
