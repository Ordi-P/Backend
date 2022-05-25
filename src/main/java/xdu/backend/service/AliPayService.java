package xdu.backend.service;

import com.alipay.api.AlipayApiException;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AliPayService {
    void aliPay(HttpServletRequest request, HttpServletResponse response, String bookId, String userId) throws IOException;

    long returnFine(String userId);

    boolean returnBook(String bookId, String userId, HttpServletResponse response);

    void updateReturnDate(String bookId, String userId);

    void payAll(HttpServletRequest request, HttpServletResponse response, String userId) throws IOException;

    void updateAllReturnDateByUserId(String userId);

    int getTotalUnpaidFines();

    int getTotalFines();

    Integer getTotalRegistered();
}