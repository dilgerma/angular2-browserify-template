package de.effectivetrainings.teleprompter.domain;


import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class EntryAddedEvent extends Event {

    private final Entry content;

    public EntryAddedEvent(@NonNull String key, @NonNull LocalDateTime date, @NonNull Entry content) {
        super(key, date);
        this.content = content;
    }
}
