package xdu.backend.exception;

public class ReserveConflictException extends Exception {

    public ReserveConflictException(String errMsg) {
        super("ReserveConflictException: " + errMsg);
    }
}
