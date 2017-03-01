package de.effectivetrainings.teleprompter.adapter.outbound.rest.counter;

import lombok.Getter;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
public class Command {

    @Getter
    private String command;

    public static final Command of(String command) {
        return new Command(command);
    }
}
