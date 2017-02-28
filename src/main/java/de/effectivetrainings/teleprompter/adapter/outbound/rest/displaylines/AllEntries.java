package de.effectivetrainings.teleprompter.adapter.outbound.rest.displaylines;

import de.effectivetrainings.teleprompter.adapter.outbound.rest.ViewEntry;
import de.effectivetrainings.teleprompter.adapter.outbound.rest.ViewEntryMapper;
import de.effectivetrainings.teleprompter.adapter.outbound.rest.ViewRendererConfig;
import de.effectivetrainings.teleprompter.domain.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class AllEntries implements Aggregate<Event> {

    private List<ViewEntry> displayLines = new ArrayList<>();

    @NonNull
    private ViewEntryMapper viewEntryMapper;
    @NonNull
    private ViewRendererConfig viewRendererConfig;
    @NonNull
    private DescriptionRepository descriptionRepository;

    public List<ViewEntry> getDisplayLines() {
        return Collections.unmodifiableList(displayLines);
    }

    @Override
    public AllEntries apply(List<Event> events) {
        events
                .stream()
                .forEach(this::on);
        return this;
    }

    @Override
    public void on(Event event) {
        if (event instanceof EntryAddedEvent) {
            this.onDisplayEntryAdded((EntryAddedEvent) event);
        }
    }


    public void onDisplayEntryAdded(EntryAddedEvent entryAddedEvent) {
        if (entryAddedEvent
                .getContent()
                .getContent()
                .startsWith(viewRendererConfig.getPrefix())) {
            final Optional<Description> description = descriptionRepository
                    .findByKey(entryAddedEvent
                            .getContent()
                            .getContent());
            final ViewEntry viewEntry = viewEntryMapper.map(entryAddedEvent.getContent(), description);
            this.displayLines.add(viewEntry);
        }
    }
}

