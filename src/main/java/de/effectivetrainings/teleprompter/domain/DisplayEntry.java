package de.effectivetrainings.teleprompter.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;


public class DisplayEntry implements Entry {

    @Getter
    private int lineNumber;
    @Getter
    private LocalDateTime date;
    @Getter
     private String content;

    public DisplayEntry(int lineNumber, String content, LocalDateTime date) {
        this.lineNumber = lineNumber;
        this.date = Objects.requireNonNull(date);
        this.content = Objects.requireNonNull(content);
    }
}
