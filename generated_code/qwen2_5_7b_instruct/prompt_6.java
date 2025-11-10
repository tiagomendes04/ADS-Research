```java
import org.json.JSONObject;
import org.json.XML;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConfigParser {

    public static void parseAndValidateXML(String xmlConfig) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new StringReader(xmlConfig));
        document.getDocumentElement().normalize();

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(document);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);

        String formattedXML = writer.toString();
        System.out.println("Formatted XML:\n" + formattedXML);

        // Basic validation (example: checking for root element)
        if (!document.getDocumentElement().getNodeName().equals("config")) {
            throw new Exception("Root element is not 'config'");
        }

        // Add more validation rules as needed
    }

    public static void parseAndValidateJSON(String jsonConfig) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonConfig);

        // Basic validation (example: checking for root object)
        if (!rootNode.isObject()) {
            throw new Exception("Root node is not an object");
        }

        // Add more validation rules as needed

        // Pretty print JSON
        String prettyPrintedJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        System.out.println("Pretty Printed JSON:\n" + prettyPrintedJson);
    }

    public static void main(String[] args) throws Exception {
        String xmlConfig = "<config><setting1>value1</setting1><setting2>value2</setting2></config>";
        parseAndValidateXML(xmlConfig);

        String jsonConfig = "{\"config\": {\"setting1\": \"value1\", \"setting2\": \"value2\"}}";
        parseAndValidateJSON(jsonConfig);
    }
}
```