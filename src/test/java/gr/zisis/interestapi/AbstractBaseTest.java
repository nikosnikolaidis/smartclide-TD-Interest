package gr.zisis.interestapi;

import java.util.concurrent.atomic.AtomicLong;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@ContextConfiguration(initializers = AbstractBaseTest.TestDataSourceInitializer.class)
@Testcontainers
public abstract class AbstractBaseTest {

    protected static final AtomicLong PID = new AtomicLong(0);
    protected static final AtomicLong FID = new AtomicLong(0);
    protected static final AtomicLong MID = new AtomicLong(0);

    private static final String POSTGRES_14_ALPINE_3_14 = "postgres:14-alpine3.14";
    private static final DockerImageName POSTGRES_IMAGE_NAME = DockerImageName.parse(POSTGRES_14_ALPINE_3_14);
    private static final String TDINTEREST = "tdinterest";
    private static final String DATABASE_SCHEMA_SQL = "database/schema.sql";

    @Container
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>(POSTGRES_IMAGE_NAME)
            .withDatabaseName(TDINTEREST)
            .withUsername(TDINTEREST)
            .withPassword(TDINTEREST)
            .withInitScript(DATABASE_SCHEMA_SQL);

    public static class TestDataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(final @NotNull ConfigurableApplicationContext applicationContext) {
            final String prefix = "spring.datasource.";
            final String jdbcUrl = prefix + "url=" + POSTGRES.getJdbcUrl();
            final String username = prefix + "username=" + POSTGRES.getUsername();
            final String password = prefix + "password=" + POSTGRES.getPassword();

            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(applicationContext, jdbcUrl, username, password);
        }
    }
}
