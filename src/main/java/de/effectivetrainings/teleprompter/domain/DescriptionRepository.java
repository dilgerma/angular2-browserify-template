package de.effectivetrainings.teleprompter.domain;


import java.util.Optional;

public interface DescriptionRepository {

    Optional<Description> findByKey(String description);

}
