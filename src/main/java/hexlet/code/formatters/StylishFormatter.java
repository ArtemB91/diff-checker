package hexlet.code.formatters;

import java.util.Map;
import java.util.StringJoiner;

public final class StylishFormatter {
    public static String format(Map<String, Object> diff) {
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add("{");
        String strTemplate = "  %s %s: %s";
        for (Map.Entry<String, Object> keyAndDesc: diff.entrySet()) {
            String key = keyAndDesc.getKey();

            Map<String, Object> diffDesc = (Map<String, Object>) keyAndDesc.getValue();
            String type = (String) diffDesc.get("type");
            switch (type) {
                case "added":
                    joiner.add(String.format(strTemplate, "+", key, diffDesc.get("value2")));
                    break;
                case "deleted":
                    joiner.add(String.format(strTemplate, "-", key, diffDesc.get("value1")));
                    break;
                case "modified":
                    joiner.add(String.format(strTemplate, "-", key, diffDesc.get("value1")));
                    joiner.add(String.format(strTemplate, "+", key, diffDesc.get("value2")));
                    break;
                case "no_changes":
                    joiner.add(String.format(strTemplate, " ", key, diffDesc.get("value1")));
                    break;
                default:
                    throw new RuntimeException(type + " - is invalid change type");
            }
        }
        joiner.add("}");
        return joiner.toString();
    }
}
