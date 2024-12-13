package me.common.util.jackson.exception;

import me.common.exception.FormativeException;

public class JacksonException extends FormativeException {
    public JacksonException() {
        super();
    }

    public JacksonException(String message) {
        super(message);
    }

    public JacksonException(Throwable cause) {
        super(cause);
    }

    public JacksonException(String format, Object... arguments) {
        super(format, arguments);
    }
}