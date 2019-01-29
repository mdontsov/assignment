package reader;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;

public class StAXReader {

    static StAXReader stAXReader;
    static XMLEvent event;
    static StartElement startElement;
    static EndElement endElement;

    public static void main(String[] args) throws FileNotFoundException, javax.xml.stream.XMLStreamException {

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();

        File file = new File("src\\main\\resources\\test-files.xml");

        InputStream stream = new FileInputStream(file);
        XMLEventReader eventReader = inputFactory.createXMLEventReader(stream);

        while (eventReader.hasNext()) {
            event = eventReader.nextEvent();
            stAXReader = new StAXReader();

            if (event.isStartElement()) {
                startElement = event.asStartElement();

                String startElementText = startElement.getName().getLocalPart();

                if (startElementText.equalsIgnoreCase("child")) {
                    Iterator<Attribute> attributes = startElement.getAttributes();
                    String value = attributes.next().getValue();
                    if (value.equalsIgnoreCase("false")) {
                        event = eventReader.nextEvent();
                        System.out.print(event.asCharacters().getData());
                    }

                }

                if (event.isStartElement()) {
                    if (startElementText.equalsIgnoreCase("name")) {
                        event = eventReader.nextEvent();
                        System.out.print(event.asCharacters().getData() + "/");
                    }
                }
            }

            if (event.isEndElement()) {
                endElement = event.asEndElement();
                String endElementText = endElement.getName().getLocalPart();
                if (endElementText.equalsIgnoreCase("child")) {
                    stAXReader = null;
                }
            }
        }
    }
}
