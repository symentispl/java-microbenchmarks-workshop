package pl.symentis.jvm.microbenchmarks.counters;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
    private AtomicInteger counter = new AtomicInteger();

    void inc() {
        counter.incrementAndGet();
    }

    int value() {
        return counter.get();
    }
}
