package de.effectivetrainings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import de.effectivetrainings.teleprompter.adapter.SourceDescription;
import de.effectivetrainings.teleprompter.adapter.TeleprompterSource;
import de.effectivetrainings.teleprompter.adapter.inbound.InstructionParser;
import de.effectivetrainings.teleprompter.adapter.inbound.LineReadHandler;
import de.effectivetrainings.teleprompter.adapter.inbound.ZshHistoryLineParser;
import de.effectivetrainings.teleprompter.adapter.inbound.file.ZshrcHistoryFileSource;
import de.effectivetrainings.teleprompter.adapter.inbound.rest.RestInboundAdapter;
import de.effectivetrainings.teleprompter.adapter.outbound.rest.TeleprompterLineViewAdapter;
import de.effectivetrainings.teleprompter.adapter.outbound.rest.TeleprompterRestAdapter;
import de.effectivetrainings.teleprompter.adapter.outbound.rest.ViewRendererConfig;
import de.effectivetrainings.teleprompter.infrastructure.DefaultInMemoryEventEmitter;
import de.effectivetrainings.teleprompter.infrastructure.EventEmitter;
import de.effectivetrainings.teleprompter.infrastructure.EventStore;
import de.effectivetrainings.teleprompter.infrastructure.InMemoryEventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;

import javax.annotation.PostConstruct;

@Configuration
@EnableAutoConfiguration
@Import({TeleprompterApplication.ZshConfig.class, TeleprompterApplication.RestConfig.class, TeleprompterApplication.EventConfig.class})
public class TeleprompterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeleprompterApplication.class, args);
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JSR310Module());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return objectMapper;
	}

	@Configuration
	public static class ZshConfig {

		@Autowired
		private EventEmitter eventEmitter;
		@Autowired
		private EventStore eventStore;

		@Bean
		public LineReadHandler lineReadHandler() {
			return new LineReadHandler(eventStore, instructionParser());
		}

		@Bean
		public ZshrcHistoryFileSource zshrcHistoryFileSource() {
			return new ZshrcHistoryFileSource(new FileSystemResource("/tmp/history"), lineReadHandler(), zshHistoryLineParser());
		}

		@Bean
		public ZshHistoryLineParser zshHistoryLineParser() {
			return new ZshHistoryLineParser();
		}


		@Bean
		public RestInboundAdapter restInboundAdapter() {
			return new RestInboundAdapter(lineReadHandler(), zshHistoryLineParser());
		}

		@Bean
		public InstructionParser instructionParser() {
			return new InstructionParser();
		}

	}

	@Configuration
	public static class RestConfig {

		@Autowired
		private EventStore eventStore;
		@Autowired
		private LineReadHandler lineReadHandler;

		@Bean
		public TeleprompterRestAdapter teleprompterRestAdapter() {
			return new TeleprompterRestAdapter(eventStore, viewRendererConfig());
		}

		@Bean
		public TeleprompterLineViewAdapter teleprompterLineViewAdapter() {
			return new TeleprompterLineViewAdapter(eventStore, viewRendererConfig());
		}


		@Bean
		public ViewRendererConfig viewRendererConfig() {
			return ViewRendererConfig.builder().prefix("git").build();
		}
	}

	@Configuration
	public static class EventConfig {

		@Autowired
		private ApplicationEventPublisher applicationEventPublisher;

		@Bean
		public EventEmitter eventEmitter() {
			return new DefaultInMemoryEventEmitter(applicationEventPublisher);
		}

		@Bean
		public EventStore eventStore() {
			return new InMemoryEventStore();
		}
	}

}
