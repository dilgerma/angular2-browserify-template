package de.effectivetrainings.teleprompter.adapter.outbound.rest.exercises;

import de.effectivetrainings.teleprompter.adapter.outbound.rest.ViewEntry;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

@Value
@NonFinal
public class ExercisesView extends ResourceSupport {

    private String name;
    private String identifier;
    private List<ViewEntry> exercises;

}
