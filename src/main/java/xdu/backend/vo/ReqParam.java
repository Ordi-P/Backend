package xdu.backend.vo;

public class ReqParam {

    String book_id;

    String user_id;

    public ReqParam() {}

    public ReqParam(String user_id, String book_id) {
        this.book_id = book_id;
        this.user_id = user_id;
    }

    public void setBookID(String book_id) {
        this.book_id = book_id;
    }

    public void setUserID(String user_id) {
        this.user_id = user_id;
    }

    public String getUserID() {
        return user_id;
    }

    public String getBookID() {
        return book_id;
    }
}
