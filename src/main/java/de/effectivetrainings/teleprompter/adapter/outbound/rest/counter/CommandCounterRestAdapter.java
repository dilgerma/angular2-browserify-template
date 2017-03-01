package de.effectivetrainings.teleprompter.adapter.outbound.rest.counter;

import de.effectivetrainings.teleprompter.domain.Event;
import de.effectivetrainings.teleprompter.infrastructure.EventStore;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommandCounterRestAdapter {

    @NonNull
    private EventStore eventStore;


    @RequestMapping(value = "/commands/{sessionId}")
    public Map<String, Integer> entries(@PathVariable("sessionId") String sessionId) {
        final List<Event> events = eventStore.events(sessionId);
        final Map<Command, Integer> commandCounts = new GitCommandCounter()
                .apply(events)
                .getCommandCounts();
        return commandCounts
                .entrySet()
                .stream()
                .collect(Collectors.toMap(c -> c
                        .getKey()
                        .getCommand(), e -> e.getValue()));
    }
}
