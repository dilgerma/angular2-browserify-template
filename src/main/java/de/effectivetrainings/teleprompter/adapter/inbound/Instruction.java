package de.effectivetrainings.teleprompter.adapter.inbound;

import lombok.Getter;
import lombok.Value;

@Value
public class Instruction {

    public static final String SESSION_TYPE = "session";

    @Getter
    private String type;
    @Getter
    private String headline;
    @Getter
    private String content;
}
