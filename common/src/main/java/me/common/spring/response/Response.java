package me.common.spring.response;

import me.common.spring.objs.DTO;

public class Response extends DTO {
    private static final long serialVersionUID = 1L;
    private boolean success;
    private Integer code;
    private String msg;

    public Response() {
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String toString() {
        return "Response [success=" + this.success + ", errCode=" + this.code + ", errMessage=" + this.msg + "]";
    }
}
