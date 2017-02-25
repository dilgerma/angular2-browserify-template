package de.effectivetrainings.teleprompter.infrastructure;

import de.effectivetrainings.teleprompter.domain.Event;

public interface EventEmitter {

    void emit(Event event);
}
