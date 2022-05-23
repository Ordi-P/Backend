package xdu.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author 邓乐丰
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Borrow implements Serializable {

    private Long transactionID;

    private Long bookID;

    private String userID;

    private Date borrowDate;

    private Date returnDate;

    private Boolean returned;

    private Integer fine;

    public Borrow(Long bookID, String userID) {
        this.bookID = bookID;
        this.userID = userID;
        borrowDate = new Date(new java.util.Date().getTime());
        returnDate = new Date(new java.util.Date().getTime() + 10L * 24 * 60 * 60 * 1000);
        returned = false;
        fine = 0;
    }

}