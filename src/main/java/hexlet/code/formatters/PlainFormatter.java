package hexlet.code.formatters;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

public final class PlainFormatter {

    public static String format(Map<String, Object> diff) {
        StringJoiner joiner = new StringJoiner("\n");
        for (Map.Entry<String, Object> keyAndDesc : diff.entrySet()) {
            String key = keyAndDesc.getKey();
            Map<String, Object> diffDesc = (Map<String, Object>) keyAndDesc.getValue();

            String row = "";

            String type = (String) diffDesc.get("type");
            switch (type) {
                case "added":
                    row = String.format("Property '%s' was added with value: %s",
                            key,
                            valueToString(diffDesc.get("value2")));
                    joiner.add(row);
                    break;
                case "deleted":
                    row = String.format("Property '%s' was removed", key);
                    joiner.add(row);
                    break;
                case "modified":
                    row = String.format("Property '%s' was updated. From %s to %s",
                            key,
                            valueToString(diffDesc.get("value1")),
                            valueToString(diffDesc.get("value2")));
                    joiner.add(row);
                    break;
                case "no_changes":
                    break;
                default:
                    throw new RuntimeException(type + " - is invalid change type");
            }
        }
        return joiner.toString();
    }

    private static String valueToString(Object value) {

        if (value == null) {
            return Objects.toString(null);
        }

        if (value instanceof Map<?, ?> || value instanceof List<?>) {
            return "[complex value]";
        }

        if (value.getClass() == String.class) {
            return "'" + value + "'";
        }

        return value.toString();
    }

}
