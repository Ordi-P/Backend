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

    void insertBorrowRecord(@Param("bookID") Long bookID, @Param("userID") String userID,
                            @Param("borrowDate") Date borrowDate, @Param("returnDate") Date returnDate,
                            @Param("returned") Boolean returned, @Param("fine") Integer fine);

    Integer getUserCurrentBorrowNumber(String userID);

    List<Book> getUserCurrentBorrowList(String userID);

    Date queryBorrowDateByBookID(Long bookID);

    String queryBorrowerByBookID(Long bookID);

    void updateBorrowRecord(@Param("bookID") Long bookID, @Param("date") Date date);

    List<UserBorrowInfo> queryUserCurrentBorrowInfoByUserID(String userID);

    List<UserBorrowInfo> queryUserHistoryBorrowInfoByUserID(String userID);

    Integer deleteBorrowRecordByBookId(Long bookID);

    String queryUserEmailByTxID(Long transactionID);

    String queryBookNameByTxID(Long transactionID);

    Integer queryUnpaidFineByUserID(String userID);

    Date queryReturnDateByTransactionID(Long transactionID);

    void updateReturnDateByTransactionTD(@Param("transactionID") Long transactionID, @Param("date") Date date);

    String queryBorrowerByTransactionID(Long transactionID);
}
