package reader;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;

public class XPathParser {

    private static String readPath(Node node) {
        Node parent = node.getParentNode();
        if (parent == null) {
            return node.getNodeName();
        }
        return readPath(parent) + "/" + node.getNodeName();
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {

        File file = new File("src\\main\\resources\\test-files.xml");
        XPath path = XPathFactory.newInstance().newXPath();
        String expression = "//*[node()]";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();

        NodeList list = (NodeList) path.compile(expression).evaluate(document, XPathConstants.NODESET);
        for (int i = 0; i < list.getLength(); i++) {
            System.out.println(readPath(list.item(i)));
        }
    }
}
