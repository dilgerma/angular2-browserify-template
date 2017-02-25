package de.effectivetrainings.teleprompter.adapter.inbound;

import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ParsedLine {

    @NonNull
    private int lineNumber;
    @NonNull
    private String content;
    @NonNull
    private LocalDateTime date;
}
