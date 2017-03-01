package de.effectivetrainings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.effectivetrainings.teleprompter.adapter.inbound.InstructionParser;
import de.effectivetrainings.teleprompter.adapter.inbound.LineReadHandler;
import de.effectivetrainings.teleprompter.adapter.inbound.ZshHistoryLineParser;
import de.effectivetrainings.teleprompter.adapter.inbound.file.ZshrcHistoryFileSource;
import de.effectivetrainings.teleprompter.adapter.inbound.rest.RestInboundAdapter;
import de.effectivetrainings.teleprompter.adapter.outbound.rest.counter.CommandCounterRestAdapter;
import de.effectivetrainings.teleprompter.adapter.outbound.rest.displaylines.TeleprompterLineViewAdapter;
import de.effectivetrainings.teleprompter.adapter.outbound.rest.displaylines.TeleprompterRestAdapter;
import de.effectivetrainings.teleprompter.adapter.outbound.rest.ViewRendererConfig;
import de.effectivetrainings.teleprompter.adapter.outbound.rest.exercises.TeleprompterExercisesRestAdapter;
import de.effectivetrainings.teleprompter.domain.DescriptionRepository;
import de.effectivetrainings.teleprompter.infrastructure.DefaultInMemoryEventEmitter;
import de.effectivetrainings.teleprompter.infrastructure.EventEmitter;
import de.effectivetrainings.teleprompter.infrastructure.EventStore;
import de.effectivetrainings.teleprompter.infrastructure.InMemoryEventStore;
import de.effectivetrainings.teleprompter.infrastructure.spring.PropertyDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableAutoConfiguration
public class TeleprompterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeleprompterApplication.class, args);
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.registerModule(new Jdk8Module());
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
		@Autowired
		private DescriptionRepository descriptionRepository;

		@Bean
		public TeleprompterRestAdapter teleprompterRestAdapter() {
			return new TeleprompterRestAdapter(eventStore, viewRendererConfig(), descriptionRepository);
		}

		@Bean
		public TeleprompterExercisesRestAdapter teleprompterExercisesRestAdapter(){
			return new TeleprompterExercisesRestAdapter(eventStore, viewRendererConfig(), descriptionRepository);
		}

		@Bean
		public CommandCounterRestAdapter commandCounterRestAdapter() {
			return new CommandCounterRestAdapter(eventStore);
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

	@Configuration
	@PropertySource(name="descriptionPropertySource", value = "classpath:knowledge-base.properties")
	public static class PropertyDescriptionConfig {

		@Autowired
		private PropertyResolver propertyResolver;

	    @Bean
	    public DescriptionRepository propertyDescriptionRepository() {
	        return new PropertyDescriptionRepository(propertyResolver);
	    }
	}

}
