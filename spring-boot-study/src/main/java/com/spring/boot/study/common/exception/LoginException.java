package com.spring.boot.study.common.exception;

public class LoginException extends BootStudyException {
    private String errorMessage;
    private int code;

    public LoginException() {
        super();
    }

    public LoginException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public LoginException(String errorMessage, int code) {
        super(errorMessage, code);
        this.errorMessage = errorMessage;
        this.code = code;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }
}
