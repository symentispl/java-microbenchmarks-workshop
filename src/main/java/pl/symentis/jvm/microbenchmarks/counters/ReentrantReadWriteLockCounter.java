package pl.symentis.jvm.microbenchmarks.counters;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockCounter {

    private int counter;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    synchronized void inc() {
        lock.writeLock().lock();
        counter++;
        lock.writeLock().unlock();
    }

    synchronized int value() {
        lock.readLock().lock();
        try {
            return counter;
        } finally {
            lock.readLock().unlock();
        }
    }
}
