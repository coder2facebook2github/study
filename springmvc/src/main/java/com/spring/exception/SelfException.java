package com.spring.exception;

/**
 * Created by Administrator on 2016/7/21.
 */
public class SelfException extends RuntimeException {
    private String errorMessage;

    public SelfException(){
        super();
    }
    public SelfException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
