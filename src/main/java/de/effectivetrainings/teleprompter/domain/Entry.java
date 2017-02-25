package de.effectivetrainings.teleprompter.domain;

import java.time.LocalDateTime;

public interface Entry {
    int getLineNumber();
    String getContent();
    LocalDateTime getDate();

}
