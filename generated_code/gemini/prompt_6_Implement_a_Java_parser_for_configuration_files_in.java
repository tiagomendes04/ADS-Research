```java
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.regex.Pattern;

// Dependency for JSON parsing:
// If using Maven, add to pom.xml:
// <dependency>
//     <groupId>org.json</groupId>
//     <artifactId>json</artifactId>
//     <version>20231013</version>
// </dependency>
// Or download org.json.jar and add to classpath.

/**
 * Custom exception for configuration parsing errors.
 */
class ConfigParseException extends Exception {
    public ConfigParseException(String message) {
        super(message);
    }

    public ConfigParseException(String message, Throwable cause) {
        super(message, cause);
    }
}

/**
 * Represents the parsed application configuration.
 */
class AppConfig {
    private String appName;
    private String version;
    private int maxConnections;
    private boolean debugMode;
    private int port;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Validates the configuration data.
     *
     * @throws ConfigParseException if validation fails.
     */
    public void validate() throws ConfigParseException {
        if (appName == null || appName.trim().isEmpty()) {
            throw new ConfigParseException("appName cannot be null or empty.");
        }
        if (version == null || !Pattern.matches("\\d+\\.\\d+\\.\\d+", version)) {
            throw new ConfigParseException("version must be in X.Y.Z format.");
        }
        if (maxConnections <= 0) {
            throw new ConfigParseException("maxConnections must be a positive integer.");
        }
        if (port < 1024 || port > 65535) {
            throw new ConfigParseException("port must be between 1024 and 65535.");
        }
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "appName='" + appName + '\'' +
                ", version='" + version + '\'' +
                ", maxConnections=" + maxConnections +
                ", debugMode=" + debugMode +
                ", port=" + port +
                '}';
    }
}

/**
 * Interface for configuration parsers.
 */
interface IConfigParser {
    AppConfig parse(InputStream inputStream) throws ConfigParseException;
}

/**
 * Parses configuration from an XML input stream.
 */
class XmlConfigParser implements IConfigParser {

    @Override
    public AppConfig parse(InputStream inputStream) throws ConfigParseException {
        AppConfig config = new AppConfig();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputStream);
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();

            config.setAppName(getTextContent(root, "appName"));
            config.setVersion(getTextContent(root, "version"));

            Element settingsElement = getFirstChildElement(root, "settings");
            if (settingsElement != null) {
                config.setMaxConnections(getIntegerContent(settingsElement, "maxConnections"));
                config.setDebugMode(getBooleanContent(settingsElement, "debugMode"));
            } else {
                // Default values or throw error if settings are mandatory
                config.setMaxConnections(0); // Will fail validation later
                config.setDebugMode(false);
            }

            // Port might be directly under root or another section. Let's assume root for this example.
            // If missing, will default to 0 and fail validation
            config.setPort(getIntegerContent(root, "port"));


            return config;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ConfigParseException("Error parsing XML configuration: " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            throw new ConfigParseException("Invalid number format in XML configuration: " + e.getMessage(), e);
        }
    }

    private String getTextContent(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }

    private int getIntegerContent(Element parent, String tagName) throws ConfigParseException {
        String content = getTextContent(parent, tagName);
        if (content != null && !content.trim().isEmpty()) {
            try {
                return Integer.parseInt(content.trim());
            } catch (NumberFormatException e) {
                throw new ConfigParseException("Invalid integer value for '" + tagName + "': " + content);
            }
        }
        return 0; // Default or indicate missing
    }

    private boolean getBooleanContent(Element parent, String tagName) {
        String content = getTextContent(parent, tagName);
        return Boolean.parseBoolean(content);
    }

    private Element getFirstChildElement(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0 && nodeList.item(0).getNodeType() == Node.ELEMENT_NODE) {
            return (Element) nodeList.item(0);
        }
        return null;
    }
}

/**
 * Parses configuration from a JSON input stream.
 */
class JsonConfigParser implements IConfigParser {

    @Override
    public AppConfig parse(InputStream inputStream) throws ConfigParseException {
        AppConfig config = new AppConfig();
        try {
            String jsonString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            JSONObject root = new JSONObject(jsonString);

            config.setAppName(root.optString("appName", null));
            config.setVersion(root.optString("version", null));

            if (root.has("settings")) {
                JSONObject settings = root.getJSONObject("settings");
                config.setMaxConnections(settings.optInt("maxConnections", 0));
                config.setDebugMode(settings.optBoolean("debugMode", false));
            } else {
                config.setMaxConnections(0); // Will fail validation later
                config.setDebugMode(false);
            }

            config.setPort(root.optInt("port", 0));

            return config;
        } catch (IOException e) {
            throw new ConfigParseException("Error reading JSON configuration: " + e.getMessage(), e);
        } catch (JSONException e) {
            throw new ConfigParseException("Error parsing JSON configuration: " + e.getMessage(), e);
        }
    }
}

/**
 * Factory to create appropriate configuration parser based on file type.
 */
class ConfigParserFactory {

    public static IConfigParser getParser(String filePath) throws ConfigParseException {
        Objects.requireNonNull(filePath, "File path cannot be null.");
        if (filePath.toLowerCase().endsWith(".xml")) {
            return new XmlConfigParser();
        } else if (filePath.toLowerCase().endsWith(".json")) {
            return new JsonConfigParser();
        } else {
            throw new ConfigParseException("Unsupported configuration file format: " + filePath);
        }
    }
}

/**
 * Main application class to demonstrate configuration parsing and validation.
 */
public class ConfigApp {

    public static void main(String[] args) {
        // --- XML Configuration Example ---
        String xmlConfigFile = "config.xml";
        createXmlConfigFile(xmlConfigFile);
        System.out.println("--- Parsing XML Config ---");
        parseAndValidateConfig(xmlConfigFile);

        // --- JSON Configuration Example ---
        String jsonConfigFile = "config.json";
        createJsonConfigFile(jsonConfigFile);
        System.out.println("\n--- Parsing JSON Config ---");
        parseAndValidateConfig(jsonConfigFile);

        // --- Invalid XML Configuration Example (Missing required fields) ---
        String invalidXmlConfigFile = "invalid_config.xml";
        createInvalidXmlConfigFile(invalidXmlConfigFile);
        System.out.println("\n--- Parsing Invalid XML Config ---");
        parseAndValidateConfig(invalidXmlConfigFile);

        // --- Invalid JSON Configuration Example (Bad port) ---
        String invalidJsonConfigFile = "invalid_config.json";
        createInvalidJsonConfigFile(invalidJsonConfigFile);
        System.out.println("\n--- Parsing Invalid JSON Config ---");
        parseAndValidateConfig(invalidJsonConfigFile);

        // --- Unsupported File Type Example ---
        String unsupportedFile = "config.txt";
        System.out.println("\n--- Parsing Unsupported Config ---");
        parseAndValidateConfig(unsupportedFile);
    }

    private static void parseAndValidateConfig(String filePath) {
        try (InputStream is = new FileInputStream(filePath)) {
            IConfigParser parser = ConfigParserFactory.getParser(filePath);
            AppConfig config = parser.parse(is);
            System.out.println("Parsed Config: " + config);
            config.validate();
            System.out.println("Config validated successfully!");
        } catch (ConfigParseException e) {
            System.err.println("Configuration Error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("File I/O Error for " + filePath + ": " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
        }
    }

    private static void createXmlConfigFile(String fileName) {
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<configuration>\n" +
                "    <appName>WebApp</appName>\n" +
                "    <version>2.1.5</version>\n" +
                "    <settings>\n" +
                "        <maxConnections>50</maxConnections>\n" +
                "        <debugMode>true</debugMode>\n" +
                "    </settings>\n" +
                "    <port>8080</port>\n" +
                "</configuration>";
        writeToFile(fileName, content);
    }

    private static void createInvalidXmlConfigFile(String fileName) {
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<configuration>\n" +
                "    <appName>BadApp</appName>\n" +
                "    <version>1.0</version>\n" + // Invalid version format
                "    <settings>\n" +
                "        <maxConnections>abc</maxConnections>\n" + // Invalid integer
                "        <debugMode>true</debugMode>\n" +
                "    </settings>\n" +
                "    <!-- <port>80</port> Missing or invalid port, will fail validation -->\n" +
                "</configuration>";
        writeToFile(fileName, content);
    }

    private static void createJsonConfigFile(String fileName) {
        String content = "{\n" +
                "    \"appName\": \"ServiceAPI\",\n" +
                "    \"version\": \"3.0.0\",\n" +
                "    \"settings\": {\n" +
                "        \"maxConnections\": 200,\n" +
                "        \"debugMode\": false\n" +
                "    },\n" +
                "    \"port\": 9000\n" +
                "}";
        writeToFile(fileName, content);
    }

    private static void createInvalidJsonConfigFile(String fileName) {
        String content = "{\n" +
                "    \"appName\": \"BadService\",\n" +
                "    \"version\": \"4.0.1\",\n" +
                "    \"settings\": {\n" +
                "        \"maxConnections\": 150,\n" +
                "        \"debugMode\": true\n" +
                "    },\n" +
                "    \"port\": 80\n" + // Invalid port range
                "}";
        writeToFile(fileName, content);
    }

    private static void writeToFile(String fileName, String content) {
        try (java.io.FileWriter writer = new java.io.FileWriter(fileName)) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Error creating file " + fileName + ": " + e.getMessage());
        }
    }
}
```