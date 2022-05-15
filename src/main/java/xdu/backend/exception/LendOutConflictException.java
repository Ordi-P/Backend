package xdu.backend.exception;

/**
 * @author 邓乐丰
 */
public class LendOutConflictException extends Exception {

    public LendOutConflictException(String errMsg) {
        super("LendOutConflictException: " + errMsg);
    }
}
