package de.effectivetrainings.teleprompter.infrastructure;

import de.effectivetrainings.teleprompter.domain.Event;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@RequiredArgsConstructor
public class DefaultInMemoryEventEmitter implements EventEmitter {

    @NonNull
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void emit(Event event) {
        applicationEventPublisher.publishEvent(event);
    };
}
