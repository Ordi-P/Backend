package xdu.backend.Dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xdu.backend.pojo.Book;
import xdu.backend.vo.AbandonedBook;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 邓乐丰
 */
@Repository
public interface BookDao {

    List<Book> getAllBooks();

    Boolean queryBookAvailability(Long bookID);

    void updateBookAvailability(@Param("bookID") Long bookID, @Param("availability") Boolean availability);

    List<Book> queryBookByISBNCode(String bookInfo);

    List<Book> queryBookByName(String bookInfo);

    List<Book> queryBookByAuthor(String bookInfo);

    List<Book> queryBookByISBNNumber(String bookInfo);

    Timestamp queryReservedTime(Long bookID);

    void updateBookReservation(@Param("bookID") Long bookID, @Param("userID") String userID, @Param("reserveTime") Timestamp reserveTime);

    String queryReserveUserID(Long bookID);

    List<Book> queryBookByISBN(String isbnNumber);

    Book queryBookByID(Long bookID);

    void addBook(Book book);

    Integer deleteBook(Long book_id);

    String queryISBNNumberByID(Long bookID);

    void undoBookReservation(@Param("userID") String userID, @Param("isbnNumber") String isbnNumber, @Param("reserveTime") Timestamp reserveTime);

    String queryBookNameByID(Long bookID);

    String queryLocationByID(Long bookID);

    Integer queryCollectionNumber();

    Integer queryLentOutNumber();

    Integer queryDamagedNumber();

    Integer queryLostNumber();

    List<AbandonedBook> queryLostBooks();

    List<AbandonedBook> queryDamagedBooks();

    void updateBookAbandonedByID(@Param("bookID") Long bookID, @Param("reason") String reason);

    void updateLocationAndCategoryByISBNNumber(@Param("location") String location, @Param("category") String category, @Param("isbnNumber") String isbnNumber);
}
