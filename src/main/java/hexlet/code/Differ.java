package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import hexlet.code.Differ.DiffDescription.CHANGE_TYPE;

public class Differ {

    private final static String PROPERTY_CHANGE_TYPE = "change type";
    private final static String PROPERTY_FIRST_VALUE = "first value";
    private final static String PROPERTY_SECOND_VALUE = "second value";

    private final static String CHANGE_TYPE_ADDED = "added";
    private final static String CHANGE_TYPE_DELETED = "deleted";
    private final static String CHANGE_TYPE_MODIFIED = "modified";
    private final static String CHANGE_TYPE_NO_CHANGES = "no changes";

    public static String generate(String filepath1, String filepath2) throws IOException {

        String contentFile1 = new String(Files.readAllBytes(Paths.get(filepath1)));
        String contentFile2 = new String(Files.readAllBytes(Paths.get(filepath2)));

        Map<String, String> data1 = getData(contentFile1);
        Map<String, String> data2 = getData(contentFile2);

        Map<String, DiffDescription> diff = getDiff(data1, data2);

        return diffToString(diff);
    }

    private static Map<String, String> getData(String content) throws IOException {

        if (content.isEmpty()) {
            return new HashMap<>();
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, new TypeReference<Map<String, String>>() {});
    }

    private static Map<String, DiffDescription> getDiff(Map<String, String> data1, Map<String, String> data2) {
        Set<String> keySet = new HashSet<>();
        keySet.addAll(data1.keySet());
        keySet.addAll(data2.keySet());

        Map<String, DiffDescription> diff = new TreeMap<>();

        for (String key : keySet) {

            if (data1.containsKey(key) && !data2.containsKey(key)) {
                diff.put(key, new DiffDescription(CHANGE_TYPE.DELETED, data1.get(key), null));
                continue;
            }

            if (!data1.containsKey(key) && data2.containsKey(key)) {
                diff.put(key, new DiffDescription(CHANGE_TYPE.ADDED, null, data2.get(key)));
                continue;
            }

            if (Objects.equals(data1.get(key), data2.get(key))) {
                diff.put(key, new DiffDescription(CHANGE_TYPE.NO_CHANGES, data1.get(key), data2.get(key)));
            } else {
                diff.put(key, new DiffDescription(CHANGE_TYPE.MODIFIED, data1.get(key), data2.get(key)));
            }

        }
        return diff;
    }

    private static String diffToString(Map<String, DiffDescription> diff) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        String strTemplate = "  %s %s: %s\n";
        for (Map.Entry<String, DiffDescription> keyAndDesc: diff.entrySet()) {
            String key = keyAndDesc.getKey();
            DiffDescription diffDesc = keyAndDesc.getValue();

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

    static class DiffDescription {
        public enum CHANGE_TYPE {
            ADDED,
            DELETED,
            MODIFIED,
            NO_CHANGES
        }
        private final CHANGE_TYPE type;
        private final String firstValue;
        private final String secondValue;

        public CHANGE_TYPE getType() {
            return type;
        }

        public String getFirstValue() {
            return firstValue;
        }

        public String getSecondValue() {
            return secondValue;
        }

        public DiffDescription(CHANGE_TYPE type, String firstValue, String secondValue) {
            this.type = type;
            this.firstValue = firstValue;
            this.secondValue = secondValue;
        }

    }
}
