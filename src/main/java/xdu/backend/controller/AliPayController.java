package xdu.backend.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xdu.backend.service.AliPayService;

@RestController
public class AliPayController {
    @Autowired
    AliPayService alipayService;


    public AliPayController() {
    }

    @RequestMapping({"/pay"})
    public void payFine(HttpServletRequest request, HttpServletResponse response, String bookId) throws IOException {
        this.alipayService.aliPay(request, response, bookId);
    }

    @RequestMapping("/preReturnBook")
    public long preReturnBook(String userId){
        long res = alipayService.returnFine(userId);
        return res;
    }

    @RequestMapping("/returnBook")
    public String returnBook(String bookId){
        boolean res = alipayService.returnBook(bookId);
        if(res) return "success";
        return "failed";
    }

    @RequestMapping("/updateBorrowDate")
    public void updateBorrowDate(String bookId, HttpServletResponse response) throws IOException {
        alipayService.updateReturnDate(bookId);
        response.sendRedirect("http://192.168.27.1:3000/userhome");
    }
}
