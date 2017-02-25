package de.effectivetrainings.teleprompter.domain;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class Event {

    @NonNull
    public String key;
    @NonNull
    public LocalDateTime date;
}
