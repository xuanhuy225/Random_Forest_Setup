package me.common.spring.repository;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public interface RepositoryConverter<T extends Serializable, P extends Serializable> {

    default T mapToTable(final P entity) {
        if (entity == null) {
            return null;
        }
        return mapToTableNotNull(entity);
    }

    default P mapToEntity(final T persistentObject) {
        if (persistentObject == null) {
            return null;
        }
        return mapToEntityNotNull(persistentObject);
    }

    T mapToTableNotNull(final P persistenceObject);

    P mapToEntityNotNull(final T tableObject);

    default List<T> mapToTable(final Iterable<P> persistenceObjects) {
        if (persistenceObjects != null) {
            return StreamSupport.stream(persistenceObjects.spliterator(), false).map(this::mapToTable).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    default List<P> mapToEntity(final Iterable<T> tableObjects) {
        if (tableObjects != null) {
            return StreamSupport.stream(tableObjects.spliterator(), false).map(this::mapToEntity).collect(Collectors.toList());
        } else {
            return null;
        }
    }

}