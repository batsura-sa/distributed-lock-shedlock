package net.javacrumbs.shedlock.provider.jdbctemplate;

import java.time.Duration;
import java.time.Instant;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.core.LockConfiguration;
import org.j262.lock.api.DistributedLock;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class DbDistributedLock implements DistributedLock {
    private final JdbcTemplateStorageAccessor accessor;

    private LockConfiguration toLockConfiguration(String lockName, Duration lockAtMostFor) {
        return new LockConfiguration(Instant.now(), lockName, lockAtMostFor, Duration.ZERO);
    }

    @Override
    public boolean lock(String lockName, Duration lockAtMostFor) {
        return accessor.insertRecord(toLockConfiguration(lockName, lockAtMostFor));
    }

    @Override
    public void unlock(String lockName) {
        accessor.unlock(toLockConfiguration(lockName, Duration.ZERO));
    }

    public static DistributedLock create(DataSource dataSource) {
        JdbcTemplateLockProvider.Configuration.Builder builder = new JdbcTemplateLockProvider.Configuration.Builder();
        JdbcTemplateLockProvider.Configuration configuration = builder.withJdbcTemplate(new JdbcTemplate(dataSource)).build();

        return new DbDistributedLock(new JdbcTemplateStorageAccessor(configuration));
    }
}
