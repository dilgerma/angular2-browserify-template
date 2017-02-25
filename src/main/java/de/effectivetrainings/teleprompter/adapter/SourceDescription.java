package de.effectivetrainings.teleprompter.adapter;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;


@Builder
public class SourceDescription {

    @Getter
    @NonNull
    private int fromLine;
    @Getter
    @NonNull
    private String session;
}
