package de.effectivetrainings.teleprompter.adapter.outbound.rest.counter;

import de.effectivetrainings.teleprompter.domain.DisplayEntry;
import de.effectivetrainings.teleprompter.domain.EntryAddedEvent;
import de.effectivetrainings.teleprompter.domain.Event;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class GitCommandCounterTest {

    private GitCommandCounter gitCommandCounter;

    @Before
    public void setUp() throws Exception {
        gitCommandCounter = new GitCommandCounter();
    }

    @Test
    public void onEvent() {
        EntryAddedEvent entryAddedEvent = new EntryAddedEvent("sessionId", LocalDateTime.now(), new DisplayEntry(-1, "git status", LocalDateTime.now()));
        EntryAddedEvent entryAddedEvent2 = new EntryAddedEvent("sessionId", LocalDateTime.now(), new DisplayEntry(-1, "git diff", LocalDateTime.now()));
        EntryAddedEvent entryAddedEvent3 = new EntryAddedEvent("sessionId", LocalDateTime.now(), new DisplayEntry(-1, "git status", LocalDateTime.now()));

        List<Event> events = Lists.newArrayList(entryAddedEvent, entryAddedEvent2, entryAddedEvent3);
        final Map<Command, Integer> commandCounts = gitCommandCounter
                .apply(events)
                .getCommandCounts();
        assertEquals(commandCounts.get(Command.of("git status")), new Integer(2));
        assertEquals(commandCounts.get(Command.of("git diff")), new Integer(1));
    }
}