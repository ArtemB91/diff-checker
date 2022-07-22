package hexlet.code;

import hexlet.code.formatters.IFormatter;
import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;

import java.util.Arrays;

public class Formatter {

    public enum FormatType {
        STYLISH,
        PLAIN
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

        throw new RuntimeException(
                format + " is invalid format. Available formats: " + Arrays.toString(FormatType.values()));
    }

}
