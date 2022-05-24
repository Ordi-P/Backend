package xdu.backend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBorrowInfo {

    Long transactionID;

    String userID;

    Long bookID;

    String bookName;

    String bookAuthor;

    Date borrowDate;

    Date returnDate;

    Boolean returned;

    Integer fine;

}
