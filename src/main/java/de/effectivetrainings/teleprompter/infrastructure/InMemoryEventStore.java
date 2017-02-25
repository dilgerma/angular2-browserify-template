package de.effectivetrainings.teleprompter.infrastructure;

import de.effectivetrainings.teleprompter.domain.Event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class InMemoryEventStore implements EventStore {

    private List<Event> events = new CopyOnWriteArrayList<>();

    @Override
    public void put(Event event) {
        this.events.add(event);
    }

    @Override
    public List<Event> events(String key) {
        return this.events.stream().filter(event -> event.getKey().equals(key)).collect(Collectors.toList());
    }

    @Override
    public void delete(String key) {
        this.events.removeIf(event -> event
                .getKey()
                .equals(key));
    }
}
