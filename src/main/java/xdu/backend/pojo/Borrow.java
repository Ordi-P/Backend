package xdu.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Borrow implements Serializable {

    private Long bookID;

    private String userID;

    private Date borrowDate;

    public Borrow(Long bookID, String userID) {
        this.bookID = bookID;
        this.userID = userID;
        borrowDate = new Date(new java.util.Date().getTime());
    }

}