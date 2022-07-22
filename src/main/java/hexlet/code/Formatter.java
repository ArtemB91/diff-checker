package hexlet.code;

import hexlet.code.formatters.IFormatter;
import hexlet.code.formatters.JSONFormatter;
import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;

import java.util.Arrays;

public class Formatter {

    public enum FormatType {
        STYLISH,
        PLAIN,
        JSON
    }

    public static IFormatter newFormatter(FormatType formatType) {

        if (formatType == null) {
            return null;
        }

        if (formatType == FormatType.STYLISH) {
            return new StylishFormatter();
        }

        if (formatType == FormatType.PLAIN) {
            return new PlainFormatter();
        }

        if (formatType == FormatType.JSON) {
            return new JSONFormatter();
        }

        throw new RuntimeException(formatType + " - is invalid format type");
    }

    public static IFormatter newFormatter() {
        return new StylishFormatter(); // by default
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
