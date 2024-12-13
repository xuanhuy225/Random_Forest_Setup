package me.common.spring.admin;

import me.common.spring.response.MultiResponse;
import me.common.spring.response.SingleResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RequestMapping("/api/default")
public interface BaseAdminController<T extends Serializable, ID extends Serializable> {
    @PostMapping
    ResponseEntity<SingleResponse<T>> save(@RequestBody @Validated T dto);

    @GetMapping("/{id}")
    ResponseEntity<SingleResponse<T>> findById(@PathVariable("id") ID id);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") ID id);

    @GetMapping
    ResponseEntity<MultiResponse<T>> pageQuery(T dto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable);

    @PutMapping("/{id}")
    ResponseEntity<SingleResponse<T>> update(@RequestBody @Validated T dto, @PathVariable("id") ID id);
}
