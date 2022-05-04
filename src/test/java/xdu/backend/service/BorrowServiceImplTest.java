package xdu.backend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xdu.backend.Dao.BookDao;
import xdu.backend.Dao.BookMetaDao;
import xdu.backend.Dao.BorrowDao;
import xdu.backend.pojo.Book;
import xdu.backend.pojo.BookMeta;

import java.sql.Date;
import java.util.List;

@SpringBootTest
public class BorrowServiceImplTest {

    @Autowired
    BookDao bookDao;
    @Autowired
    BookMetaDao bookMetaDao;
    @Autowired
    BorrowDao borrowDao;

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

    @Test
    public void testDate() {
        Date date = borrowDao.queryBorrowDateByBookID(191);
        date.setTime(date.getTime() + 24L * 60 * 60 * 1000 - 1);
        System.out.println(date.getTime());
        System.out.println(System.currentTimeMillis());
        Assertions.assertTrue(date.before(new Date(System.currentTimeMillis())));
        // Assertions.assertEquals(date.getTime(), System.currentTimeMillis() - 1000L);

    }
}
