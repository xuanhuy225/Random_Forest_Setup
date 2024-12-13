package me.common.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class JsonHelper {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonHelper() {

    }

    /**
     *
     * @param json
     * @param fieldName
     * @throws JsonProcessingException
     * @note function is case-insensitive for both json and keyword
     */
    public static List<Long> getJsonFieldAsLongList(String json, String fieldName) throws JsonProcessingException {
        JsonNode jsonNode = OBJECT_MAPPER.readTree(json.toLowerCase());
        JsonNode jsonField = jsonNode.get(fieldName.toLowerCase());
        if (jsonField != null) {
            List<Long> longList = new ArrayList<>();
            for (JsonNode item : jsonField) {
                longList.add(item.asLong());
            }
            return longList;
        }
        return Collections.emptyList();
    }

    /**
     *
     * @param jsonString
     * @return
     * @throws JsonProcessingException
     */
    public static Map<String, String> mapJsonStringToMap(String jsonString) throws JsonProcessingException {
        JsonNode jsonNode = OBJECT_MAPPER.readTree(jsonString);
        if (jsonNode != null) {
            Map<String, String> resultMap = new HashMap<>();
            Iterator<Map.Entry<String, JsonNode>> iterator = jsonNode.fields();
            while (iterator.hasNext()) {
                Map.Entry<String, JsonNode> next = iterator.next();
                JsonNode value = next.getValue();
                if (value.isArray()) {
                    resultMap.put(next.getKey(), value.toPrettyString());
                } else {
                    resultMap.put(next.getKey(), value.asText());
                }
            }
            return resultMap;
        }
        return Collections.emptyMap();
    }
}
