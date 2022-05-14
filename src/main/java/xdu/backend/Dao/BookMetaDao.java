package xdu.backend.Dao;

import org.springframework.stereotype.Repository;
import xdu.backend.pojo.BookMeta;

import java.util.List;

@Repository
public interface BookMetaDao {

    List<BookMeta> queryBookMetaByISBNCode(String isbnCode);

    List<BookMeta> queryBookMetaByAuthor(String author);

    List<BookMeta> queryBookMetaByName(String bookName);

    List<BookMeta> queryBookMetaByISBNNumber(String isbnNumber);

    List<BookMeta> getAllBookMetas();

    List<BookMeta> queryBookMetaByISBN(String isbnNumber);

    void updateBookMeta(String isbn_code, Integer deltaNum);

    void insertBookMeta(BookMeta bookMeta);

    void deleteBookMeta(String isbn_code);
}
