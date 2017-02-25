package de.effectivetrainings.teleprompter.adapter.inbound.rest;

import de.effectivetrainings.teleprompter.adapter.inbound.LineReadHandler;
import de.effectivetrainings.teleprompter.adapter.inbound.ParsedLine;
import de.effectivetrainings.teleprompter.adapter.inbound.ZshHistoryLineParser;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class RestInboundAdapter {

    private LineReadHandler lineReadHandler;
    private ZshHistoryLineParser zshHistoryLineParser;

    public RestInboundAdapter(@NonNull LineReadHandler lineReadHandler, @NonNull ZshHistoryLineParser zshHistoryLineParser) {
        this.lineReadHandler = lineReadHandler;
        this.zshHistoryLineParser = zshHistoryLineParser;
    }

    @RequestMapping(value = "/line/{sessionId}", method = RequestMethod.POST)
    public void putLine(@PathVariable String sessionId, @RequestBody String lines) {
        Optional<ParsedLine> parsedLine = zshHistoryLineParser.parse(-1, lines);
        parsedLine.ifPresent(pl -> {
            lineReadHandler.parse(sessionId, new ParsedLine(-1, pl.getContent(), LocalDateTime.now()));
        });
    }
}
