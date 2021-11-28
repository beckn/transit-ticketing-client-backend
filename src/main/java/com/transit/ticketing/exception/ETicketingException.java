package com.transit.ticketing.exception;

public class ETicketingException extends Exception{
    public ETicketingException() {
        super();
    }

    public ETicketingException(String message) {
        super(message);
    }

    public ETicketingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ETicketingException(Throwable cause) {
        super(cause);
    }

    protected ETicketingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
