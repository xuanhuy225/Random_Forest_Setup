package me.miniapp.base.server;

import me.miniapp.base.server.configuration.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;


@SpringBootApplication
@ComponentScan({"me.miniapp.base",})
@Slf4j
@EnableConfigurationProperties({ LiquibaseProperties.class, ApplicationProperties.class })
public class ServerApplication {

    private final Environment env;

    public ServerApplication(Environment env) {
        this.env = env;
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ServerApplication.class);
        ConfigurableApplicationContext run = app.run(args);
        Environment env = run.getEnvironment();

    }
}
