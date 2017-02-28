package de.effectivetrainings.teleprompter.domain;

import java.util.List;

public interface Aggregate<T extends Event> {

    Aggregate<T> apply(List<T> events);
    void on(T event);
}
