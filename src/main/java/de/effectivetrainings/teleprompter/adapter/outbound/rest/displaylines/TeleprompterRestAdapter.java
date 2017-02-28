package de.effectivetrainings.teleprompter.adapter.outbound.rest.displaylines;

import de.effectivetrainings.teleprompter.adapter.outbound.rest.ViewEntry;
import de.effectivetrainings.teleprompter.adapter.outbound.rest.ViewEntryMapper;
import de.effectivetrainings.teleprompter.adapter.outbound.rest.ViewRendererConfig;
import de.effectivetrainings.teleprompter.domain.DescriptionRepository;
import de.effectivetrainings.teleprompter.domain.Event;
import de.effectivetrainings.teleprompter.infrastructure.EventStore;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeleprompterRestAdapter {

    @NonNull
    private EventStore eventStore;
    @NonNull
    private ViewRendererConfig viewRendererConfig;
    @NonNull
    private DescriptionRepository descriptionRepository;


    @RequestMapping(value = "/entries/{sessionId}")
    public List<ViewEntry> entries(@PathVariable("sessionId") String sessionId) {
        final List<Event> events = eventStore.events(sessionId);
        final List<ViewEntry> displayLines = new ArrayList<>(new AllEntries(new ViewEntryMapper(), viewRendererConfig, descriptionRepository)
                .apply(events)
                .getDisplayLines());
        Collections.reverse(displayLines);
        return displayLines;
    }
}
