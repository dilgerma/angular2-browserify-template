package de.effectivetrainings.teleprompter.adapter.outbound.rest.exercises;

import de.effectivetrainings.teleprompter.adapter.outbound.rest.ViewEntryMapper;
import de.effectivetrainings.teleprompter.adapter.outbound.rest.ViewRendererConfig;
import de.effectivetrainings.teleprompter.domain.DescriptionRepository;
import de.effectivetrainings.teleprompter.domain.Event;
import de.effectivetrainings.teleprompter.infrastructure.EventStore;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeleprompterExercisesRestAdapter {

    @NonNull
    private EventStore eventStore;
    @NonNull
    private ViewRendererConfig viewRendererConfig;
    @NonNull
    private DescriptionRepository descriptionRepository;


    @RequestMapping(value = "/exercises/{sessionId}")
    public List<ExercisesView> entries(@PathVariable("sessionId") String sessionId) {
        final List<Event> events = eventStore.events(sessionId);
        final List<ExercisesView> exercises = new ArrayList<>(fromEvents(sessionId, events));
        Collections.reverse(exercises);
        return exercises;
    }

    @RequestMapping(value = "/exercises/{sessionId}/{id}")
    public ExercisesView exercise(@PathVariable("sessionId") String sessionId, @PathVariable("id") String exerciseId) {
        final List<Event> events = eventStore.events(sessionId, event -> event.getId().equals(exerciseId));
        final List<ExercisesView> exercisesViews = fromEvents(sessionId, events);
        return exercisesViews.isEmpty() ? null : exercisesViews.get(0);
    }

    private List<ExercisesView> fromEvents(String session, List<Event> events) {
        final Exercises viewEngine = new Exercises(session, new ViewEntryMapper(), viewRendererConfig, descriptionRepository).apply(events);
        return viewEngine.view();
    }
}
