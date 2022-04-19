package xdu.backend.exception;

public class UserOperationException extends Exception {
    public UserOperationException(String errMsg) {
        super("UserOperationException: " + errMsg);
    }
}
