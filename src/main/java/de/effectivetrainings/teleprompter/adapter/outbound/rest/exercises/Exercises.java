package de.effectivetrainings.teleprompter.adapter.outbound.rest.exercises;

import de.effectivetrainings.teleprompter.adapter.outbound.rest.ViewEntry;
import de.effectivetrainings.teleprompter.adapter.outbound.rest.ViewEntryMapper;
import de.effectivetrainings.teleprompter.adapter.outbound.rest.ViewRendererConfig;
import de.effectivetrainings.teleprompter.domain.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class Exercises implements Aggregate<Event> {

    private Map<Exercise, List<ViewEntry>> exercises = new LinkedHashMap<>();

    @NonNull
    private String session;
    @NonNull
    private ViewEntryMapper viewEntryMapper;
    @NonNull
    private ViewRendererConfig viewRendererConfig;
    @NonNull
    private DescriptionRepository descriptionRepository;

    private Optional<Exercise> currentExercise = Optional.empty();

    public Exercises(@NonNull String session) {
        this.session = session;
    }

    @Override
    public Exercises apply(List<Event> events) {
        events
                .stream()
                .forEach(this::on);
        return this;
    }

    public List<ExercisesView> view() {
        final List<ExercisesView> collect = exercises
                .entrySet()
                .stream()
                .map(entry -> exercisesView(entry))
                .collect(Collectors.toList());
        return collect;
    }

    private ExercisesView exercisesView(Map.Entry<Exercise, List<ViewEntry>> entry) {
        final ExercisesView exercisesView = new ExercisesView(
                entry
                        .getKey()
                        .getName(),
                entry
                        .getKey()
                        .getId(),
                entry.getValue());
        exercisesView.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(TeleprompterExercisesRestAdapter.class).exercise(session, entry.getKey().getId())).withRel("self"));
        return exercisesView;
    }


    @Override
    public void on(Event event) {
        if (event instanceof EntryAddedEvent) {
            this.onDisplayEntryAdded((EntryAddedEvent) event);
        } else if (event instanceof ExerciseStartedEvent) {
            this.onExerciseStarted((ExerciseStartedEvent) event);
        } else if(event instanceof ExerciseStoppedEvent) {
            this.onExerciseStopped((ExerciseStoppedEvent) event);
        }
    }

    private void onExerciseStopped(ExerciseStoppedEvent event) {
        this.currentExercise = Optional.empty();
    }

    private void onExerciseStarted(ExerciseStartedEvent event) {
        final Exercise exercise = new Exercise(UUID.randomUUID().toString(), event.getExercise());
        exercises.computeIfAbsent(exercise, (k) -> new ArrayList<>());
        currentExercise = Optional.of(exercise);
    }

    public void onDisplayEntryAdded(EntryAddedEvent entryAddedEvent) {
        if (entryAddedEvent
                .getContent()
                .getContent()
                .startsWith(viewRendererConfig.getPrefix())) {
            final Optional<Description> description = descriptionRepository
                    .findByKey(entryAddedEvent
                            .getContent()
                            .getContent());
            final ViewEntry viewEntry = viewEntryMapper.map(entryAddedEvent.getContent(), description);
            if (currentExercise.isPresent()) {
                exercises
                        .get(currentExercise.get())
                        .add(viewEntry);
            }
        }
    }
}
