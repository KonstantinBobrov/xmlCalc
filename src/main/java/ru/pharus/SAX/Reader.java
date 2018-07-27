package ru.pharus.SAX;

import static ru.pharus.Constants.*;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import ru.pharus.XSDValidator;

public class Reader {
    public static void main(String[] args) throws Exception {

        // Validation
        if (!XSDValidator.validate(sourceFile, XSDFile)) {
            System.out.println("XML File validation failed");
        }

        //Reading and parsing
        XMLReader reader = XMLReaderFactory.createXMLReader();
        OperationSaxHandler operationSaxHandler = new OperationSaxHandler();
        reader.setContentHandler(operationSaxHandler);
        //InputSource source = new InputSource(Files.newInputStream(Paths.get(sourceFile)));
        InputSource source = new InputSource(sourceFile);
        reader.parse(source);

        //Generate Results
        Writer writer = new Writer();
        writer.writeXML(outputFile, operationSaxHandler.getExpressions());
    }


}
