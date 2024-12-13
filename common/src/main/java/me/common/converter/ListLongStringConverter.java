package me.common.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;


@Converter
public class ListLongStringConverter implements AttributeConverter<List<Long>, String> {


    @Override
    public String convertToDatabaseColumn(List<Long> attribute) {
        return attribute != null ? parseListLongToString(attribute) : null;
    }

    @Override
    public List<Long> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        } else {
            List<Long> rs = Collections.emptyList();
            try {
                if (!dbData.isEmpty()) {
                    rs = Arrays.stream(dbData.split(",")).map(Long::parseLong).collect(Collectors.toList());
                }
            } catch (Exception ignored) {
            }
            return rs;
        }
    }

    private static String parseListLongToString(List<Long> attribute) {
        return attribute.stream().map(Object::toString).collect(joining(","));
    }
}