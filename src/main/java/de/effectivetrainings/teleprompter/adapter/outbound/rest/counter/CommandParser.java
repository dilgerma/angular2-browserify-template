package de.effectivetrainings.teleprompter.adapter.outbound.rest.counter;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {

    private final Pattern pattern;

    public CommandParser(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    public Optional<Command> getCommandString(String command) {
        final Matcher matcher = pattern.matcher(command);
        return matcher.matches() ? Optional.of(Command.of(matcher.group(1))) : Optional.empty();
    }
}
