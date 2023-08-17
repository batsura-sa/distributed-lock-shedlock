package org.j262.lock.base;

import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

public class PostgreSQLTestContainer extends PostgreSQLContainer<PostgreSQLTestContainer> {
    private static PostgreSQLTestContainer container;

    public PostgreSQLTestContainer() {
        super("postgres:latest");
        withDatabaseName("postgres");
        withUsername("postgres");
        withPassword("postgres");
        withInitScript("db/init_schema.sql");
        withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger(PostgreSQLTestContainer.class)));
    }

    public static PostgreSQLTestContainer getInstance() {
        if (container == null) {
            container = new PostgreSQLTestContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
