package logging_starter.config;


import logging_starter.property.LoggingProperties;
import logging_starter.aspect.LoggingAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Configuration class for setting up logging aspects.
 * This class defines beans for logging properties and the logging aspect.
 */
@Configuration
public class LoggingAutoConfiguration {

    /**
     * Creates a {@link LoggingAspect} bean if the logging aspect is enabled.
     * The logging aspect is enabled by default if the property "logging.aspect.enabled" is not set.
     *
     * @param loggingProperties the logging properties to be used by the logging aspect
     * @return a new instance of {@link LoggingAspect}
     */
    @Bean
    @ConditionalOnProperty(name = "logging.aspect.enabled", havingValue = "true", matchIfMissing = true)
    public LoggingAspect loggingAspect(LoggingProperties loggingProperties) {
        return new LoggingAspect(loggingProperties);
    }

    /**
     * Creates a {@link LoggingProperties} bean.
     *
     * @return a new instance of {@link LoggingProperties}
     */
    @Bean
    public LoggingProperties loggingProperties() {
        return new LoggingProperties();
    }
}
