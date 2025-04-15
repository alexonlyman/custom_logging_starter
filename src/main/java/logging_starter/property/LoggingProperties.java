package logging_starter.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * Properties class for configuring logging aspects.
 * This class holds the configuration properties for the logging aspect,
 * including whether logging is enabled and the logging level.
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "logging.aspect")
public class LoggingProperties {
    /**
     * Indicates whether logging is enabled.
     * Default value is {@code true}.
     */
    private boolean enabled = true;

    /**
     * The logging level to be used.
     * Default value is {@code "info"}.
     */
    private String level = "info";

}
