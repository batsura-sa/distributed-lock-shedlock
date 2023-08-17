package org.j262.lock.config;

import javax.sql.DataSource;
import net.javacrumbs.shedlock.provider.jdbctemplate.DbDistributedLock;
import org.j262.lock.api.DistributedLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration()
public class BeanConfig {

    @Bean
    public DistributedLock distributedLock(DataSource dataSource) {
        return DbDistributedLock.create(dataSource);
    }

}
