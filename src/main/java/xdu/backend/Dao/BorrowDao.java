package xdu.backend.Dao;

import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface BorrowDao {

    void insertBorrowRecord(String bookID, String userID, Date borrowDate);

    int getUserBorrowNumber(String userID);
}
