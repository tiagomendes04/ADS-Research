```java
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import javax.xml.bind.*;
import javax.xml.bind.annotation.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ConfigParser {

    public static Config parse(Path path) throws IOException, ValidationException {
        String content = new String(Files.readAllBytes(path));
        Config config;
        if (isJson(content)) {
            config = parseJson(content);
        } else if (isXml(content)) {
            config = parseXml(content);
        } else {
            throw new IOException("Unsupported configuration format");
        }
        config.validate();
        return config;
    }

    private static boolean isJson(String content) {
        String trimmed = content.trim();
        return trimmed.startsWith("{");
    }

    private static boolean isXml(String content) {
        String trimmed = content.trim();
        return trimmed.startsWith("<");
    }

    private static Config parseJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, Config.class);
        } catch (MismatchedInputException e) {
            throw new IOException("Invalid JSON configuration", e);
        }
    }

    private static Config parseXml(String xml) throws IOException {
        try {
            JAXBContext ctx = JAXBContext.newInstance(Config.class);
            Unmarshaller um = ctx.createUnmarshaller();
            try (StringReader sr = new StringReader(xml)) {
                return (Config) um.unmarshal(sr);
            }
        } catch (JAXBException e) {
            throw new IOException("Invalid XML configuration", e);
        }
    }

    @XmlRootElement(name = "config")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Config {

        @XmlElement(required = true)
        private String name;

        @XmlElement(required = true)
        private Integer timeout;

        @XmlElementWrapper(name = "servers")
        @XmlElement(name = "server")
        private List<String> servers;

        // Default constructor required by JAXB/Jackson
        public Config() {}

        // Getters and setters (Jackson can use fields directly, but they are useful)

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getTimeout() {
            return timeout;
        }

        public void setTimeout(Integer timeout) {
            this.timeout = timeout;
        }

        public List<String> getServers() {
            return servers;
        }

        public void setServers(List<String> servers) {
            this.servers = servers;
        }

        public void validate() throws ValidationException {
            List<String> errors = new ArrayList<>();
            if (name == null || name.trim().isEmpty()) {
                errors.add("name must be provided and non‑empty");
            }
            if (timeout == null || timeout <= 0) {
                errors.add("timeout must be a positive integer");
            }
            if (servers == null || servers.isEmpty()) {
                errors.add("at least one server must be defined");
            } else {
                for (String s : servers) {
                    if (s == null || s.trim().isEmpty()) {
                        errors.add("server entries must be non‑empty strings");
                        break;
                    }
                }
            }
            if (!errors.isEmpty()) {
                throw new ValidationException(String.join("; ", errors));
            }
        }
    }

    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }
}
```