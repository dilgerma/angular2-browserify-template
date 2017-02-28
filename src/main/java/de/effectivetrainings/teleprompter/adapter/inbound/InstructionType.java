package de.effectivetrainings.teleprompter.adapter.inbound;

import java.util.Optional;

public enum InstructionType {

    SESSION_CREATED("session_created"),
    SESSION_DESTROYED("session_destroyed"),
    HINT_APPLIED("hint"),
    EXERCISE_STARTED("exercise_started"),
    EXERCISE_STOPPED("exercise_stopped");

    private String type;

    InstructionType(String type) {
        this.type = type;
    }

    public static Optional<InstructionType> of(String type) {
        for (InstructionType t : values()) {
            if (type.equals(t.type)) {
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }
}
