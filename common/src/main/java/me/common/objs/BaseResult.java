package me.common.objs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


public class BaseResult<T> implements Result {
    private String msg;
    private int code;
    private int subCode;
    private String subMsg;
    private T data;
    private Object debug;
    @JsonIgnoreProperties
    private Object extendResponse;

    public BaseResult() {
        this.setBase(BaseAPICode.UNKNOWN_ERROR);
    }

    public BaseResult(T data, BaseAPICode code) {
        this.setBase(data, code);
    }

    @Override
    public boolean isSuccess() {
        return code == 0;
    }

    public String getMsg() {
        return msg;
    }

    public BaseResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public int getCode() {
        return code;
    }

    public BaseResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public T getData() {
        return data;
    }

    public BaseResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public BaseResult<T> setBase(BaseAPICode code) {
        this.setCode(code.getCode());
        this.setMsg(code.getMessage());
        return this;
    }

    public BaseResult<T> setBase(T data, BaseAPICode code) {
        this.setData(data);
        this.setBase(code);
        return this;
    }

    public BaseResult<T> setBase(T data, int code) {
        this.setData(data);
        this.setCode(code);
        return this;
    }

    public int getSubCode() {
        return subCode;
    }

    public BaseResult<T> setSubCode(int subCode) {
        this.subCode = subCode;
        return this;
    }

    public Object getDebug() {
        return debug;
    }

    public BaseResult<T> setDebug(Object debug) {
        this.debug = debug;
        return this;
    }

    public String getSubMsg() {
        return subMsg;
    }

    public BaseResult<T> setSubMsg(String subMsg) {
        this.subMsg = subMsg;
        return this;
    }

    public Object getExtendResponse() {
        return extendResponse;
    }

    public BaseResult<T> setExtendResponse(Object extendResponse) {
        this.extendResponse = extendResponse;
        return this;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", messageCode=" + subCode +
                ", data=" + data +
                '}';
    }
}
