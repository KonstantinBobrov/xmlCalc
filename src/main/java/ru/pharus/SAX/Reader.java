package SAX;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class Reader {
    final static String sourceFile = "src/main/resources/sampleTest.xml";
    final static String XSDFile = "src/main/resources/Calculator.xsd";

    public static void main(String[] args) throws Exception {

        XSDValidator validator = new XSDValidator();



        XMLReader reader = XMLReaderFactory.createXMLReader();
        reader.setContentHandler(new OperationSaxHandler());

        //InputSource source = new InputSource(Files.newInputStream(Paths.get(sourceFile)));
        InputSource source = new InputSource(sourceFile);

        reader.parse(source);
    }


}
