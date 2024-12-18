package me.common.objs;


import me.common.util.JacksonUtils;


public enum BaseErrorCode implements ErrorCode {
    SUCCESS(0, "SUCCESS"),
    UNKNOWN_ERROR(1, "UNKNOWN");

    private final int code;
    private final String message;

    BaseErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static boolean isSuccess(Integer errorCode) {
        return errorCode != null && errorCode == 0;
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
