package pl.symentis.jvm.microbenchmarks.locks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class LockElision {

    static Object lock = new Object();

    int x;

    @Benchmark
    public void baseline() {
        x++;
    }

    @Benchmark
    public void locked() {
        synchronized (new Object()) {
            x++;
        }
    }

    @Benchmark
    public void sharedLock() {
        synchronized (lock) {
            x++;
        }
    }

    @Benchmark
    @Fork(jvmArgsAppend = "-XX:-DoEscapeAnalysis")
    public void noEscapeAnalisysLocked() {
        synchronized (new Object()) {
            x++;
        }
    }

    @Benchmark
    @Fork(jvmArgsAppend = "-XX:-EliminateLocks")
    public void noLockElisionLocked() {
        synchronized (new Object()) {
            x++;
        }
    }
}
