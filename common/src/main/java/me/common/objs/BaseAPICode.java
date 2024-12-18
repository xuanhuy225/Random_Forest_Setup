package me.common.objs;


import me.common.util.JacksonUtils;

public enum BaseAPICode implements ErrorCode {
    SUCCESS(0, "SUCCESS"),
    UNKNOWN_ERROR(1, "UNKNOWN"),
    EXCEED_LOGIN_ATTEMPT(2, "EXCEED_LOGIN_ATTEMPT"),
    BAD_REQUEST(400, "Bad Request"),
    FORBIDDEN(403, "FORBIDDEN"),
    NOT_FOUND(404, "NOT_FOUND"),
    UNSUPPORTED(415, "UNSUPPORTED"),
    SERVER_ERROR(500, "SERVER_ERROR"),
    BAD_GATEWAY(502, "BAD_GATEWAY"),
    TIMEOUT(504, "TIMEOUT");

    private final int code;
    private final String message;

    BaseAPICode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }


    @Override
    public String toString() {
        return JacksonUtils.to(this);
    }
}
