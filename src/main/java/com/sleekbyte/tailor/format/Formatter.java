package com.sleekbyte.tailor.format;

import com.sleekbyte.tailor.output.ViolationMessage;

import java.io.IOException;
import java.util.List;

/**
 * Interface used by the Printer class to display violation messages/errors.
 * New formats can be added by implementing this interface.
 */
public interface Formatter {

    public void displayViolationMessages(List<ViolationMessage> violationMessages) throws IOException;

    public void displayParseErrorMessage() throws IOException;
}
