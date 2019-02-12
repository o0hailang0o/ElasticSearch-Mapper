package com.demo.elasticsearch.exceptions;

/**
 * @author liujian on 2018/12/29.
 */
public class NotFoundIdException extends RuntimeException{

    public NotFoundIdException() {
    }

    public NotFoundIdException(String message) {
        super(message);
    }

    public NotFoundIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundIdException(Throwable cause) {
        super(cause);
    }

    public NotFoundIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
