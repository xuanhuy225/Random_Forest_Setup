package me.common.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.sql.Timestamp;


@Converter
public class LongToTimeStamp implements AttributeConverter<Long, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(Long attribute) {
        return attribute == null ? null : new Timestamp(attribute);
    }

    @Override
    public Long convertToEntityAttribute(Timestamp dbData) {
        return dbData == null ? null : dbData.getTime();
    }
}
