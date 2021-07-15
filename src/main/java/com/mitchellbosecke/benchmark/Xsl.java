package com.mitchellbosecke.benchmark;

import com.mitchellbosecke.benchmark.model.Stock;
import org.apache.xalan.templates.OutputProperties;
import org.apache.xalan.transformer.TransformerImpl;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class Xsl extends BaseBenchmark {

    private Templates templates;

    private List<Stock> items;

    @Setup
    public void setup() throws IOException, TransformerConfigurationException {
        URL url = getClass().getResource("/templates/stocks.xsl.html");
        templates = TransformerFactory.newInstance().newTemplates(new StreamSource(
                Objects.requireNonNull(url).openStream(),
                url.getPath()
        ));
        this.items = Stock.dummyItems();
    }

    @Benchmark
    public String benchmark() throws TransformerException {
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerImpl transformer = (TransformerImpl) templates.newTransformer();
        OutputProperties format = transformer.getOutputFormat();
        format.setProperty("{http://xml.apache.org/xalan}omit-meta-tag", "yes");
        transformer.transform(getXml(), result);
        return writer.toString();
    }

    private StreamSource getXml() {
        StringBuilder result = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        result.append("<items>");
        for (Stock item : items) {
            result.append("<item>");
            result.append("<change>").append(item.getChange()).append("</change>");
            result.append("<symbol>").append(item.getSymbol()).append("</symbol>");
            result.append("<url>").append(item.getUrl()).append("</url>");
            result.append("<name>").append(item.getName()).append("</name>");
            result.append("<price>").append(item.getPrice()).append("</price>");
            result.append("<ratio>").append(item.getRatio()).append("</ratio>");
            result.append("</item>");
        }
        result.append("</items>");
        return new StreamSource(new StringReader(result.toString()));
    }
}
