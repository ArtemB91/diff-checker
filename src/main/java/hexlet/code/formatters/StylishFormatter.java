package hexlet.code.formatters;

import hexlet.code.Differ;

import java.util.Map;
import java.util.StringJoiner;

public final class StylishFormatter implements IFormatter {
    @Override
    public String format(Map<String, Differ.DiffDescription> diff) {
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add("{");
        String strTemplate = "  %s %s: %s";
        for (Map.Entry<String, Differ.DiffDescription> keyAndDesc: diff.entrySet()) {
            String key = keyAndDesc.getKey();
            Differ.DiffDescription diffDesc = keyAndDesc.getValue();
            switch (diffDesc.getType()) {
                case ADDED:
                    joiner.add(String.format(strTemplate, "+", key, diffDesc.getSecondValue()));
                    break;
                case DELETED:
                    joiner.add(String.format(strTemplate, "-", key, diffDesc.getFirstValue()));
                    break;
                case MODIFIED:
                    joiner.add(String.format(strTemplate, "-", key, diffDesc.getFirstValue()));
                    joiner.add(String.format(strTemplate, "+", key, diffDesc.getSecondValue()));
                    break;
                case NO_CHANGES:
                    joiner.add(String.format(strTemplate, " ", key, diffDesc.getFirstValue()));
                    break;
                default:
                    throw new RuntimeException(key + " - is invalid change type");
            }
        }
        joiner.add("}");
        return joiner.toString();
    }
}
