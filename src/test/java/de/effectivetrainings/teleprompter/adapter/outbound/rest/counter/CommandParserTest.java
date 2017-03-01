package de.effectivetrainings.teleprompter.adapter.outbound.rest.counter;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandParserTest {

    private final CommandParser commandParser = new CommandParser("(git \\w+) *.*");

    @Test
    public void parseCommand() {
        final Optional<Command> commandString = commandParser.getCommandString("git status");
        assertTrue(commandString.isPresent());
        assertEquals("git status", commandString.get().getCommand());

    }
}