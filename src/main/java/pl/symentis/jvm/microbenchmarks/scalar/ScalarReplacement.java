package pl.symentis.jvm.microbenchmarks.scalar;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
public class ScalarReplacement {

    @Benchmark
    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public void scalarReplacement(Blackhole bh) {
        var x = 1;
        var y = 1;
        bh.consume(x + y);
    }

    class Data {
        private int x = 1;
        private int y = 1;
    }
}
