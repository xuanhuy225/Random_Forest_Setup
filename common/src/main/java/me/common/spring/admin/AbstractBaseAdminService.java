package me.common.spring.admin;

import me.common.spring.converter.RestConverter;
import me.common.spring.repository.BaseCRUDUseCase;

import java.io.Serializable;
import java.util.List;


public abstract class AbstractBaseAdminService<T extends Serializable,E extends Serializable, ID extends Serializable> implements BaseAdminService<T, ID> {
    protected abstract BaseCRUDUseCase<E, ID> getUseCase();
    protected abstract RestConverter<T, E> getConverter();
    public T save(T dto) {
        return getConverter().mapToRest(getUseCase().saveAndFlush(getConverter().mapToEntity(dto)));
    }

    public T findById(ID id) {
        return getConverter().mapToRest(getUseCase().getOne(id));
    }

    public void delete(ID id) {
        getUseCase().deleteById(id);
    }

    public T update(T dto, ID id) {
        return getConverter().mapToRest(getUseCase().saveAndFlush(getConverter().mapToEntity(dto)));
    }

    public List<T> getAll() {
        return getConverter().mapToRest(getUseCase().findAll());
    }
}
