package me.common.spring.jpa.queries;


import org.springframework.data.domain.Sort;

public class JpaPagingHelper {

    public static OffsetBasedPageRequest generatePageableParam(Integer offset, Integer limit, String sortBy, String sortWith) {
        Sort sort = null;
        if (sortBy != null && !sortBy.isEmpty()) {
            sort = Sort.by(sortBy).ascending();
            if (sortWith != null && sortWith.equalsIgnoreCase("desc")) sort.descending();
        }
        OffsetBasedPageRequest pageable = new OffsetBasedPageRequest(offset, limit, sort);
        return pageable;
    }
}
