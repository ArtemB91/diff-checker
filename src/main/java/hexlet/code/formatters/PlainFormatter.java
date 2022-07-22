package hexlet.code.formatters;

import hexlet.code.Differ;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

public final class PlainFormatter implements IFormatter {

    private static final Map<Class<?>, Class<?>> WRAPPER_TYPE_MAP;
    static {
        WRAPPER_TYPE_MAP = new HashMap<Class<?>, Class<?>>();
        WRAPPER_TYPE_MAP.put(Integer.class, int.class);
        WRAPPER_TYPE_MAP.put(Byte.class, byte.class);
        WRAPPER_TYPE_MAP.put(Character.class, char.class);
        WRAPPER_TYPE_MAP.put(Boolean.class, boolean.class);
        WRAPPER_TYPE_MAP.put(Double.class, double.class);
        WRAPPER_TYPE_MAP.put(Float.class, float.class);
        WRAPPER_TYPE_MAP.put(Long.class, long.class);
        WRAPPER_TYPE_MAP.put(Short.class, short.class);
        WRAPPER_TYPE_MAP.put(Void.class, void.class);
        WRAPPER_TYPE_MAP.put(String.class, String.class);
    }
    @Override
    public String format(Map<String, Differ.DiffDescription> diff) {
        StringJoiner joiner = new StringJoiner("\n");
        for (Map.Entry<String, Differ.DiffDescription> keyAndDesc : diff.entrySet()) {
            String key = keyAndDesc.getKey();
            Differ.DiffDescription diffDesc = keyAndDesc.getValue();

            String row = "";
            switch (diffDesc.getType()) {
                case ADDED:
                    row = String.format("Property '%s' was added with value: %s",
                            key,
                            valueToString(diffDesc.getSecondValue()));
                    joiner.add(row);
                    break;
                case DELETED:
                    row = String.format("Property '%s' was removed", key);
                    joiner.add(row);
                    break;
                case MODIFIED:
                    row = String.format("Property '%s' was updated. From %s to %s",
                            key,
                            valueToString(diffDesc.getFirstValue()),
                            valueToString(diffDesc.getSecondValue()));
                    joiner.add(row);
                    break;
                case NO_CHANGES:
                    break;
                default:
                    throw new RuntimeException(key + " - is invalid change type");
            }
        }
        return joiner.toString();
    }

    private String valueToString(Object value) {

        if (value == null) {
            return Objects.toString(null);
        }

        if (isComplexValue(value)) {
            return "[complex value]";
        }

        if (value.getClass() == String.class) {
            return "'" + value + "'";
        }

        return value.toString();
    }

    private boolean isComplexValue(Object source) {
        return !WRAPPER_TYPE_MAP.containsKey(source.getClass());
    }

}
