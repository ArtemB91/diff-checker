package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.Map;

public class Parser {

    private static final String JSON_EXT = "json";
    private static final String YAML_EXT = "yml";

    private final String extension;

    Parser(String ext) {
        this.extension = ext;
    }

    public final Map<String, Object> parse(String content) throws IOException {
        ObjectMapper mapper = getObjectMapper();
        return mapper.readValue(content, new TypeReference<Map<String, Object>>() { });
    }

    private ObjectMapper getObjectMapper() {

        if (extension == null || extension.isBlank()) {
            return new ObjectMapper(); // json by default
        }

        if (extension.equalsIgnoreCase(YAML_EXT)) {
            return new ObjectMapper(new YAMLFactory());
        } else if (extension.equalsIgnoreCase(JSON_EXT)) {
            return new ObjectMapper();
        } else {
            throw new RuntimeException(
                    String.format("'%s' - is illegal extension. Supported extensions: json, yml", extension));
        }

    }
}
