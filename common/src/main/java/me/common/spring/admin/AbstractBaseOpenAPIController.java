package me.common.spring.admin;

import lombok.extern.slf4j.Slf4j;
import me.common.spring.exception.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@Slf4j
public abstract class AbstractBaseOpenAPIController<T extends Serializable, ID extends Serializable> implements BaseOpenAPIController<T, ID> {

    protected abstract <A extends BaseAdminService<T, ID>> A getService();

    @Override
    public ResponseEntity<T> save(T dto) {
        T save = getService().save(dto);
        return ResponseEntity.ok(save);
    }

    @Override
    public ResponseEntity<T> findById(ID id) {
        T dto = getService().findById(id);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Void> delete(ID id) {
        Optional.ofNullable(getService().findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new ResourceNotFoundException();
        });
        getService().delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<T>> pageQuery(T dto, Pageable pageable) {
        List<T> dtos = getService().getAll();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<T> update(T dto, ID id) {
        T update = getService().update(dto, id);
        return ResponseEntity.ok(update);
    }
}
