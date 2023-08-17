package org.j262.lock;

import java.time.Duration;
import lombok.SneakyThrows;
import org.j262.lock.api.DistributedLock;
import org.j262.lock.base.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class DistributedLockTest extends AbstractTest {

    @Autowired
    private DistributedLock distributedLock;

    private static final String CLEAN_SQL = """
            truncate table shedlock cascade;
            """;

    private static final String LOCK1 = "LOCK1";
    private static final Duration LOCK_FOR_MINUTE = Duration.ofMinutes(1);
    private static final Duration LOCK_FOR_SECOND = Duration.ofSeconds(1);

    @Test
    @DisplayName("Lock and unlock test")
    @Sql(statements = {CLEAN_SQL})
    void lockUnlockTest() {
        Assertions.assertTrue(distributedLock.lock(LOCK1, LOCK_FOR_MINUTE));
        Assertions.assertFalse(distributedLock.lock(LOCK1, LOCK_FOR_MINUTE));
        distributedLock.unlock(LOCK1);

        Assertions.assertTrue(distributedLock.lock(LOCK1, LOCK_FOR_MINUTE));
        distributedLock.unlock(LOCK1);
    }

    @Test
    @DisplayName("Repeat lock after deadline")
    @Sql(statements = {CLEAN_SQL})
    @SneakyThrows
    public void repeatedLockTest() {
        Assertions.assertTrue(distributedLock.lock(LOCK1, LOCK_FOR_SECOND));
        Assertions.assertFalse(distributedLock.lock(LOCK1, LOCK_FOR_SECOND));

        Thread.sleep(Duration.ofSeconds(1).toMillis());

        Assertions.assertTrue(distributedLock.lock(LOCK1, LOCK_FOR_SECOND));
    }
}
