**XmlParser.java**
```java
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XmlParser {
    private Map<String, Map<String, String>> config;

    public XmlParser(String configFilePath) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        config = parseConfig(configFilePath);
    }

    private Map<String, Map<String, String>> parseConfig(String configFilePath) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(configFilePath));
        Element root = document.getDocumentElement();

        Map<String, Map<String, String>> config = new HashMap<>();

        NodeList elements = root.getElementsByTagName("*");
        for (int i = 0; i < elements.getLength(); i++) {
            Element element = (Element) elements.item(i);
            if (element.hasAttribute("type")) {
                String type = element.getAttribute("type");
                if (type.equals("json")) {
                    Map<String, String> jsonConfig = parseJsonConfig(element);
                    config.put(element.getTagName(), jsonConfig);
                } else if (type.equals("xml")) {
                    Map<String, String> xmlConfig = parseXmlConfig(element);
                    config.put(element.getTagName(), xmlConfig);
                }
            }
        }

        return config;
    }

    private Map<String, String> parseJsonConfig(Element element) throws ParserConfigurationException, SAXException, TransformerException {
        Map<String, String> config = new HashMap<>();

        config.put("key", element.getAttribute("key"));
        config.put("value", element.getAttribute("value"));

        return config;
    }

    private Map<String, String> parseXmlConfig(Element element) throws ParserConfigurationException, SAXException, TransformerException {
        Map<String, String> config = new HashMap<>();

        config.put("key", element.getAttribute("key"));
        config.put("value", element.getAttribute("value"));

        return config;
    }

    public void validateConfig(Map<String, String> config) {
        for (Map.Entry<String, String> entry : config.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (!value.equals(config.get(key))) {
                throw new IllegalArgumentException("Invalid configuration: " + key + " has value '" + value + "' instead of '" + config.get(key) + "'");
            }
        }
    }

    public void printConfig(Map<String, String> config) {
        for (Map.Entry<String, String> entry : config.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
```

**JsonConfig.java**
```java
public class JsonConfig {
    private String key;
    private String value;

    public JsonConfig(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
```

**XmlConfig.java**
```java
public class XmlConfig {
    private String key;
    private String value;

    public XmlConfig(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
```