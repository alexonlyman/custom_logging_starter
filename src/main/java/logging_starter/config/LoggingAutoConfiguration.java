package logging_starter;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingAutoConfiguration {

    @Bean
    @ConditionalOnProperty(name = "logging.aspect.enabled", havingValue = "true", matchIfMissing = true)
    public LoggingAspect loggingAspect(LoggingProperties loggingProperties) {
        return new LoggingAspect(loggingProperties);
    }

    @Bean
    public LoggingProperties loggingProperties() {
        return new LoggingProperties();
    }
}
