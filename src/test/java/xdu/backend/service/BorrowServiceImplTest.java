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
import xdu.backend.vo.BookInfo;

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

}
