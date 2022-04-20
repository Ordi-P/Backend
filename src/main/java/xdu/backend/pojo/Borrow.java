package xdu.backend.pojo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class Borrow implements Serializable {

    private String bookID;

    private String userID;

    private Date borrowDate;

    public Borrow(String bookID, String userID) {
        this.bookID = bookID;
        this.userID = userID;
        borrowDate = new Date(new java.util.Date().getTime());
    }
}