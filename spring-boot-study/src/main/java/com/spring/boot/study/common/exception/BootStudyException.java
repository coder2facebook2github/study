package com.spring.boot.study.common.exception;

public class BootStudyException extends RuntimeException {
    private String errorMessage;
    private int code;

    public BootStudyException(){
        super();
    }

    public BootStudyException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public BootStudyException(String errorMessage, int code) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
