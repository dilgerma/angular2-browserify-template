package de.effectivetrainings.teleprompter.infrastructure;

import de.effectivetrainings.teleprompter.domain.Event;

import java.util.List;
import java.util.function.Predicate;

public interface EventStore {
    void put(Event event);

    List<Event> events(String key);

    List<Event> events(String key, Predicate<Event> predicate);

    void delete(String key);
}
