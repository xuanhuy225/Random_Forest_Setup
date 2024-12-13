package me.common.spring.jpa.queries;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import jakarta.validation.constraints.NotNull;

public class OffsetBasedPageRequest implements Pageable {
    private final Integer limit;
    private final Integer offset;
    private final Sort sort;

    public OffsetBasedPageRequest(Integer offset, Integer limit, Sort sort) {
        if (offset == null || limit == null) {
            this.offset = 0;
            this.limit = 1000;
        } else {
            if (offset < 0) this.offset = 0;
            else this.offset = offset;
            if (limit < 1) this.limit = 1000;
            else this.limit = limit;
        }
        if (sort == null) this.sort = Sort.unsorted();
        else this.sort = sort;
    }

    public OffsetBasedPageRequest(Integer offset, Integer limit) {
        this(offset, limit, Sort.unsorted());
    }

    @Override
    public int getPageNumber() {
        return offset/limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @NotNull
    @Override
    public Sort getSort() {
        return sort;
    }

    @NotNull
    @Override
    public Pageable next() {
        return new OffsetBasedPageRequest((int) (getOffset() + getPageSize()), getPageSize(), getSort());
    }

    @NotNull
    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    private Pageable previous() {
        return hasPrevious() ? new OffsetBasedPageRequest((int) (getOffset() - getPageSize()), getPageSize(), getSort()) : this;
    }

    @NotNull
    @Override
    public Pageable first() {
        return new OffsetBasedPageRequest(0, getPageSize(), getSort());
    }

    @NotNull
    @Override
    public Pageable withPage(int pageNumber) {
        return new OffsetBasedPageRequest(pageNumber * getPageSize(), getPageSize(), getSort());
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }
}
