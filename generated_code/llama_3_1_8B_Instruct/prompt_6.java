```java
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonRootName("config")
public class Configuration {

    @JsonProperty("name")
    private String name;

    @JsonProperty("environment")
    private String environment;

    @JsonProperty("server")
    private Server server;

    @Data
    public static class Server {

        @JsonProperty("host")
        private String host;

        @JsonProperty("port")
        private int port;
    }
}
```

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XmlParser {

    public static Configuration parseXml(String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(Files.readString(Paths.get(filePath)))));
            Element root = document.getDocumentElement();
            return parseElement(root);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Configuration parseElement(Element element) {
        Configuration configuration = new Configuration();
        NodeList nodeList = element.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element child = (Element) nodeList.item(i);
            if (child.getTagName().equals("name")) {
                configuration.setName(child.getTextContent());
            } else if (child.getTagName().equals("environment")) {
                configuration.setEnvironment(child.getTextContent());
            } else if (child.getTagName().equals("server")) {
                configuration.setServer(parseServer(child));
            }
        }
        return configuration;
    }

    private static Configuration.Server parseServer(Element element) {
        Configuration.Server server = new Configuration.Server();
        NodeList nodeList = element.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element child = (Element) nodeList.item(i);
            if (child.getTagName().equals("host")) {
                server.setHost(child.getTextContent());
            } else if (child.getTagName().equals("port")) {
                server.setPort(Integer.parseInt(child.getTextContent()));
            }
        }
        return server;
    }
}
```

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonParser {

    public static Configuration parseJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, Configuration.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
```

```java
import java.io.File;

public class Main {

    public static void main(String[] args) {
        String xmlFilePath = "path/to/config.xml";
        String json = "{\"name\":\"config\",\"environment\":\"dev\",\"server\":{\"host\":\"localhost\",\"port\":8080}}";
        Configuration configuration = JsonParser.parseJson(json);
        System.out.println(configuration);

        configuration = XmlParser.parseXml(xmlFilePath);
        System.out.println(configuration);
    }
}
```