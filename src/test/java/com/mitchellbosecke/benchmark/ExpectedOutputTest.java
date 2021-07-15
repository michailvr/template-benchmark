package com.mitchellbosecke.benchmark;

import com.mitchellbosecke.pebble.error.PebbleException;
import freemarker.template.TemplateException;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.transform.TransformerException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Martin Kouba
 */
public class ExpectedOutputTest {

    @BeforeClass
    public static void beforeClass() {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Test
    public void testFreemarkerOutput() throws IOException, TemplateException {
        Freemarker freemarker = new Freemarker();
        freemarker.setup();
        assertOutput(freemarker.benchmark());
    }

    @Test
    public void testRockerOutput() throws IOException, TemplateException {
        Rocker rocker = new Rocker();
        rocker.setup();
        assertOutput(rocker.benchmark());
    }

    @Test
    public void testRockerRuntimeOutput() throws IOException, TemplateException {
        RockerRuntime rocker = new RockerRuntime();
        rocker.setup();
        assertOutput(rocker.benchmark());
    }

    @Test
    public void testPebbleOutput() throws IOException, PebbleException {
        Pebble pebble = new Pebble();
        pebble.setup();
        assertOutput(pebble.benchmark());
    }

    @Test
    public void testVelocityOutput() throws IOException {
        Velocity velocity = new Velocity();
        velocity.setup();
        assertOutput(velocity.benchmark());
    }

    @Test
    public void testMustacheOutput() throws IOException {
        Mustache mustache = new Mustache();
        mustache.setup();
        assertOutput(mustache.benchmark());
    }

    @Test
    public void testThymeleafOutput() throws IOException, TemplateException {
        Thymeleaf thymeleaf = new Thymeleaf();
        thymeleaf.setup();
        assertOutput(thymeleaf.benchmark());
    }

    @Test
    public void testTrimouOutput() throws IOException {
        Trimou trimou = new Trimou();
        trimou.setup();
        assertOutput(trimou.benchmark());
    }

    @Test
    public void testHbsOutput() throws IOException {
        Handlebars hbs = new Handlebars();
        hbs.setup();
        assertOutput(hbs.benchmark());
    }

    @Test
    public void testJteOutput() throws IOException {
        Jte jte = new Jte();
        jte.setup();
        assertOutput(jte.benchmark());
    }

    @Test
    public void testXslOutput() throws IOException, TransformerException {
        Xsl xsl = new Xsl();
        xsl.setup();
        assertOutputXslt(xsl.benchmark());
    }

    private void assertOutput(final String output) throws IOException {
        assertEquals(readExpectedOutputResource("/expected-output.html"), output.replaceAll("\\s", ""));
    }

    private void assertOutputXslt(final String output) throws IOException {
        assertEquals(readExpectedOutputResource("/expected-output-xslt.html"), output.replaceAll("\\s", ""));
    }

    private String readExpectedOutputResource(String resource) throws IOException {
        StringBuilder builder = new StringBuilder();
        //noinspection ConstantConditions
        try (BufferedReader in = new BufferedReader(new InputStreamReader(ExpectedOutputTest.class.getResourceAsStream(resource)))) {
            for (;;) {
                String line = in.readLine();
                if (line == null) {
                  break;
                }
                builder.append(line);
            }
        }
        // Remove all whitespaces
        return builder.toString().replaceAll("\\s", "");
    }

}
