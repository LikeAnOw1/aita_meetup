package me.likeanowl.aitameetup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;


@ContextConfiguration(initializers = BaseIntegrationTest.TestContextInitializer.class)
public abstract class BaseIntegrationTest {
    private final static Logger log = LoggerFactory.getLogger(BaseIntegrationTest.class);

    private final static JdbcDatabaseContainer container = new PostgreSQLContainer<>("postgres:12")
            .withDatabaseName("aita_meetup")
            .withUsername("aita_meetup")
            .withPassword("aita_meetup")
            .withInitScript("db/test-init.sql");

    static {
        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log);
        container.withLogConsumer(logConsumer);
        container.start();
    }

    public static class TestContextInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

        @Override
        public void initialize(GenericApplicationContext genericApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + container.getJdbcUrl(),
                    "spring.datasource.username=" + container.getUsername(),
                    "spring.datasource.driver-class-name=org.postgresql.Driver",
                    "spring.datasource.password=" + container.getPassword(),
                    "spring.flyway.enabled=false"
            ).applyTo(genericApplicationContext.getEnvironment());
        }
    }
}
