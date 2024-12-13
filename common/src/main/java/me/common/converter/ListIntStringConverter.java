package me.common.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;


@Converter
public class ListIntStringConverter implements AttributeConverter<List<Integer>, String> {


    @Override
    public String convertToDatabaseColumn(List<Integer> attribute) {
        return attribute != null ? parseListIntToString(attribute) : null;
    }

    @Override
    public List<Integer> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        } else {
            List<Integer> rs = Collections.emptyList();
            if (!dbData.isEmpty()) {
                try {
                    rs = Arrays.stream(dbData.split(",")).map(Integer::parseInt).collect(Collectors.toList());
                } catch (Exception ignored) {
                }
            }
            return rs;
        }
    }

    private static String parseListIntToString(List<Integer> attribute) {
        return attribute.stream().map(Object::toString).collect(joining(","));
    }
}