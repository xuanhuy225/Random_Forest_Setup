package me.miniapp.base.server.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to My App.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
}
