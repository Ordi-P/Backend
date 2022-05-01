package xdu.backend.Dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xdu.backend.pojo.Book;
import xdu.backend.vo.UserBorrowInfo;

import java.sql.Date;
import java.util.List;

@Repository
public interface BorrowDao {

    void insertBorrowRecord(@Param("bookID") long bookID, @Param("userID") String userID, @Param("borrowDate") Date borrowDate);

    int getUserBorrowNumber(String userID);

    List<Book> getUserBorrowList(String userID);

    Date queryBorrowDateByBookID(long bookID);

    String queryBorrowerByBookID(long bookID);

    void updateBorrowRecord(@Param("bookID") long bookID, @Param("date") Date date);

    List<UserBorrowInfo> queryUserBorrowInfoByID(String userID);

    int deleteBorrowRecordByBookId(long bookID);

}
