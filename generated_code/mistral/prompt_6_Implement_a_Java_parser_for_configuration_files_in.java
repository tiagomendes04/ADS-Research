Here's a Java implementation for parsing and validating XML and JSON configuration files:

```java
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ConfigParser {

    public static Map<String, Object> parseConfig(String filePath) throws Exception {
        if (filePath.endsWith(".xml")) {
            return parseXmlConfig(filePath);
        } else if (filePath.endsWith(".json")) {
            return parseJsonConfig(filePath);
        } else {
            throw new IllegalArgumentException("Unsupported file format");
        }
    }

    private static Map<String, Object> parseXmlConfig(String filePath) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filePath));

        Map<String, Object> config = new HashMap<>();
        parseXmlElement(document.getDocumentElement(), config);
        return config;
    }

    private static void parseXmlElement(Element element, Map<String, Object> config) {
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeType() == Element.ELEMENT_NODE) {
                Element child = (Element) children.item(i);
                String key = child.getNodeName();
                String value = child.getTextContent().trim();

                if (value.isEmpty()) {
                    Map<String, Object> nested = new HashMap<>();
                    parseXmlElement(child, nested);
                    config.put(key, nested);
                } else {
                    config.put(key, value);
                }
            }
        }
    }

    private static Map<String, Object> parseJsonConfig(String filePath) throws IOException, JSONException {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            JSONTokener tokener = new JSONTokener(inputStream);
            JSONObject jsonObject = new JSONObject(tokener);
            return convertJsonToMap(jsonObject);
        }
    }

    private static Map<String, Object> convertJsonToMap(JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                map.put(key, convertJsonToMap((JSONObject) value));
            } else if (value instanceof org.json.JSONArray) {
                // Handle arrays if needed
                map.put(key, value.toString());
            } else {
                map.put(key, value);
            }
        }
        return map;
    }

    public static void validateXmlConfig(String filePath, String xsdPath) throws Exception {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new File(xsdPath));
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(new File(filePath)));
    }

    public static void main(String[] args) {
        try {
            // Example usage
            Map<String, Object> config = ConfigParser.parseConfig("config.xml");
            System.out.println("Parsed config: " + config);

            // Validate XML against XSD
            ConfigParser.validateXmlConfig("config.xml", "config.xsd");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

Dependencies (add to your pom.xml if using Maven):

```xml
<dependencies>
    <!-- JSON parsing -->
    <dependency>
        <groupId>org.json</groupId>
        <artifactId>json</artifactId>
        <version>20231013</version>
    </dependency>

    <!-- XML parsing and validation -->
    <dependency>
        <groupId>javax.xml</groupId>
        <artifactId>javax.xml-api</artifactId>
        <version>1.4.02</version>
    </dependency>
</dependencies>
```