package de.effectivetrainings.teleprompter.domain;

import lombok.NonNull;
import lombok.Value;

@Value
public class Description {

    @NonNull
    private String key;
    @NonNull
    private String content;

    public static Description empty() {
        return new Description("", "");
    }
}
