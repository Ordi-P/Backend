package xdu.backend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBorrowInfo {

    String userID;

    long bookID;

    String bookName;

    String bookAuthor;

    Date borrowDate;

}
