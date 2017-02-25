package de.effectivetrainings.teleprompter.adapter.inbound;

import de.effectivetrainings.teleprompter.domain.EntryAddedEvent;
import de.effectivetrainings.teleprompter.domain.Event;
import de.effectivetrainings.teleprompter.infrastructure.EventStore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LineReadHandlerTest {
    @Mock
    private EventStore eventStore;
    @Captor
    private ArgumentCaptor<Event> eventCaptor;

    private LineReadHandler testee;

    @Before
    public void setUp() throws Exception {
        testee = new LineReadHandler(eventStore, new InstructionParser());
    }

    @Test
    public void parseInstruction() {
        final String instructionKey = "session";
        final String instructionValue = "1234";
        testee.parse("session", new ParsedLine(1, "### " + instructionKey + " " + instructionValue, LocalDateTime.now()));
        verify(eventStore).put(eventCaptor.capture());

        final Event event = eventCaptor.getValue();
    }

    @Test
    public void parseDisplay() {
        final String content = "Hello World";
        testee.parse("session", new ParsedLine(1, content, LocalDateTime.now()));
        verify(eventStore).put(eventCaptor.capture());

        final Event event = eventCaptor.getValue();
        assertTrue(event instanceof EntryAddedEvent);
        assertEquals(content, ((EntryAddedEvent) event).getContent().getContent());
    }
}