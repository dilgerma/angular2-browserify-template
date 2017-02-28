package de.effectivetrainings.teleprompter.domain;

import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ExerciseStartedEvent extends Event {

    @NonNull
    private String exercise;

    public ExerciseStartedEvent(String session, @NonNull String exercise) {
        super(session, LocalDateTime.now());
        this.exercise = exercise;
    }
}
