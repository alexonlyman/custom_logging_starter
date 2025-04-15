package logging_starter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "logging.aspect")
public class LoggingProperties {

    private boolean enabled = true;
    private String level = "info";

}
