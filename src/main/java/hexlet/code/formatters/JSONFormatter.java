package hexlet.code.formatters;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.util.Map;

public final class JSONFormatter {
    public static String format(Map<String, Object> diff) throws IOException {
        JsonMapper jsonMapper = JsonMapper.builder()
                .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
                .build();
        return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(diff);
    }
}
