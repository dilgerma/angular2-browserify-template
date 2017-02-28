package de.effectivetrainings.teleprompter.adapter.inbound;

import de.effectivetrainings.teleprompter.domain.*;
import de.effectivetrainings.teleprompter.infrastructure.EventStore;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

public class LineReadHandler {

    private final EventStore eventStore;
    private final InstructionParser instructionParser;

    public LineReadHandler(@NonNull EventStore eventStore, @NonNull InstructionParser instructionParser) {
        this.instructionParser = instructionParser;
        this.eventStore = eventStore;
    }

    public void parse(String session, ParsedLine parsedLine) {
        final Optional<Instruction> possibleInstruction = instructionParser.instruction(parsedLine.getContent());
        if (possibleInstruction.isPresent()) {

            //we have an instruction
            event(session, possibleInstruction.get()).ifPresent(ev -> eventStore.put(ev));

        } else {
            //something to display
            eventStore.put(new EntryAddedEvent(session, LocalDateTime.now(ZoneId.of("GMT")), new DisplayEntry(parsedLine.getLineNumber(), parsedLine.getContent(), parsedLine.getDate())));
        }
    }

    private Optional<Event> event(String session, Instruction instruction) {
        final Optional<InstructionType> type = InstructionType.of(instruction.getType());
        final Optional<Event> eventTypeToSend = type.map(t -> {
            switch (t) {
                case SESSION_CREATED:
                    return new SessionCreatedEvent(session, LocalDateTime.now(), instruction.getContent());
                case SESSION_DESTROYED:
                    return new SessionDestroyedEvent(session, LocalDateTime.now(), instruction.getContent());
                case EXERCISE_STARTED:
                    return new ExerciseStartedEvent(session, instruction.getHeadline());
                case EXERCISE_STOPPED:
                    return new ExerciseStoppedEvent(session);
            }
            return null;
        });
        return eventTypeToSend;
    }
}
