package xdu.backend.Dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xdu.backend.pojo.Book;
import xdu.backend.vo.UserBorrowInfo;

import java.sql.Date;
import java.util.List;

/**
 * @author 邓乐丰
 */
@Repository
public interface BorrowDao {

    void insertBorrowRecord(@Param("bookID") Long bookID, @Param("userID") String userID, @Param("borrowDate") Date borrowDate);

    int getUserBorrowNumber(String userID);

    List<Book> getUserBorrowList(String userID);

    Date queryBorrowDateByBookID(Long bookID);

    String queryBorrowerByBookID(Long bookID);

    void updateBorrowRecord(@Param("bookID") Long bookID, @Param("date") Date date);

    List<UserBorrowInfo> queryUserBorrowInfoByID(String userID);

    int deleteBorrowRecordByBookId(Long bookID);

    String queryUserEmailByTxID(Long transactionID);

    String queryBookNameByTxID(Long transactionID);

    Integer queryUnpaidFineByUserID(String userID);
}
