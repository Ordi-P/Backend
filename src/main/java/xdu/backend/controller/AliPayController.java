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
    public void payFine(HttpServletRequest request, HttpServletResponse response, String bookId, String userId) throws IOException {
        this.alipayService.aliPay(request, response, bookId, userId);
    }

    @RequestMapping("/preReturnBook")
    public long preReturnBook(String userId){
        long res = alipayService.returnFine(userId);
        return res;
    }

    @RequestMapping("/returnBook")
    public String returnBook(String bookId, String userId, HttpServletResponse response){
        boolean res = alipayService.returnBook(bookId, userId, response);
        if(res) return "success";
        return "failed";
    }

    @RequestMapping("/updateBorrowDate")
    public void updateBorrowDate(String bookId, String userId, HttpServletResponse response) throws IOException {
        alipayService.updateReturnDate(bookId,userId);
        response.sendRedirect("http://192.168.27.1:3000/userhome");
    }

    @RequestMapping("/payAll")
    public void payAll(HttpServletRequest request, HttpServletResponse response, String userId) throws IOException {
        alipayService.payAll(request,response,userId);
    }

    @RequestMapping("/updateAllBorrowDateByUserId")
    public void updateAllBorrowDateByUserId(String userId, HttpServletResponse response) throws IOException {
        alipayService.updateAllReturnDateByUserId(userId);
        response.sendRedirect("http://192.168.27.1:3000/userhome");
    }
}
