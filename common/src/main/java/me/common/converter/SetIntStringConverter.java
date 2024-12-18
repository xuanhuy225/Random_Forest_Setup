package me.common.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;


@Converter
public class SetIntStringConverter implements AttributeConverter<Set<Integer>, String> {


    @Override
    public String convertToDatabaseColumn(Set<Integer> attribute) {
        return attribute != null ? parseSetIntToString(attribute) : null;
    }

    @Override
    public Set<Integer> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        } else {
            Set<Integer> rs = Collections.emptySet();
            try {
                if (!dbData.isEmpty()) {
                    rs = Arrays.stream(dbData.split(",")).map(Integer::parseInt).collect(Collectors.toSet());
                }
            } catch (Exception ignored) {
            }
            return rs;
        }
    }

    private static String parseSetIntToString(Set<Integer> attribute) {
        return attribute.stream().map(Object::toString).collect(joining(","));
    }
}