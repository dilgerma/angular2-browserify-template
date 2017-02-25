package de.effectivetrainings.teleprompter.domain;

import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class SessionDestroyedEvent extends Event {

    @NonNull
    private String sessionId;

    public SessionDestroyedEvent(String key, LocalDateTime date, String sessionId) {
        super(key, date);
        this.sessionId = sessionId;
    }
}
