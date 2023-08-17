package org.j262.lock.api;

import java.time.Duration;

/**
 * Provided distributed lock for shedlock library
 */
public interface DistributedLock {
    /**
     * @param lockName      unique lock name
     * @param lockAtMostFor lock lifetime
     * @return true if lock succeeded
     */
    boolean lock(String lockName, Duration lockAtMostFor);

    /**
     * @param lockName unique lock name
     */
    void unlock(String lockName);
}
