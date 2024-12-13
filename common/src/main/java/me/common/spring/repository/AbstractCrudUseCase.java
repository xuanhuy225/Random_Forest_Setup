package me.common.spring.repository;

import me.common.spring.admin.queries.PaginationPayload;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractCrudUseCase<T extends Serializable, ID extends Serializable> implements BaseCRUDUseCase<T, ID> {

    /**
     * This method abstract get repository gateway.
     *
     * @param <A> Repository gateway extend
     * @return instance of repository gateway
     */
    protected abstract <A extends RepositoryGateway<T, ID>> A getGateway();


    @Override
    public T getOne(ID id) {
        return getGateway().getOne(id);
    }

    @Override
    public List<T> findAll() {
        return getGateway().findAll();
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        return getGateway().findAllById(ids);
    }

    @Override
    public T saveAndFlush(T object) {
        return getGateway().saveAndFlush(object);
    }

    @Override
    public List<T> saveAll(Iterable<T> objects) {
        return getGateway().saveAll(objects);
    }

    @Override
    public void flush() {
        getGateway().flush();
    }

    @Override
    public boolean deleteById(ID id) {
        return getGateway().deleteById(id);
    }

    @Override
    public long countAll() {
        return getGateway().countAll();
    }

    @Override
    public List<T> findAll(int page, int size, String sortBy, String sortType) {
        return getGateway().findAll(page, size, sortBy, sortType);
    }

    @Override
    public List<T> findAll(PaginationPayload paginationPayload) {
        return getGateway().findAll(paginationPayload);
    }

}
