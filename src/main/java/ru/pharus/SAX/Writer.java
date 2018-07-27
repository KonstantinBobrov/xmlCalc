package ru.pharus.SAX;

import java.io.FileOutputStream;
import java.util.List;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class Writer {

    public void writeXML(String outputFile, List<Expression> expressions) throws Exception {
        XMLOutputFactory wtiteFactory = XMLOutputFactory.newFactory();
        XMLStreamWriter writer = wtiteFactory.createXMLStreamWriter(
            new FileOutputStream(outputFile));

        writer.writeStartDocument("UTF-8", "1.0");
        writer.writeCharacters("\n");
        writer.writeStartElement("simpleCalculator");
        writer.writeCharacters("\n");
        writer.writeStartElement("expressionResults");
        writer.writeCharacters("\n");

        expressions.stream().forEach(expression -> {
            try {
                writer.writeStartElement("expressionResult");
                writer.writeCharacters("\n");
                writer.writeStartElement("result");
                writer.writeCharacters(expression.getExpressionResult().toString());
                writer.writeEndElement();
                writer.writeCharacters("\n");
                writer.writeEndElement();
                writer.writeCharacters("\n");
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        });

        writer.writeEndElement();
        writer.writeCharacters("\n");
        writer.writeEndElement();
        writer.writeCharacters("\n");
        writer.writeEndDocument();
    }

}
