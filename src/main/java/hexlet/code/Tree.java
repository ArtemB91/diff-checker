package hexlet.code;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

public class Tree {

    public static Map<String, Object> getDiff(Map<String, Object> data1, Map<String, Object> data2) {
        Set<String> keySet = new HashSet<>();
        keySet.addAll(data1.keySet());
        keySet.addAll(data2.keySet());

        Map<String, Object> diff = new TreeMap<>();

        for (String key : keySet) {
            Map<String, Object> diffDescription = new HashMap<>();

            diffDescription.put("type", "");
            diffDescription.put("value1", data1.get(key));
            diffDescription.put("value2", data2.get(key));

            diff.put(key, diffDescription);

            if (data1.containsKey(key) && !data2.containsKey(key)) {
                diffDescription.put("type", "deleted");
                continue;
            }

            if (!data1.containsKey(key) && data2.containsKey(key)) {
                diffDescription.put("type", "added");
                continue;
            }

            if (Objects.equals(data1.get(key), data2.get(key))) {
                diffDescription.put("type", "no_changes");

            } else {
                diffDescription.put("type", "modified");
            }

        }
        return diff;
    }

}
