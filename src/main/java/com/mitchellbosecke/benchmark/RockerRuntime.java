package com.mitchellbosecke.benchmark;

import com.fizzed.rocker.Rocker;
import freemarker.template.TemplateException;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import java.io.IOException;
import java.util.Map;

public class RockerRuntime extends BaseBenchmark {

    private Map<String, Object> context;

    @Setup
    public void setup() throws IOException {
        // no config needed, replicate stocks from context
        context = getContext();
    }

    @Benchmark
    public String benchmark() throws TemplateException, IOException {
        return Rocker.template("templates/stockruntime.rocker.html")
                .bind(context)
                .render()
                .toString();
    }

}
