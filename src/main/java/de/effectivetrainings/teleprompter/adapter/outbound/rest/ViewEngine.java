package de.effectivetrainings.teleprompter.adapter.outbound.rest;

import de.effectivetrainings.teleprompter.domain.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class ViewEngine implements EventHandler<Event> {

    public static final String INSTRUCTION_SESSION = "session";
    public static final String INSTRUCTION_SESSION_CLEAR = "clear";
    public static final String INSTRUCTION_HINT = "hint";

    private List<ViewEntry> displayLines = new ArrayList<>();

    private ViewEntryMapper viewEntryMapper;
    private ViewRendererConfig viewRendererConfig;

    public ViewEngine(@NonNull ViewEntryMapper viewEntryMapper, @NonNull ViewRendererConfig viewRendererConfig) {
        this.viewEntryMapper = viewEntryMapper;
        this.viewRendererConfig = viewRendererConfig;
    }

    public List<ViewEntry> getDisplayLines() {
        return Collections.unmodifiableList(displayLines);
    }

    @Override
    public ViewEngine apply(List<Event> events) {
        events.stream().forEach(this::on);
        return this;
    }

    @Override
    public void on(Event event) {
        if (event instanceof EntryAddedEvent) {
            this.onDisplayEntryAdded((EntryAddedEvent) event);
        } else {
            log.error("Invalid Event received - {}. Ignoring.", event);
        }
    }

    public void onDisplayEntryAdded(EntryAddedEvent entryAddedEvent) {
        if (entryAddedEvent
                .getContent()
                .getContent()
                .startsWith(viewRendererConfig.getPrefix())) {
            this.displayLines
                    .add(viewEntryMapper.map(entryAddedEvent.getContent()));
        }
    }

}
