package xdu.backend.Dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xdu.backend.pojo.Book;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface BookDao {

    List<Book> getAllBooks();

    boolean queryBookAvailability(String bookID);

    void updateBookAvailability(@Param("bookID") String bookID, @Param("availability") boolean availability);

    List<Book> queryBookByISBNCode(String bookInfo);

    List<Book> queryBookByName(String bookInfo);

    List<Book> queryBookByAuthor(String bookInfo);

    List<Book> queryBookByISBNNumber(String bookInfo);

    Timestamp queryReservedTime(String bookID);

    void updateBookReservation(@Param("bookID") String bookID, @Param("userID") String userID, @Param("reserveTime") Timestamp reserveTime);

    String queryReserveUserID(String bookID);

    List<Book> queryBookByISBN(String isbnCode);
}
