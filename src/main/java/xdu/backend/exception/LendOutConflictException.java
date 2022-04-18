package xdu.backend.exception;

public class LendOutConflictException extends Exception {

    public LendOutConflictException(String errMsg) {
        super("LendOutConflictException: " + errMsg);
    }
}
