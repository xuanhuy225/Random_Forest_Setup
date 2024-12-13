package me.common.spring.response;

public class SingleResponse<T> extends Response {
    private static final long serialVersionUID = 1L;
    private T data;

    public SingleResponse() {
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> SingleResponse<T> buildSuccess() {
        SingleResponse<T> response = new SingleResponse<>();
        response.setCode(0);
        response.setMsg("Success");
        response.setSuccess(true);
        return response;
    }

    public static <T> SingleResponse<T> buildFailure(Integer errCode, String errMessage) {
        SingleResponse<T> response = new SingleResponse<>();
        response.setSuccess(false);
        response.setCode(errCode);
        response.setMsg(errMessage);
        return response;
    }

    public static <T> SingleResponse<T> of(T data) {
        SingleResponse<T> response = new SingleResponse<>();
        response.setSuccess(true);
        response.setCode(0);
        response.setMsg("Success");
        response.setData(data);
        return response;
    }
}

