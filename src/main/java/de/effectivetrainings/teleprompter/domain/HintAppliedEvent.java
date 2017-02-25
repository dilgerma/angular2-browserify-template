package de.effectivetrainings.teleprompter.domain;

import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class HintAppliedEvent extends Event {

    @NonNull
    private String headline;
    @NonNull
    private String hint;

    public HintAppliedEvent(String key, LocalDateTime date, String headline, String hint) {
        super(key, date);
        this.headline = headline;
        this.hint = hint;
    }
}
