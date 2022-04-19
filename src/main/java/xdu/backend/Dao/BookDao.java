package xdu.backend.Dao;

import org.springframework.stereotype.Repository;
import xdu.backend.pojo.Book;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface BookDao {

    List<Book> getAllBooks();

    void setBookReservation(String bookID, String userID, Timestamp timestamp);

    boolean getBookAvailability(String bookID);

    Book queryBookByISBNCode(String bookInfo);

    List<Book> queryBookByName(String bookInfo);

    List<Book> queryBookByAuthor(String bookInfo);

    List<Book> queryBookByISBNNumber(String bookInfo);

    Timestamp getReservedTime(String bookID);

    void updateBookAvailability(String bookID, boolean availability);

    String getReserveUserID(String bookID);
}
