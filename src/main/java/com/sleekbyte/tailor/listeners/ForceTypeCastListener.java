package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

public class ForceTypeCastListener extends SwiftBaseListener {

    private Printer printer;

    public ForceTypeCastListener(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void enterTypeCastingOperator(SwiftParser.TypeCastingOperatorContext ctx) {
        verifyForceTypeCasting(ctx);
    }

    private void verifyForceTypeCasting(SwiftParser.TypeCastingOperatorContext ctx) {
        ParseTree secondChild = ctx.getChild(1);
        if (secondChild.getText().equals("!")) {
            // TODO: use util method that returns location of parse tree once {} check gets merged into master
            Location exclamationLocation = ListenerUtil.getTokenLocation(((TerminalNodeImpl) secondChild).getSymbol());
            printer.warn(Messages.FORCE_CAST, exclamationLocation);
        }
    }
}
