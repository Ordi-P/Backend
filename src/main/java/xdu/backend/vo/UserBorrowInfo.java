package xdu.backend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * 返回给前端的用户借书信息，需要多表查询
 * @author 邓乐丰
 */
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
