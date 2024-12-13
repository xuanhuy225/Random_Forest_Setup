package me.common.util;

import me.common.util.jackson.exception.JacksonException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
public class JacksonUtils {
    private static ObjectMapper mapper;

    private static final Set<JsonReadFeature> JSON_READ_FEATURES_ENABLED = Sets.newHashSet(
            //Allow Java comments in JSON
            JsonReadFeature.ALLOW_JAVA_COMMENTS,
            //Allow json to have fields not enclosed in double quotes
            JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES,
            //Allow json to have fields enclosed in single quotes
            JsonReadFeature.ALLOW_SINGLE_QUOTES,
            //Allow json to have ascii control characters that are not enclosed in quotes
            JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS,
            //Allow json number type numbers to have leading 0 (example: 0001)
            JsonReadFeature.ALLOW_LEADING_ZEROS_FOR_NUMBERS,
            //Allow json to have NaN, INF, -INF as number type
            JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS,
            //Allow only Key without Value
            JsonReadFeature.ALLOW_MISSING_VALUES,
            //Allow multiple commas at the end of the array json
            JsonReadFeature.ALLOW_TRAILING_COMMA
    );

    static {
        try {
            //initialization
            mapper = initMapper();
        } catch (Exception e) {
            log.error("jackson config error", e);
        }
    }

    public static ObjectMapper initMapper() {
        JsonMapper.Builder builder = JsonMapper.builder().enable(JSON_READ_FEATURES_ENABLED.toArray(new JsonReadFeature[0]));
        return initMapperConfig(builder.build());
    }

    public static ObjectMapper initMapperConfig(ObjectMapper objectMapper) {
        String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
        objectMapper.setDateFormat(new SimpleDateFormat(dateTimeFormat));
        // configure the serialization level
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //Configure JSON indentation support
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        //Allow a single value to be treated as an array
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        //Forbid duplicate keys, throw an exception
        objectMapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
        //It is forbidden to deserialize Enum using int to represent Enum's order(), throwing an exception
        objectMapper.enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
        //Do not report an error when there are attributes that cannot be mapped
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //Do not throw an exception when the object is empty
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        //Time format
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // allow unknown fields
        objectMapper.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);
        //When serializing BigDecimal, output the original number or scientific notation, the default is false, that is, whether to output in toPlainString() scientific notation
        objectMapper.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        //Identify Java8 time
        objectMapper.registerModule(new ParameterNamesModule());
        objectMapper.registerModule(new Jdk8Module());
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)))
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
        objectMapper.registerModule(javaTimeModule);
        //Identify the class of the Guava package
        objectMapper.registerModule(new GuavaModule());
        return objectMapper;
    }

    public static ObjectMapper getObjectMapper() {
        return mapper;
    }

    /**
     * JSON deserialization
     */
    public static <V> V from(URL url, Class<V> type) {
        try {
            return mapper.readValue(url, type);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, url: {}, type: {}", url.getPath(), type, e);
        }
    }

    /**
     * JSON deserialization
     */
    public static <V> V from(URL url, TypeReference<V> type) {
        try {
            return mapper.readValue(url, type);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, url: {}, type: {}", url.getPath(), type, e);
        }
    }

    /**
     * JSON deserialization (List)
     */
    public static <V> List<V> fromList(URL url, Class<V> type) {
        try {
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, type);
            return mapper.readValue(url, collectionType);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, url: {}, type: {}", url.getPath(), type, e);
        }
    }

    /**
     * JSON deserialization
     */
    public static <V> V from(InputStream inputStream, Class<V> type) {
        try {
            return mapper.readValue(inputStream, type);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, type: {}", type, e);
        }
    }

    /**
     * JSON deserialization
     */
    public static <V> V from(InputStream inputStream, TypeReference<V> type) {
        try {
            return mapper.readValue(inputStream, type);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, type: {}", type, e);
        }
    }
    /**
     * JSON deserialization
     */
    public static <V> V from(String json, TypeReference<V> type) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, type: {}", type, e);
        }
    }
    /**
     * JSON deserialization
     */
    public static <V> V from(String json, Type type) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            JavaType javaType = mapper.getTypeFactory().constructType(type);
            return mapper.readValue(json, javaType);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, json: {}, type: {}", json, type, e);
        }
    }

    /**
     * JSON deserialization (List)
     */
    public static <V> List<V> fromList(String json, Class<V> type) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, type);
            return mapper.readValue(json, collectionType);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, json: {}, type: {}", json, type, e);
        }
    }

    /**
     * JSON deserialization (Map)
     */
    public static Map<String, Object> fromMap(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class);
            return mapper.readValue(json, mapType);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, json: {}, type: {}", json, e);
        }
    }

    /**
     * Serialize to JSON
     */
    public static <V> String to(List<V> list) {
        try {
            return mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new JacksonException("jackson to error, data: {}", list, e);
        }
    }

    /**
     * Serialize to JSON
     */
    public static <V> String to(V v) {
        try {
            return mapper.writeValueAsString(v);
        } catch (JsonProcessingException e) {
            throw new JacksonException("jackson to error, data: {}", v, e);
        }
    }

    public static <V> Map<String, Object> toMap(V v) {
        return mapper.convertValue(v, Map.class);
    }


    /**
     * Serialize to JSON
     */
    public static <V> void toFile(String path, List<V> list) {
        try (Writer writer = new FileWriter(new File(path), true)) {
            mapper.writer().writeValues(writer).writeAll(list);
        } catch (Exception e) {
            throw new JacksonException("jackson to file error, path: {}, list: {}", path, list, e);
        }
    }

    /**
     * Serialize to JSON
     */
    public static <V> void toFile(String path, V v) {
        try (Writer writer = new FileWriter(new File(path), true)) {
            mapper.writer().writeValues(writer).write(v);
        } catch (Exception e) {
            throw new JacksonException("jackson to file error, path: {}, data: {}", path, v, e);
        }
    }

    /**
     * Get a field from the json string
     *
     * @return String, default is null
     */
    public static String getAsString(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            JsonNode jsonNode = getAsJsonObject(json, key);
            if (null == jsonNode) {
                return null;
            }
            return getAsString(jsonNode);
        } catch (Exception e) {
            throw new JacksonException("jackson get string error, json: {}, key: {}", json, key, e);
        }
    }

    private static String getAsString(JsonNode jsonNode) {
        return jsonNode.isTextual() ? jsonNode.textValue() : jsonNode.toString();
    }

    /**
     * Get a field from the json string
     *
     * @return int, default is 0
     */
    public static int getAsInt(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return 0;
        }
        try {
            JsonNode jsonNode = getAsJsonObject(json, key);
            if (null == jsonNode) {
                return 0;
            }
            return jsonNode.isInt() ? jsonNode.intValue() : Integer.parseInt(getAsString(jsonNode));
        } catch (Exception e) {
            throw new JacksonException("jackson get int error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * Get a field from the json string
     *
     * @return long, default is 0
     */
    public static long getAsLong(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return 0L;
        }
        try {
            JsonNode jsonNode = getAsJsonObject(json, key);
            if (null == jsonNode) {
                return 0L;
            }
            return jsonNode.isLong() ? jsonNode.longValue() : Long.parseLong(getAsString(jsonNode));
        } catch (Exception e) {
            throw new JacksonException("jackson get long error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * Get a field from the json string
     *
     * @return double, default is 0.0
     */
    public static double getAsDouble(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return 0.0;
        }
        try {
            JsonNode jsonNode = getAsJsonObject(json, key);
            if (null == jsonNode) {
                return 0.0;
            }
            return jsonNode.isDouble() ? jsonNode.doubleValue() : Double.parseDouble(getAsString(jsonNode));
        } catch (Exception e) {
            throw new JacksonException("jackson get double error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * Get a field from the json string
     *
     * @return BigInteger, default is 0.0
     */
    public static BigInteger getAsBigInteger(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return new BigInteger(String.valueOf(0.00));
        }
        try {
            JsonNode jsonNode = getAsJsonObject(json, key);
            if (null == jsonNode) {
                return new BigInteger(String.valueOf(0.00));
            }
            return jsonNode.isBigInteger() ? jsonNode.bigIntegerValue() : new BigInteger(getAsString(jsonNode));
        } catch (Exception e) {
            throw new JacksonException("jackson get big integer error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * Get a field from the json string
     *
     * @return BigDecimal, default is 0.00
     */
    public static BigDecimal getAsBigDecimal(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return new BigDecimal("0.00");
        }
        try {
            JsonNode jsonNode = getAsJsonObject(json, key);
            if (null == jsonNode) {
                return new BigDecimal("0.00");
            }
            return jsonNode.isBigDecimal() ? jsonNode.decimalValue() : new BigDecimal(getAsString(jsonNode));
        } catch (Exception e) {
            throw new JacksonException("jackson get big decimal error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * Get a field from the json string
     *
     * @return boolean, default is false
     */
    public static boolean getAsBoolean(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return false;
        }
        try {
            JsonNode jsonNode = getAsJsonObject(json, key);
            if (null == jsonNode) {
                return false;
            }
            if (jsonNode.isBoolean()) {
                return jsonNode.booleanValue();
            } else {
                if (jsonNode.isTextual()) {
                    String textValue = jsonNode.textValue();
                    if ("1".equals(textValue)) {
                        return true;
                    } else {
                        return BooleanUtils.toBoolean(textValue);
                    }
                } else {//number
                    return BooleanUtils.toBoolean(jsonNode.intValue());
                }
            }
        } catch (Exception e) {
            throw new JacksonException("jackson get boolean error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * Get a field from the json string
     *
     * @return byte[], default is null
     */
    public static byte[] getAsBytes(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            JsonNode jsonNode = getAsJsonObject(json, key);
            if (null == jsonNode) {
                return null;
            }
            return jsonNode.isBinary() ? jsonNode.binaryValue() : getAsString(jsonNode).getBytes();
        } catch (Exception e) {
            throw new JacksonException("jackson get byte error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * Get a field from the json string
     *
     * @return object, default is null
     */
    public static <V> V getAsObject(String json, String key, Class<V> type) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            JsonNode jsonNode = getAsJsonObject(json, key);
            if (null == jsonNode) {
                return null;
            }
            JavaType javaType = mapper.getTypeFactory().constructType(type);
            return from(getAsString(jsonNode), javaType);
        } catch (Exception e) {
            throw new JacksonException("jackson get list error, json: {}, key: {}, type: {}", json, key, type, e);
        }
    }


    /**
     * Get a field from the json string
     *
     * @return list, default is null
     */
    public static <V> List<V> getAsList(String json, String key, Class<V> type) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            JsonNode jsonNode = getAsJsonObject(json, key);
            if (null == jsonNode) {
                return null;
            }
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, type);
            return from(getAsString(jsonNode), collectionType);
        } catch (Exception e) {
            throw new JacksonException("jackson get list error, json: {}, key: {}, type: {}", json, key, type, e);
        }
    }

    /**
     * Get a field from the json string
     *
     * @return JsonNode, default is null
     */
    public static JsonNode getAsJsonObject(String json, String key) {
        try {
            JsonNode node = mapper.readTree(json);
            if (null == node) {
                return null;
            }
            return node.get(key);
        } catch (IOException e) {
            throw new JacksonException("jackson get object from json error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * Add attributes to json
     *
     * @return json
     */
    public static <V> String add(String json, String key, V value) {
        try {
            JsonNode node = mapper.readTree(json);
            add(node, key, value);
            return node.toString();
        } catch (IOException e) {
            throw new JacksonException("jackson add error, json: {}, key: {}, value: {}", json, key, value, e);
        }
    }

    /**
     * Add attributes to json
     */
    private static <V> void add(JsonNode jsonNode, String key, V value) {
        if (value instanceof String) {
            ((ObjectNode) jsonNode).put(key, (String) value);
        } else if (value instanceof Short) {
            ((ObjectNode) jsonNode).put(key, (Short) value);
        } else if (value instanceof Integer) {
            ((ObjectNode) jsonNode).put(key, (Integer) value);
        } else if (value instanceof Long) {
            ((ObjectNode) jsonNode).put(key, (Long) value);
        } else if (value instanceof Float) {
            ((ObjectNode) jsonNode).put(key, (Float) value);
        } else if (value instanceof Double) {
            ((ObjectNode) jsonNode).put(key, (Double) value);
        } else if (value instanceof BigDecimal) {
            ((ObjectNode) jsonNode).put(key, (BigDecimal) value);
        } else if (value instanceof BigInteger) {
            ((ObjectNode) jsonNode).put(key, (BigInteger) value);
        } else if (value instanceof Boolean) {
            ((ObjectNode) jsonNode).put(key, (Boolean) value);
        } else if (value instanceof byte[]) {
            ((ObjectNode) jsonNode).put(key, (byte[]) value);
        } else {
            ((ObjectNode) jsonNode).put(key, to(value));
        }
    }

    /**
     * Remove an attribute in json
     *
     * @return json
     */
    public static String remove(String json, String key) {
        try {
            JsonNode node = mapper.readTree(json);
            ((ObjectNode) node).remove(key);
            return node.toString();
        } catch (IOException e) {
            throw new JacksonException("jackson remove error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * Modify the attributes in json
     */
    public static <V> String update(String json, String key, V value) {
        try {
            JsonNode node = mapper.readTree(json);
            ((ObjectNode) node).remove(key);
            add(node, key, value);
            return node.toString();
        } catch (IOException e) {
            throw new JacksonException("jackson update error, json: {}, key: {}, value: {}", json, key, value, e);
        }
    }

    /**
     * Format Json (beautification)
     *
     * @return json
     */
    public static String format(String json) {
        try {
            JsonNode node = mapper.readTree(json);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        } catch (IOException e) {
            throw new JacksonException("jackson format json error, json: {}", json, e);
        }
    }

    /**
     * Determine whether the string is json
     *
     * @return json
     */
    public static boolean isJson(String json) {
        try {
            mapper.readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static<T> String toJson(T data) {
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new JacksonException("jackson to json error, data: {}", data, e);
        }
    }
}

