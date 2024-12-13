package me.common.spring.repository;


import java.io.Serializable;
import java.util.List;

public interface RepositoryESGateway<T extends Serializable, ID extends Serializable> {
    T getOne(ID id);

    List<T> findAll();

    // TODO: findAll with Sort
    List<T> findAllById(Iterable<ID> ids);

    T saveAndFlush(T object);

    List<T> saveAll(Iterable<T> objects);

    void flush();

    boolean deleteById(ID id);

    long countAll();

    List<T> findAll(int page, int size, String sortBy, String sortType);
}
