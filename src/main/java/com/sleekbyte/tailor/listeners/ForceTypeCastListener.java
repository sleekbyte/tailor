package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Parse tree listener for force type cast checks.
 */
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
            Location exclamationLocation = ListenerUtil.getParseTreeStartLocation(secondChild);
            printer.warn(Rules.FORCED_TYPE_CAST, Messages.FORCE_CAST, exclamationLocation);
        }
    }
}
