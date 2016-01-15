package com.sleekbyte.tailor.format;

import com.sleekbyte.tailor.output.ViolationMessage;

import java.io.IOException;
import java.util.List;

public interface Formatter {

    public void displayViolationMessages(List<ViolationMessage> violationMessages) throws IOException;

    public void displayParseErrorMessage() throws IOException;
}
