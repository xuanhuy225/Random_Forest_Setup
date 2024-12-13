package me.common.spring.repository;


import me.common.spring.admin.queries.PaginationPayload;

import java.io.Serializable;
import java.util.List;

public interface BaseCRUDUseCase<T extends Serializable, ID extends Serializable> {
    T getOne(ID id);

    List<T> findAll();

    List<T> findAllById(Iterable<ID> ids);

    T saveAndFlush(T object);

    List<T> saveAll(Iterable<T> objects);

    void flush();

    boolean deleteById(ID id);

    long countAll();

    List<T> findAll(int page, int size, String sortBy, String sortType);

    List<T> findAll(PaginationPayload paginationPayload);
}
