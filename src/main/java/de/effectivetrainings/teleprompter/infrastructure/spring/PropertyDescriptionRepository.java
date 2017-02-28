package de.effectivetrainings.teleprompter.infrastructure.spring;

import de.effectivetrainings.teleprompter.domain.Description;
import de.effectivetrainings.teleprompter.domain.DescriptionRepository;
import lombok.NonNull;
import org.springframework.core.env.PropertyResolver;

import java.util.Optional;

public class PropertyDescriptionRepository implements DescriptionRepository {

    private PropertyResolver propertyResolver;

    public PropertyDescriptionRepository(@NonNull PropertyResolver propertyResolver) {
        this.propertyResolver = propertyResolver;
    }


    @Override
    public Optional<Description> findByKey(String key) {
        if(!propertyResolver.containsProperty(sanitize(key))) {
            return Optional.empty();
        }
        return Optional.ofNullable(new Description(key, propertyResolver
                .getProperty(sanitize(key)).toString()));
    }

    private String sanitize(String key) {
        return key.replace("git ", "");
    }
}
