package me.common.spring.admin;

import java.io.Serializable;
import java.util.List;

public interface BaseAdminService<T extends Serializable, ID extends Serializable> {
    T save(T dto);

    T findById(ID id);

    void delete(ID id);

    T update(T dto, ID id);

    List<T> getAll();
}
