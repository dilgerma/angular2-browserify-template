package de.effectivetrainings.teleprompter.adapter.outbound.rest.displaylines;

import de.effectivetrainings.teleprompter.adapter.outbound.rest.SessionConfig;
import de.effectivetrainings.teleprompter.adapter.outbound.rest.ViewRendererConfig;
import de.effectivetrainings.teleprompter.adapter.outbound.rest.exercises.TeleprompterExercisesRestAdapter;
import de.effectivetrainings.teleprompter.domain.Event;
import de.effectivetrainings.teleprompter.infrastructure.EventStore;
import lombok.NonNull;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
public class TeleprompterLineViewAdapter {

    private EventStore eventStore;
      private ViewRendererConfig viewRendererConfig;

    public TeleprompterLineViewAdapter(@NonNull EventStore eventStore, @NonNull ViewRendererConfig viewRendererConfig) {
        this.eventStore = eventStore;
        this.viewRendererConfig = viewRendererConfig;
    }

    @RequestMapping("/training/{sessionId}")
    public ModelAndView modelAndView(@PathVariable("sessionId") String sessionId) {
        final List<Event> events = eventStore.events(sessionId);
//        ViewEngine viewEngine = new ViewEngine(new ViewEntryMapper(), viewRendererConfig).apply(events);
        Map<String, Object> model = new HashMap<>();
        SessionConfig resourceSupport = new SessionConfig(sessionId);
        resourceSupport.add(linkTo(methodOn(TeleprompterLineViewAdapter.class).modelAndView(sessionId)).withSelfRel());
        resourceSupport.add(ControllerLinkBuilder
                .linkTo(methodOn(TeleprompterRestAdapter.class).entries(sessionId)).withRel("view"));
        resourceSupport.add(ControllerLinkBuilder.linkTo(methodOn(TeleprompterExercisesRestAdapter.class).entries(sessionId)).withRel("exercises"));
        model.put("pageConfig", resourceSupport);
        return new ModelAndView("index",  model);
    }
}
