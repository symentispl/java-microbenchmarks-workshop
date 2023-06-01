package pl.symentis.jvm.microbenchmarks.counters;

public class Counter {
    private int counter;

    void inc() {
        counter++;
    }

    int value() {
        return counter;
    }
}
