package me.common.spring.repository;

import me.common.spring.admin.queries.PaginationPayload;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


public abstract class AbstractRepositoryGateway<E extends Serializable, T extends Serializable, ID extends Serializable> implements RepositoryGateway<E, ID> {

    protected abstract <A extends JpaRepository<T, ID>> A getRepository();

    protected <A extends JpaSpecificationExecutor<T>> A getRepositorySpecification() {
        if (getRepository() instanceof JpaSpecificationExecutor) {
            return (A) getRepository();
        }
        throw new NotImplementedException("Not implemented yet");
    }

    protected abstract <A extends RepositoryConverter<T, E>> A getRepositoryConverter();

    @Override
    @Transactional
    public E getOne(ID id) {
        try {
            T referenceById = getRepository().getReferenceById(id);
            return getRepositoryConverter().mapToEntity(referenceById);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public List<E> findAll() {
        return getRepository().findAll().stream().map(getRepositoryConverter()::mapToEntity).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<E> findAllById(Iterable<ID> ids) {
        return getRepositoryConverter().mapToEntity(getRepository().findAllById(ids));
    }

    @Override
    @Transactional
    public E saveAndFlush(E object) {
        return getRepositoryConverter().mapToEntity(getRepository().saveAndFlush(getRepositoryConverter().mapToTable(object)));
    }

    @Override
    @Transactional
    public List<E> saveAll(Iterable<E> objects) {
        return getRepositoryConverter().mapToEntity(getRepository().saveAll(getRepositoryConverter().mapToTable(objects)));
    }

    @Override
    @Transactional
    public void flush() {
        getRepository().flush();
    }

    @Override
    @Transactional
    public boolean deleteById(ID id) {
        getRepository().deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public long countAll() {
        return getRepository().count();
    }

    @Override
    @Transactional
    public List<E> findAll(int page, int size, String sortBy, String sortType) {

        PageRequest pageRequest;
        switch (sortType) {
            case "asc":
                pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));
                break;
            case "desc":
                pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
                break;
            default:
                pageRequest = PageRequest.of(page, size);
        }

        return this.getRepositoryConverter().mapToEntity(this.getRepository().findAll(pageRequest).getContent());
    }

    @Override
    @Transactional
    public List<E> findAll(PaginationPayload paginationPayload) {

        PageRequest pageRequest;

        int pageIndex = paginationPayload.getPageIndex();
        int pageSize = paginationPayload.getPageSize();
        String sortBy = paginationPayload.getSortBy();
        PaginationPayload.SortType sortType = paginationPayload.getSortType();
        switch (sortType) {
            case ASC:
                pageRequest = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
                break;
            case DESC:
                pageRequest = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
                break;
            default:
                pageRequest = PageRequest.of(pageIndex, pageSize);
        }

        return this.getRepositoryConverter().mapToEntity(this.getRepository().findAll(pageRequest).getContent());
    }

}
