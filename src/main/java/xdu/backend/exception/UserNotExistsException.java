package xdu.backend.exception;

public class UserNotExistsException extends Exception {
    public UserNotExistsException(String userID) {
        super("The user of ID:" + userID + " doesn't exists, please register first.");
    }
}
