package de.effectivetrainings.teleprompter.adapter.outbound.rest;

import de.effectivetrainings.teleprompter.domain.Event;
import de.effectivetrainings.teleprompter.infrastructure.EventStore;
import lombok.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
        ViewEngine viewEngine = new ViewEngine(new ViewEntryMapper(), viewRendererConfig).apply(events);
        return new ModelAndView("index", "lines", viewEngine.getDisplayLines());
    }
}
