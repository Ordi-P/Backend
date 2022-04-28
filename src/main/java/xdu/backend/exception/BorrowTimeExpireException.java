package xdu.backend.exception;

public class BorrowTimeExpireException extends Exception {
    public BorrowTimeExpireException(String bookName) {
        super("Cannot renew your borrow: The borrow of " + bookName + " has expired.");
    }
}
