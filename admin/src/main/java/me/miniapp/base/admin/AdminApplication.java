package me.miniapp.base.admin;

import me.miniapp.base.admin.configuration.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;


@SpringBootApplication
@ComponentScan({"me.miniapp.base",})
@Slf4j
@EnableConfigurationProperties({ LiquibaseProperties.class, ApplicationProperties.class })
public class AdminApplication {

    private final Environment env;

    public AdminApplication(Environment env) {
        this.env = env;
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AdminApplication.class);
        Environment env = app.run(args).getEnvironment();
    }
}
