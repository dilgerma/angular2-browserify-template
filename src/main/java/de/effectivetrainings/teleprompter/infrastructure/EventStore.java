package de.effectivetrainings.teleprompter.infrastructure;

import de.effectivetrainings.teleprompter.domain.Event;

import java.util.List;

public interface EventStore {
    void put(Event event);

    List<Event> events(String key);

    void delete(String key);
}
