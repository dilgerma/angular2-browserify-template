package de.effectivetrainings.teleprompter.adapter.outbound.rest;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@RequiredArgsConstructor
public class SessionConfig extends ResourceSupport {

    @NonNull
    @Getter
    private String sessionId;

}
