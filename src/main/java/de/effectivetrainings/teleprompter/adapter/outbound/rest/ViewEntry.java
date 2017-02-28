package de.effectivetrainings.teleprompter.adapter.outbound.rest;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Optional;

@Builder
@Data
public class ViewEntry {

    private Date localDateTime;
    private String content;
    private int lineNumber;
    private Optional<String> description;

}
