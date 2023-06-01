package pl.symentis.jvm.microbenchmarks.lambdas;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(timeUnit = TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@Threads(5)
@Fork(2)
public class ConsumerBenchmark {

    @Param({"100", "1000", "10000"})
    int size;

    AtomicLong count = new AtomicLong(0);

    Logic logic;

    InjectableConsumer injectableConsumer;
    private List<Integer> integers = IntStream.range(1, size).boxed().collect(toList());

    @Benchmark
    public void consumer_as_consumer_object_with_reference_but_field_injected_by_constructor(
            final Blackhole blackhole) {

        this.logic.calculate(integers, this.injectableConsumer);

        blackhole.consume(count.get());
    }

    @Benchmark
    public void consumer_as_consumer_object_per_invocation__with_constructor(final Blackhole blackhole) {

        this.logic.calculate(integers, new InjectableConsumer(count));

        blackhole.consume(count.get());
    }

    @Benchmark
    public void consumer_as_consumer_object_but_without_constructor(final Blackhole blackhole) {

        this.logic.calculate(integers, new SimpleConsumer());

        blackhole.consume(count.get());
    }

    @Benchmark
    public void captured_consumer_as_functional_interface(final Blackhole blackhole) {

        this.logic.calculate(integers, ints -> {
            final long counted = ints.size();

            this.count.set(counted);
        });

        blackhole.consume(count.get());
    }

    @Setup
    public void setup() {
        this.logic = new Logic();
        this.injectableConsumer = new InjectableConsumer(count);
    }

    static class Logic {

        void calculate(List<Integer> ints, final Consumer<List<Integer>> consumer) {
            Optional.ofNullable(ints).ifPresent(consumer);
        }
    }

    class SimpleConsumer implements Consumer<List<Integer>> {
        @Override
        public void accept(final List<Integer> integers) {
            final long counted = integers.size();

            count.set(counted);
        }
    }

    class InjectableConsumer implements Consumer<List<Integer>> {
        private final AtomicLong count;

        InjectableConsumer(final AtomicLong count) {
            this.count = count;
        }

        @Override
        public void accept(final List<Integer> integers) {
            final long counted = integers.size();

            this.count.set(counted);
        }
    }
}
