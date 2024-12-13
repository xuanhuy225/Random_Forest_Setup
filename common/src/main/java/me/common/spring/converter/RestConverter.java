package me.common.spring.converter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public interface RestConverter<R extends Serializable, E extends Serializable> {

    default E mapToEntity(final R rest) {
        throw new UnsupportedOperationException();
    }

    default R mapToRest(final E entity) {
        throw new UnsupportedOperationException();
    }

    default List<E> mapToEntity(final Iterable<R> rests) {
        if (rests != null) {
            return StreamSupport.stream(rests.spliterator(), false).map(this::mapToEntity).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    default List<R> mapToRest(final Iterable<E> entities) {
        if (entities != null) {
            return StreamSupport.stream(entities.spliterator(), false).map(this::mapToRest).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
