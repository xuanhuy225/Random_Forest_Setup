package me.common.spring.admin;

import me.common.spring.exception.ResourceNotFoundException;
import me.common.spring.response.MultiResponse;
import me.common.spring.response.SingleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@Slf4j
public abstract class AbstractBaseAdminController<T extends Serializable, ID extends Serializable> implements BaseAdminController<T, ID> {

    protected abstract <A extends BaseAdminService<T, ID>> A getService();

    @Override
    public ResponseEntity<SingleResponse<T>> save(T dto) {
        T save = getService().save(dto);
        if (save == null) {
            return ResponseEntity.ok(SingleResponse.buildFailure(1, "Save failed"));
        }
        return ResponseEntity.ok(SingleResponse.of(save));
    }

    @Override
    public ResponseEntity<SingleResponse<T>> findById(ID id) {
        T dto = getService().findById(id);
        if (dto == null) {
            return ResponseEntity.ok(SingleResponse.buildFailure(1, "Not found"));
        }
        return ResponseEntity.ok(SingleResponse.of(dto));
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
    public ResponseEntity<MultiResponse<T>> pageQuery(T dto, Pageable pageable) {
        List<T> dtos = getService().getAll();
        if (dtos == null) {
            return ResponseEntity.ok(MultiResponse.buildFailure(1, "Not found"));
        }
        return ResponseEntity.ok(MultiResponse.of(dtos, (long) dtos.size()));
    }

    @Override
    public ResponseEntity<SingleResponse<T>> update(T dto, ID id) {
        T update = getService().update(dto, id);
        if (update == null) {
            return ResponseEntity.ok(SingleResponse.buildFailure(1, "Update failed"));
        }
        return ResponseEntity.ok(SingleResponse.of(update));
    }
}
