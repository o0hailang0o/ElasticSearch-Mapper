package com.demo.elasticsearch.exceptions;

/**
 * @author liujian on 2019/2/1.
 */
public class FunNullException extends RuntimeException{

    public FunNullException() {
        super();
    }

    public FunNullException(String message) {
        super(message);
    }

    public FunNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public FunNullException(Throwable cause) {
        super(cause);
    }

    protected FunNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
