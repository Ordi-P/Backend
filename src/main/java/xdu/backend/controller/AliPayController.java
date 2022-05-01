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
    public long preReturnBook(String bookId){
        long abookId = Long.parseLong(bookId);
        long res = alipayService.returnFine(abookId);
        return res;
    }

    @RequestMapping("/returnBook")
    public String returnBook(String bookId){
        boolean res = alipayService.returnBook(bookId);
        if(res) return "success";
        return "failed";
    }
}
