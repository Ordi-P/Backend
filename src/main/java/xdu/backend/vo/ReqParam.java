package xdu.backend.vo;

public class ReqParam {

    String transaction_id;

    String book_id;

    String user_id;

    public ReqParam() {}

    public ReqParam(String transaction_id, String user_id, String book_id) {
        this.transaction_id = transaction_id;
        this.book_id = book_id;
        this.user_id = user_id;
    }

    public void setBookID(String book_id) {
        this.book_id = book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public void setUserID(String user_id) {
        this.user_id = user_id;
    }

    public void setTransactionID(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getUserID() {
        return user_id;
    }

    public String getBookID() {
        return book_id;
    }

    public String getTransactionID() {
        return transaction_id;
    }
}
