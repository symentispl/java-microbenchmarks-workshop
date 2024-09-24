package pl.symentis.jvm.microbenchmarks.inlining;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;

/**
 * We can use HotSpot-specific functionality to tell the compiler what do we want to do with particular methods. To demonstrate the effects, we end up with 3
 * methods in this sample. These are our targets:
 * <p>
 * - first method is prohibited from inlining
 * <p>
 * - second method is forced to inline
 * <p>
 * - third method is prohibited from compiling
 * <p>
 * <p>
 * <p>
 * We might even place the annotations directly to the benchmarked
 * <p>
 * methods, but this expresses the intent more clearly.
 */
@Fork(value = 1)
@Measurement(iterations = 1)
@Warmup(iterations = 1)
public class Inlining {

    public void target_blank() {
        // this method was intentionally left blank
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public void target_dontInline() {
        // this method was intentionally left blank
    }

    @CompilerControl(CompilerControl.Mode.INLINE)
    public void target_inline() {
        // this method was intentionally left blank
    }

    @CompilerControl(CompilerControl.Mode.EXCLUDE)
    public void target_exclude() {
        // this method was intentionally left blank
    }

    /*
     * These method measures the calls performance.
     */
    @Benchmark
    public void baseline() {
        // this method was intentionally left blank
    }

    @Benchmark
    public void blank() {
        target_blank();
    }

    @Benchmark
    public void dontinline() {
        target_dontInline();
    }

    @Benchmark
    public void inline() {
        target_inline();
    }

    @Benchmark
    public void exclude() {
        target_exclude();
    }
}
