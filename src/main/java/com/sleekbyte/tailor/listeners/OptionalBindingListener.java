package com.sleekbyte.tailor.listeners;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.antlr.SwiftParser.OptionalBindingConditionContext;
import com.sleekbyte.tailor.antlr.SwiftParser.OptionalBindingContinuationContext;
import com.sleekbyte.tailor.antlr.SwiftParser.OptionalBindingHeadContext;
import com.sleekbyte.tailor.common.Location;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.utils.ListenerUtil;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Parse tree listener for verifying redundant optional bindings.
 */
public final class OptionalBindingListener extends SwiftBaseListener {

    private Printer printer;

    public OptionalBindingListener(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void enterOptionalBindingContinuationList(SwiftParser.OptionalBindingContinuationListContext ctx) {
        OptionalBindingConditionContext bindingCondition = (OptionalBindingConditionContext)ctx.getParent();
        String lastBindingHead = bindingCondition.optionalBindingHead().getChild(0).getText();

        for (ParseTree child : ctx.children) {
            if (child instanceof OptionalBindingContinuationContext) {
                OptionalBindingHeadContext head = ((OptionalBindingContinuationContext) child).optionalBindingHead();
                if (head == null) {
                    continue;
                }
                if (head.getChild(0).getText().equals(lastBindingHead)) {
                    Location location = ListenerUtil.getContextStartLocation(head);
                    printer.error(Rules.REDUNDANT_OPTIONAL_BINDING,
                        Messages.REDUNDANT + "'" + lastBindingHead + "' " + Messages.AT_COLUMN + location.column
                            + Messages.REDUNDANT_OPTIONAL_BINDING, location);
                } else {
                    lastBindingHead = head.getChild(0).getText();
                }
            }
        }
    }
}
