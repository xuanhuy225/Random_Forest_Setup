package me.common.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;


@Converter
public class ListStringStringConverter implements AttributeConverter<List<String>, String> {


    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return attribute != null ? parseListStringToString(attribute) : null;
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        } else {
            List<String> rs = Collections.emptyList();
            try {
                if (!dbData.isEmpty()) {
                    rs = Arrays.stream(dbData.split(",")).collect(Collectors.toList());
                }
            } catch (Exception ignored) {
            }
            return rs;
        }
    }

    private static String parseListStringToString(List<String> attribute) {
        return attribute.stream().map(Object::toString).collect(joining(","));
    }
}