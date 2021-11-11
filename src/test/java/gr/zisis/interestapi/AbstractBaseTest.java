package gr.zisis.interestapi;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class AbstractBaseTest {

    protected static final AtomicLong PID = new AtomicLong(0);
    protected static final AtomicLong FID = new AtomicLong(0);
    protected static final AtomicLong MID = new AtomicLong(0);

    private static final String POSTGRES_14_ALPINE_3_14 = "postgres:14-alpine3.14";
    private static final DockerImageName POSTGRES_IMAGE_NAME = DockerImageName.parse(POSTGRES_14_ALPINE_3_14);
    private static final String TDINTEREST = "tdinterest";
    private static final String DATABASE_SCHEMA_SQL = "database/schema.sql";

    private static PostgreSQLContainer<?> POSTGRES;

    static {
        POSTGRES = new PostgreSQLContainer<>(POSTGRES_IMAGE_NAME)
                .withDatabaseName(TDINTEREST)
                .withUsername(TDINTEREST)
                .withPassword(TDINTEREST)
                .withInitScript(DATABASE_SCHEMA_SQL)
                .withReuse(true);

        POSTGRES.start();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
    }
    
}
