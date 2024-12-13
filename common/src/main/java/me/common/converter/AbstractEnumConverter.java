package me.common.converter;

import me.common.spring.objs.EnumEntity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public abstract class AbstractEnumConverter<T extends Enum<T> & EnumEntity<E>, E> implements AttributeConverter<T, E> {
    private final Class<T> clazz;

    public AbstractEnumConverter() {
        this.clazz = getEnumClazz();
    }

    protected abstract Class<T> getEnumClazz();

    @Override
    public E convertToDatabaseColumn(T attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public T convertToEntityAttribute(E dbData) {
        T[] enums = clazz.getEnumConstants();

        for (T e : enums) {
            if (e.getValue().equals(dbData)) {
                return e;
            }
        }

        return null;
    }
}