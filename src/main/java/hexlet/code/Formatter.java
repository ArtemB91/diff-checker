package hexlet.code;

import java.util.Arrays;
import java.util.Map;

public class Formatter {

    private final FormatType formatType;
    public enum FormatType {
        STYLISH
    }

    Formatter(FormatType formatType) {
        this.formatType = formatType;
    }

    public final String format(Map<String, Differ.DiffDescription> diff) {
        if (formatType == FormatType.STYLISH) {
            return formatStylish(diff);
        } else {
            throw new RuntimeException(formatType + " - is invalid format type");
        }
    }

    private String formatStylish(Map<String, Differ.DiffDescription> diff) {

        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        String strTemplate = "  %s %s: %s\n";
        for (Map.Entry<String, Differ.DiffDescription> keyAndDesc: diff.entrySet()) {
            String key = keyAndDesc.getKey();
            Differ.DiffDescription diffDesc = keyAndDesc.getValue();

            switch (diffDesc.getType()) {
                case ADDED:
                    builder.append(String.format(strTemplate, "+", key, diffDesc.getSecondValue()));
                    break;
                case DELETED:
                    builder.append(String.format(strTemplate, "-", key, diffDesc.getFirstValue()));
                    break;
                case MODIFIED:
                    builder.append(String.format(strTemplate, "-", key, diffDesc.getFirstValue()));
                    builder.append(String.format(strTemplate, "+", key, diffDesc.getSecondValue()));
                    break;
                case NO_CHANGES:
                    builder.append(String.format(strTemplate, " ", key, diffDesc.getFirstValue()));
                    break;
                default:
                    throw new RuntimeException(key + " - is invalid change type");
            }
        }
        builder.append("}");
        return builder.toString();

    }

    public static FormatType getFormatType(String format) {
        if (format == null) {
            return null;
        }

        if (format.equalsIgnoreCase("stylish")) {
            return FormatType.STYLISH;
        } else {
            throw new RuntimeException(
                    format + " is invalid format. Available formats: " + Arrays.toString(FormatType.values()));
        }
    }

}
