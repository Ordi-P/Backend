package xdu.backend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BorrowServiceImplTest {

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
