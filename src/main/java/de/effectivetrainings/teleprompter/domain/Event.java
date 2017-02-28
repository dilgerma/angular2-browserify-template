package de.effectivetrainings.teleprompter.domain;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Event {

    @NonNull
    public String key;
    @NonNull
    private String id;
    @NonNull
    public LocalDateTime date;


    public Event(@NonNull String key, @NonNull String id, @NonNull LocalDateTime date) {
        this.key = key;
        this.id = id;
        this.date = date;
    }

    public Event(@NonNull String key, @NonNull LocalDateTime date) {
        this(key, UUID
                .randomUUID()
                .toString(), date);
    }
}
