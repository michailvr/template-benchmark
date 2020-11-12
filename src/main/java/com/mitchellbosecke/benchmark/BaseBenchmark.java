package com.mitchellbosecke.benchmark;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;

import com.mitchellbosecke.benchmark.model.Stock;

@Fork(5)
@Threads(32)
@Warmup(iterations = 5)
@Measurement(iterations = 10)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class BaseBenchmark {

    protected Map<String, Object> getContext() {
        Map<String, Object> context = new HashMap<>();
        context.put("items", Stock.dummyItems());
        return context;
    }

}
