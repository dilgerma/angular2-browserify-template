package de.effectivetrainings.teleprompter.adapter.outbound.rest;

import de.effectivetrainings.teleprompter.domain.Description;
import de.effectivetrainings.teleprompter.domain.Entry;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

public class ViewEntryMapper {

    public ViewEntry map(Entry entry, Optional<Description> description) {
        ZonedDateTime zdt = entry.getDate().atZone(ZoneId.systemDefault());
        return new ViewEntry(Date.from(zdt.toInstant()), entry.getContent(), entry.getLineNumber(), description.map(d -> d.getContent()));
    }

    public ViewEntry mapExercise(String exercise) {
        return new ViewEntry(null, exercise, -1, null);
    }
}
