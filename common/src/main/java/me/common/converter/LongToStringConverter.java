package me.common.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter
public class LongToStringConverter implements AttributeConverter<Long, String> {

    @Override
    public String convertToDatabaseColumn(Long attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public Long convertToEntityAttribute(String dbData) {
        return dbData == null ? null : Long.parseLong(dbData);
    }
}
