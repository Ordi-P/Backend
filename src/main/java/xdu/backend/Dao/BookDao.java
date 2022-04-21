package xdu.backend.Dao;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xdu.backend.pojo.Book;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface BookDao {

    List<Book> getAllBooks();

    boolean queryBookAvailability(long bookID);

    void updateBookAvailability(@Param("bookID") long bookID, @Param("availability") boolean availability);

    List<Book> queryBookByISBNCode(String bookInfo);

    List<Book> queryBookByName(String bookInfo);

    List<Book> queryBookByAuthor(String bookInfo);

    List<Book> queryBookByISBNNumber(String bookInfo);

    Timestamp queryReservedTime(long bookID);

    void updateBookReservation(@Param("bookID") long bookID, @Param("userID") String userID, @Param("reserveTime") Timestamp reserveTime);

    String queryReserveUserID(long bookID);

    List<Book> queryBookByISBN(String isbnCode);

    Book queryBookByID(long bookID);

    void addBook(Book book);

    int deleteBook(Long book_id);
}
