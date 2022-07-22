package hexlet.code.formatters;

import hexlet.code.Differ;

import java.util.Map;

public interface IFormatter {
    String format(Map<String, Differ.DiffDescription> diff);
}
