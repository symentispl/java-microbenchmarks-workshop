/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package pl.symentis.jvm.microbenchmarks.loops;

import static java.util.stream.Collectors.toList;

import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
@Fork(value = 1)
@Measurement(iterations = 1)
@Warmup(iterations = 1)
public class Loops {
    @Param({"10", "1000000"})
    private int size;

    private List<Integer> ints;

    @Setup
    public void setUp() {
        ints = IntStream.of(new int[size]).mapToObj(Integer::valueOf).collect(toList());
    }

    @Benchmark
    public void forLoop(Blackhole bh) {
        for (int i = 0; i < ints.size(); i++) {
            Blackhole.consumeCPU(500);
            bh.consume(ints.get(i));
        }
    }

    @Benchmark
    public void forEach(Blackhole bh) {
        for (Integer i : ints) {
            Blackhole.consumeCPU(500);
            bh.consume(i);
        }
    }

    @Benchmark
    public void iterate(Blackhole bh) {
        Iterator<Integer> iterator = ints.iterator();
        while (iterator.hasNext()) {
            Blackhole.consumeCPU(500);
            bh.consume(iterator.next());
        }
    }

    @Benchmark
    public void stream(Blackhole bh) {
        ints.stream().forEach(i -> {
            Blackhole.consumeCPU(500);
            bh.consume(i);
        });
    }

    @Benchmark
    public void parallelStream(Blackhole bh) {
        ints.stream().parallel().forEach(i -> {
            Blackhole.consumeCPU(500);
            bh.consume(i);
        });
    }
}
