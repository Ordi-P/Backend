package xdu.backend.service;

import com.alipay.api.AlipayApiException;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AliPayService {
    void aliPay(HttpServletRequest request, HttpServletResponse response, String bookId) throws IOException;

    long returnFine(long bookId);

    boolean returnBook(String bookId);
}