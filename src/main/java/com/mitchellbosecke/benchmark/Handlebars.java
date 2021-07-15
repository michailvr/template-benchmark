package com.mitchellbosecke.benchmark;

import com.github.jknack.handlebars.Handlebars.SafeString;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.mitchellbosecke.benchmark.model.Stock;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import java.io.IOException;

public class Handlebars extends BaseBenchmark {

    private Object context;

    private Template template;

    @Setup
    public void setup() throws IOException {
        template = new com.github.jknack.handlebars.Handlebars(new ClassPathTemplateLoader("/", ".html"))
                .registerHelper(
                        "minus",
                        (Helper<Stock>) (stock, options) -> stock.getChange() < 0 ? new SafeString("class=\"minus\"")
                                                                                  : null
                ).compile("templates/stocks.hbs");
        this.context = getContext();
    }

    @Benchmark
    public String benchmark() throws IOException {
        return template.apply(context);
    }

}
