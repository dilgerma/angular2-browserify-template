package de.effectivetrainings.teleprompter.adapter.outbound.rest;

import de.effectivetrainings.teleprompter.domain.Event;
import de.effectivetrainings.teleprompter.infrastructure.EventStore;
import lombok.NonNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeleprompterRestAdapter {

    private EventStore eventStore;
    private ViewRendererConfig viewRendererConfig;

    public TeleprompterRestAdapter(@NonNull EventStore eventStore, @NonNull ViewRendererConfig viewRendererConfig) {
        this.eventStore = eventStore;
        this.viewRendererConfig = viewRendererConfig;
    }

    @RequestMapping(value = "/entries/{sessionId}")
    public List<ViewEntry> entries(@PathVariable("sessionId") String sessionId) {
        final List<Event> events = eventStore.events(sessionId);
        return new ViewEngine(new ViewEntryMapper(), viewRendererConfig).apply(events).getDisplayLines();
    }
}
