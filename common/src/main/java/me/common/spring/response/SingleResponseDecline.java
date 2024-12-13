package me.common.spring.response;

public class SingleResponseDecline<T> extends Response {
    private static final long serialVersionUID = 1L;

    private Integer declineErrorCode;
    private T data;

    public SingleResponseDecline() {
    }

    public Integer getDeclineErrorCode() {
        return this.declineErrorCode;
    }

    public void setDeclineErrorCode(Integer declineErrorCode) {
        this.declineErrorCode = declineErrorCode;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static SingleResponseDecline buildSuccess() {
        SingleResponseDecline response = new SingleResponseDecline();
        response.setCode(0);
        response.setMsg("Success");
        response.setSuccess(true);
        response.setDeclineErrorCode(null);
        return response;
    }

    public static SingleResponseDecline buildFailure(Integer errCode, String errMessage, Integer declineErrorCode) {
        SingleResponseDecline response = new SingleResponseDecline();
        response.setSuccess(false);
        response.setCode(errCode);
        response.setMsg(errMessage);
        response.setDeclineErrorCode(declineErrorCode);
        return response;
    }

    public static <T> SingleResponseDecline<T> of(T data) {
        SingleResponseDecline<T> response = new SingleResponseDecline();
        response.setSuccess(true);
        response.setCode(0);
        response.setMsg("Success");
        response.setData(data);
        response.setDeclineErrorCode(null);
        return response;
    }

}
