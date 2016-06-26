package operate.fNr;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class PX {
    public HashMap<String, String> parseXml(InputStream inStream)
            throws Exception {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(inStream);

        Element root = document.getDocumentElement();

        NodeList childNodes = root.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++) {
            String errorName;
            String errorid;
            Node childNode = childNodes.item(j);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) childNode;
                if (childElement.getAttributeNode("id").getNodeValue().equals("ObsoleteLayoutParam")) {
                    errorName = childElement.getAttributeNode("errorLine1").getNodeValue();
                    errorid = childElement.getAttributeNode("id").getNodeValue();
                    NodeList childSubNodes = childElement.getChildNodes();
                    for (int k = 0; k < childSubNodes.getLength(); k++) {
                        Node childSubNode = childSubNodes.item(k);
                        if (childSubNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element childSubElement = (Element) childSubNode;
                            hashMap.put(childSubElement.getAttributeNode("line").getNodeValue().trim() + childSubElement.getAttributeNode("file").getNodeValue().trim(), childSubElement.getAttributeNode("line").getNodeValue().trim());
                        }
                    }
                }
            }
        }
        return hashMap;
    }

    private void encapsulate(Element element) {

    }

}