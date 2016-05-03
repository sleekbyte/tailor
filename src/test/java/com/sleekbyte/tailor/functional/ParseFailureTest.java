package com.sleekbyte.tailor.functional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import com.sleekbyte.tailor.Tailor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Tests for parse failures.
 */
@RunWith(MockitoJUnitRunner.class)
public final class ParseFailureTest extends RuleTest {

    @Override
    protected void addAllExpectedMsgs() {
        this.expectedMessages.add(
            String.format("%s.swift", this.getClass().getSimpleName())
                + " could not be parsed successfully, skipping...");
    }

    @Test
    public void testRule() throws IOException {
        String[] command = Stream.concat(Arrays.stream(this.getCommandArgs()), Arrays.stream(this.getDefaultArgs()))
            .toArray(String[]::new);
        addAllExpectedMsgs();

        Tailor.main(command);

        List<String> actualOutput = new ArrayList<>();

        String[] msgs = outContent.toString(Charset.defaultCharset().name()).split(NEWLINE_REGEX);
        String summary = msgs[msgs.length - 1];
        // Skip first four lines for progress and file header, last two lines for summary
        msgs = Arrays.copyOfRange(msgs, 4, msgs.length - 2);

        for (String msg : msgs) {
            String truncatedMsg = msg.substring(msg.indexOf(inputFile.getName()));
            actualOutput.add(truncatedMsg);
        }
        assertThat(summary, containsString("skipped 1"));
        // Ensure number of warnings in summary equals actual number of warnings in the output
        assertThat(summary, containsString("0 violations"));
        assertArrayEquals(outContent.toString(Charset.defaultCharset().name()), this.expectedMessages.toArray(),
            actualOutput.toArray());
    }
}

