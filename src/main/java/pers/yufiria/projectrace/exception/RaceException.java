package pers.yufiria.projectrace.exception;

public class RaceException extends RuntimeException {

    public RaceException() {
    }

    public RaceException(String message) {
        super(message);
    }

    public RaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RaceException(Throwable cause) {
        super(cause);
    }

    public RaceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
