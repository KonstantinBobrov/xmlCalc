import java.nio.file.Files;
import java.nio.file.Paths;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class Reader {
    final static String sourceFile = "src/main/resources/myTest.xml";


    public static void main(String[] args) throws Exception {

        XMLReader reader = XMLReaderFactory.createXMLReader();

        reader.setContentHandler(new OperationSaxHandler());

        //InputSource source = new InputSource(Files.newInputStream(Paths.get(sourceFile)));
        InputSource source = new InputSource(sourceFile);

        reader.parse(source);
    }


}
