```java
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import java.io.File;
import java.io.IOException;

public class ConfigParser {

    public static class ConfigNode {
        private String key;
        private String value;

        public ConfigNode(String key, String value) {
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

    public static ConfigNode parseConfig(String configString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(configString);
        return new ConfigNode(rootNode.get("key").asText(), rootNode.get("value").asText());
    }

    public static ConfigNode parseConfigFile(File configFile) throws IOException {
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(configFile);
        Element root = doc.getRootElement();
        return new ConfigNode(root.getChild("key").getText(), root.getChild("value").getText());
    }
}
```