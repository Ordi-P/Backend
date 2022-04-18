package xdu.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xdu.backend.pojo.Book;
import xdu.backend.service.BorrowServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/book")
@CrossOrigin
public class BorrowController {

    @Autowired
    BorrowServiceImpl borrowService;

    @RequestMapping(value = "/searchbook", method = RequestMethod.GET)
    @ResponseBody
    public List<Book> searchBook(@RequestBody(required = false) String bookInfo) {
        return borrowService.searchBook(bookInfo);
    }

    @RequestMapping(value = "/myborrow", method = RequestMethod.GET)
    @ResponseBody
    public List<Book> myBorrow(String userID) {
        String result;
        List<Book> bookList = borrowService.queryMyBorrow(userID);
        if (bookList != null && bookList.size() != 0) {
            result = "success";
        } else {
            result = "failed";
        }
        return bookList;
    }

    @RequestMapping(value = "/reservebook", method = RequestMethod.POST)
    @ResponseBody
    public String reserveBook(String bookID, String userID) {
        return "success";
    }

    @RequestMapping(value = "/lendout", method = RequestMethod.POST)
    public String lendOut( String bookID, String userID) {
        return "success";
    }

}
