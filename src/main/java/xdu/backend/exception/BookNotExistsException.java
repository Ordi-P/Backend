package xdu.backend.exception;

/**
 * @author 邓乐丰
 */
public class BookNotExistsException extends Exception {

    public BookNotExistsException(long bookID) {
        super("The book of ID:" + bookID + " doesn't exist. Please check again");
    }
}
