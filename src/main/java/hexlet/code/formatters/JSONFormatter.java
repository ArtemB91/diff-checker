package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.Differ;

import java.io.IOException;
import java.util.Map;

public final class JSONFormatter implements IFormatter {
    @Override
    public String format(Map<String, Differ.DiffDescription> diff) throws IOException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(diff);
    }
}
