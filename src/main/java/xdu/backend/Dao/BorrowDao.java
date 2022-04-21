package xdu.backend.Dao;

import org.springframework.stereotype.Repository;
import xdu.backend.pojo.Book;

import java.sql.Date;
import java.util.List;

@Repository
public interface BorrowDao {

    void insertBorrowRecord(String bookID, String userID, Date borrowDate);

    int getUserBorrowNumber(String userID);

    List<Book> getUserBorrowList(String userID);
}
