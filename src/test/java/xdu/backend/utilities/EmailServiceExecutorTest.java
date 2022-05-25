package xdu.backend.utilities;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceExecutorTest {
    @Autowired
    EmailServiceExecutor emailServiceExecutor;

    @Test
    public void testSendReservePromptEmail() {
        emailServiceExecutor.sendReservePromptEmail("19030500199", 5L);
    }
}
