package org.j262.lock.base;

import java.time.Duration;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class AbstractTest {
    @Container
    @ClassRule
    public static final PostgreSQLContainer<PostgreSQLTestContainer> postgreSQLContainer = PostgreSQLTestContainer.getInstance();

    @DynamicPropertySource
    static void databaseProperty(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeEach
    @Sql(statements = {"DROP DATABASE business;", "CREATE DATABASE business TEMPLATE test_template;"})
    public void waitContainer() {
        try {
            Thread.sleep(Duration.ofSeconds(1).toMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
