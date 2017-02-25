package de.effectivetrainings.teleprompter.domain;

import de.effectivetrainings.teleprompter.adapter.outbound.rest.ViewEngine;

import java.util.List;

public interface EventHandler<T extends Event> {

    ViewEngine apply(List<T> events);
    void on(T event);
}
