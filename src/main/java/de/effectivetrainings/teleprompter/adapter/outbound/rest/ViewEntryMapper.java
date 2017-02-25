package de.effectivetrainings.teleprompter.adapter.outbound.rest;

import de.effectivetrainings.teleprompter.domain.Entry;

public class ViewEntryMapper {

    public ViewEntry map(Entry entry) {
        return new ViewEntry(entry.getDate(), entry.getContent(), entry.getLineNumber());
    }
}
