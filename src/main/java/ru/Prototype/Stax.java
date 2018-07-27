package ru.Prototype;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class Stax {

    public static void main(String[] args) throws Exception {

    List<String> stringList = getListLegacyId("src/main/resources/derwent.xml");

    stringList.forEach(System.out::println);

    }

    private static List<String> getListLegacyId(String sourceFile){
        List<String> listLegacyId = new ArrayList<>();

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

        try {
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(sourceFile));

            while (reader.hasNext()) {
                XMLEvent xmlEvent = reader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    String uri = startElement.getNamespaceURI("tsip");
                    //System.out.println(startElement.getName().getLocalPart());
                    if (startElement.getName().getLocalPart().equals("invention")) {

                        Attribute actionAttr = startElement.getAttributeByName(new QName(uri, "action"));
                        Attribute srcAttr = startElement.getAttributeByName(new QName(uri, "src"));
                        Attribute idAttr = startElement.getAttributeByName(new QName(uri,"pan"));

                        if(actionAttr != null &&
                            "delete".equals(actionAttr.getValue()) &&
                            srcAttr != null &&
                            "dwpi".equals(srcAttr.getValue()) &&
                            idAttr !=null) {

                            listLegacyId.add("DWPI" + idAttr.getValue());
                        }
                    }
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return listLegacyId;
    }
}
