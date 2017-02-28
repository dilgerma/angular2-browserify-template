package de.effectivetrainings.teleprompter.domain;

import lombok.NonNull;

import java.time.LocalDateTime;

public class ExerciseStoppedEvent extends Event {
    public ExerciseStoppedEvent(@NonNull  String session) {
        super(session, LocalDateTime.now());
    }
}
