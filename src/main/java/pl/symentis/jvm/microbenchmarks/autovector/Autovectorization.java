package pl.symentis.jvm.microbenchmarks.autovector;

import java.util.Random;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@Fork(value = 1)
@Measurement(iterations = 1)
@Warmup(iterations = 1)
public class Autovectorization {
    @State(Scope.Benchmark)
    public static class Vectors {

        @Param({"1000", "100000", "100000000"})
        public int streamSize = 100_000_000;

        private double[] xs;
        private double[] ys;
        private double[] zs;

        @Setup(Level.Iteration)
        public void setUp() {
            var random = new Random();
            this.xs = random.doubles(streamSize).toArray();
            ys = random.doubles(streamSize).toArray();
            zs = new double[streamSize];
        }
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public void autoVector(Vectors iv, Blackhole bh) {
        for (int i = 0; i < iv.streamSize; i++) {
            iv.zs[i] = ((iv.xs[i] * iv.ys[i]) + 1) * -1;
        }
        bh.consume(iv.zs);
    }

    @Fork(jvmArgsAppend = {"-XX:-UseSuperWord"})
    @Benchmark
    @CompilerControl(CompilerControl.Mode.PRINT)
    public void noVector(Vectors iv, Blackhole bh) {
        for (int i = 0; i < iv.streamSize; i++) {
            iv.zs[i] = ((iv.xs[i] * iv.ys[i]) + 1) * -1;
        }
        bh.consume(iv.zs);
    }
}
