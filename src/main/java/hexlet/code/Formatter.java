package hexlet.code;

import hexlet.code.formatters.JSONFormatter;
import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class Formatter {

    public enum FormatType {
        STYLISH,
        PLAIN,
        JSON
    }
    public static String format(Map<String, Object> diff, FormatType formatType) throws IOException {
        if (formatType == null) {
            return null;
        }

        if (formatType == FormatType.STYLISH) {
            return StylishFormatter.format(diff);
        }

        if (formatType == FormatType.PLAIN) {
            return PlainFormatter.format(diff);
        }

        if (formatType == FormatType.JSON) {
            return JSONFormatter.format(diff);
        }

        throw new RuntimeException(formatType + " - is invalid format type");
    }



    public static FormatType getFormatType(String format) {
        if (format == null) {
            return null;
        }

        if (format.equalsIgnoreCase("stylish")) {
            return FormatType.STYLISH;
        }

        if (format.equalsIgnoreCase("plain")) {
            return FormatType.PLAIN;
        }

        if (format.equalsIgnoreCase("json")) {
            return FormatType.JSON;
        }

        throw new RuntimeException(
                format + " is invalid format. Available formats: " + Arrays.toString(FormatType.values()));
    }

}
