package me.common.spring.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MultiResponse<T> extends Response {
    private static final long serialVersionUID = 1L;
    private Collection<T> data;

    private Long total;
    public MultiResponse() {
    }

    public List<T> getData() {
        if (null == this.data) {
            return Collections.emptyList();
        } else {
            return (List)(this.data instanceof List ? (List)this.data : new ArrayList(this.data));
        }
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

    public boolean isEmpty() {
        return this.data == null || this.data.isEmpty();
    }

    public boolean isNotEmpty() {
        return !this.isEmpty();
    }

    public Long getTotal() {
        return total;
    }

    public MultiResponse<T> setTotal(Long total) {
        this.total = total;
        return this;
    }

    public static <T> MultiResponse<T> buildSuccess() {
        MultiResponse<T> response = new MultiResponse<>();
        response.setCode(0);
        response.setMsg("Success");
        response.setSuccess(true);
        return response;
    }

    public static <T> MultiResponse<T> buildFailure(Integer errCode, String errMessage) {
        MultiResponse<T> response = new MultiResponse<>();
        response.setSuccess(false);
        response.setCode(errCode);
        response.setMsg(errMessage);
        return response;
    }

    public static <T> MultiResponse<T> of(Collection<T> data) {
        MultiResponse<T> response = new MultiResponse();
        response.setSuccess(true);
        response.setCode(0);
        response.setMsg("Success");
        response.setData(data);
        return response;
    }

    public static <T> MultiResponse<T> of(Collection<T> data, Long total) {
        MultiResponse<T> response = MultiResponse.of(data);
        response.setTotal(total);
        return response;
    }
}
