package de.effectivetrainings.teleprompter.adapter.inbound;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Slf4j
public class ZshHistoryLineParser {

    public Optional<ParsedLine> parse(int linenumber, String line) {
        String[] segments = line.split(";");
        if (segments.length != 2) {
            log.warn("Invalid entry : {}. Ignoring", line);
            return Optional.empty();
        }
        String command = segments[1];

        //extended history format stores timestamp:0 - see here.
        final String timeStampWithExtendedHistoryFormat = segments[0].substring(2);
        final long epochMilli = Long.parseLong(timeStampWithExtendedHistoryFormat.split(":")[0]);
        LocalDateTime timestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZoneId.systemDefault());
        return Optional.of(new ParsedLine(linenumber, command, timestamp));
    }
}
