package de.effectivetrainings.teleprompter.adapter.inbound;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstructionParser {

    public static final String INSTRUCTION_DELIMITER = "###";

    //### type key value => ### format Hinweis Das ist ein wichtiger Hinweis
    private final Pattern instructionPattern = Pattern.compile(INSTRUCTION_DELIMITER + " ([\\w_-]+) ([\\w_-]+) (\\w+.*)");

    public Optional<Instruction> instruction(String line) {
        final Matcher matcher = instructionPattern.matcher(line);
        if (matcher.matches()) {
            return Optional.of(new Instruction(matcher.group(1), matcher.group(2), matcher.group(3)));
        } else {
            return Optional.empty();
        }
    }
}
