package de.effectivetrainings.teleprompter.adapter.outbound.rest.counter;

import de.effectivetrainings.teleprompter.domain.Aggregate;
import de.effectivetrainings.teleprompter.domain.EntryAddedEvent;
import de.effectivetrainings.teleprompter.domain.Event;

import java.util.*;

public class GitCommandCounter implements Aggregate<Event> {

    private Map<Command, Integer> commandCounts = new HashMap<>();

    private static final CommandParser commandParser = new CommandParser("(git \\w+) *.*");

    public Map<Command, Integer> getCommandCounts() {
        return Collections.unmodifiableMap(commandCounts);
    }

    @Override
    public GitCommandCounter apply(List<Event> events) {
        events
                .stream()
                .forEach(this::on);
        return this;
    }

    @Override
    public void on(Event event) {
        if (event instanceof EntryAddedEvent) {
            final Optional<Command> commandString = commandParser.getCommandString(((EntryAddedEvent) event)
                    .getContent()
                    .getContent());
            commandString.ifPresent(c -> {
                commandCounts.compute(c, (command, current)-> current != null ? ++current : 1);
            });
        }
    }
}
