```java
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.Source;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.StringReader;
import java.io.File;
import java.io.IOException;

public class ConfigParser {

    public static JSONObject parseJSON(String jsonContent) throws JSONException {
        return new JSONObject(new JSONTokener(jsonContent));
    }

    public static void validateJSON(JSONObject jsonObject, String schemaPath) throws IOException {
        // JSON Schema validation is not natively supported in Java, you would need a library like org.everit.json.schema
        // This method is a placeholder as actual implementation requires additional dependencies.
    }

    public static Document parseXML(String xmlContent) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlContent));
        return dBuilder.parse(is);
    }

    public static void validateXML(Document document, String schemaPath) throws SAXException, IOException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schema = schemaFactory.newSchema(new File(schemaPath));
        Validator validator = schema.newValidator();
        Source source = new DOMSource(document);
        validator.validate(source);
    }
}
```