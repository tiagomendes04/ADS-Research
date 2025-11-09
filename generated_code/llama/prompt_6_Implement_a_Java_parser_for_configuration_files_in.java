**Configuration Parser in Java**
================================

### Maven Dependencies

```xml
<dependencies>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.14.2</version>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.dataformat</groupId>
        <artifactId>jackson-dataformat-xml</artifactId>
        <version>2.14.2</version>
    </dependency>
    <dependency>
        <groupId>org.json</groupId>
        <artifactId>json</artifactId>
        <version>20220924</version>
    </dependency>
</dependencies>
```

### Configuration Parser Class

```java
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationParser {

    private static final String JSON_FILE_EXTENSION = ".json";
    private static final String XML_FILE_EXTENSION = ".xml";

    public static Map<String, Object> parseJsonFile(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(filePath), Map.class);
    }

    public static Map<String, Object> parseXmlFile(String filePath) throws IOException {
        XmlMapper mapper = new XmlMapper();
        return mapper.readValue(new File(filePath), Map.class);
    }

    public static Map<String, Object> parseJsonString(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonString, Map.class);
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, Object> parseXmlString(String xmlString) {
        try {
            XmlMapper mapper = new XmlMapper();
            return mapper.readValue(xmlString, Map.class);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean validateConfiguration(Map<String, Object> configuration, Map<String, Object> schema) {
        if (configuration == null || schema == null) {
            return false;
        }

        for (Map.Entry<String, Object> entry : schema.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (configuration.containsKey(key)) {
                Object configValue = configuration.get(key);

                if (value instanceof Map) {
                    if (!validateConfiguration((Map<String, Object>) configValue, (Map<String, Object>) value)) {
                        return false;
                    }
                } else if (!configValue.equals(value)) {
                    return false;
                }
            } else {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) throws IOException {
        String jsonFilePath = "path/to/config.json";
        String xmlFilePath = "path/to/config.xml";
        String jsonString = "{\"key\":\"value\"}";
        String xmlString = "<root><key>value</key></root>";

        Map<String, Object> jsonConfiguration = parseJsonFile(jsonFilePath);
        Map<String, Object> xmlConfiguration = parseXmlFile(xmlFilePath);
        Map<String, Object> jsonParsed = parseJsonString(jsonString);
        Map<String, Object> xmlParsed = parseXmlString(xmlString);

        Map<String, Object> schema = new HashMap<>();
        schema.put("key", "value");

        System.out.println(validateConfiguration(jsonConfiguration, schema));
        System.out.println(validateConfiguration(xmlConfiguration, schema));
        System.out.println(validateConfiguration(jsonParsed, schema));
        System.out.println(validateConfiguration(xmlParsed, schema));
    }
}
```

### JSON Schema

```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "type": "object",
  "properties": {
    "key": {
      "type": "string"
    }
  },
  "required": ["key"]
}
```

### XML Schema

```xml
<?xml version="1.0" encoding="UTF-8"?>
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema">
    <xs:element name="root">
        <xs:complexType>
            <xs:sequence>
                <xs:element name="key" type="xs:string"/>
            </xs:sequence>
        </xs:complexType>
    </xs:element>
</xs:schema>
```