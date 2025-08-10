package pl.symentis.jvm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.openjdk.jmh.annotations.*;

public class SimpleBenchmark {

    @State(Scope.Benchmark)
    public static class ArrayListBenchmark {
        @Param({"10", "1000", "10000"})
        public int arraySize;

        @Param({"10"})
        public int initialArraySize;

        List<String> arrayList;

        @Setup(Level.Iteration)
        public void setup() {
            arrayList = new ArrayList<>(initialArraySize);
        }
    }

    @State(Scope.Benchmark)
    public static class LinkedListBenchmark {
        @Param({"10", "1000", "10000"})
        public int arraySize;

        List<String> linkedList;

        @Setup(Level.Iteration)
        public void setup() {
            linkedList = new LinkedList<>();
        }
    }

    @Benchmark
    public void arrayList(ArrayListBenchmark benchmark) {
        for (int i = 0; i < benchmark.arraySize; i++) {
            benchmark.arrayList.add("Hello World!!!");
        }
    }

    @Benchmark
    public void linkedList(LinkedListBenchmark benchmark) {
        for (int i = 0; i < benchmark.arraySize; i++) {
            benchmark.linkedList.add("Hello World!!!");
        }
    }
}
