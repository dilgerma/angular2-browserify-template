package de.effectivetrainings.teleprompter.adapter.inbound.file;

import de.effectivetrainings.teleprompter.adapter.SourceDescription;
import de.effectivetrainings.teleprompter.adapter.TeleprompterSource;
import de.effectivetrainings.teleprompter.adapter.inbound.LineReadHandler;
import de.effectivetrainings.teleprompter.adapter.inbound.ParsedLine;
import de.effectivetrainings.teleprompter.adapter.inbound.ZshHistoryLineParser;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@Slf4j

public class ZshrcHistoryFileSource implements TeleprompterSource {

    private final Resource classPathResource;
    private final LineReadHandler lineReadHandler;
    private final ZshHistoryLineParser zshHistoryLineParser;

    public ZshrcHistoryFileSource(@NonNull Resource classPathResource, @NonNull LineReadHandler lineReadHandler, @NonNull ZshHistoryLineParser zshHistoryLineParser) {
        this.classPathResource = classPathResource;
        this.lineReadHandler = lineReadHandler;
        this.zshHistoryLineParser = zshHistoryLineParser;
    }


    @Override
    public void read(SourceDescription sourceDescription) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(classPathResource.getFile()));
            String line = null;
            int linenumber = 0;
            while ((line = bufferedReader.readLine()) != null) {
                linenumber++;
                if (linenumber < sourceDescription.getFromLine())
                    continue;

                //parse line
                final Optional<ParsedLine> parse = zshHistoryLineParser.parse(linenumber, line);
                parse.ifPresent(parsed -> lineReadHandler.parse(sourceDescription.getSession(), parsed));
            }
        } catch (IOException e) {
            throw new SourceNotReadableException(e);
        }

    }

}
