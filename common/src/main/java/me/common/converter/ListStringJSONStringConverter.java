package me.common.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import me.common.util.JacksonUtils;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.joining;


@Converter
public class ListStringJSONStringConverter implements AttributeConverter<List<String>, String> {


    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return attribute != null ? JacksonUtils.to(attribute) : null;
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        } else {
            List<String> rs = Collections.emptyList();
            try {
                if (!dbData.isEmpty()) {
                    rs = JacksonUtils.from(dbData, new TypeReference<List<String>>() {
                    });
                }
            } catch (Exception ignored) {
            }
            return rs;
        }
    }
}