package xdu.backend.exception;

/**
 * @author 邓乐丰
 */
public class ReserveConflictException extends Exception {

    public ReserveConflictException(String errMsg) {
        super("ReserveConflictException: " + errMsg);
    }
}
