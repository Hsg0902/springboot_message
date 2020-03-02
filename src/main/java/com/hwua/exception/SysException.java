package com.hwua.exception;

public class SysException extends RuntimeException {
    private String message;

    public SysException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
