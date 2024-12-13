package me.common.spring.objs;

public interface EnumEntity<E> {
    E getValue();

    default String getDescription() {
        return null;
    }
}
