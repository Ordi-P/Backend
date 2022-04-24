package xdu.backend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xdu.backend.Dao.BookDao;
import xdu.backend.Dao.BookMetaDao;
import xdu.backend.pojo.Book;
import xdu.backend.pojo.BookMeta;

import java.util.List;

@SpringBootTest
public class BorrowServiceImplTest {

    @Autowired
    BookDao bookDao;
    @Autowired
    BookMetaDao bookMetaDao;

    @Test
    public void testRegex() {
        String ISBNNumberRegex = "^\\d{13}$";
        Assertions.assertTrue("9787111599715".matches(ISBNNumberRegex));
    }

    @Test
    public void testParse() {
        String s = "00000001";
        Assertions.assertEquals(1, Long.parseLong(s));
    }

    @Test
    public void testQueryBookISBNCode() {
        Book book = bookDao.queryBookByID(12);
        Assertions.assertEquals("9-787-111-58165-9", book.getIsbnCode());
    }

    @Test
    public void testQueryBookMetaByISBNCode() {
        Book book = bookDao.queryBookByID(12);

        String code = book.getIsbnCode();

        List<BookMeta> bookMeta = bookMetaDao.queryBookMetaByISBNCode(code);

        Assertions.assertNotNull(bookMeta.get(0));

    }
}
