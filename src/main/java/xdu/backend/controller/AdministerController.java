package xdu.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xdu.backend.Dao.BookMapper;
import xdu.backend.Dao.BorrowMapper;
import xdu.backend.Dao.ReserveMapper;
import xdu.backend.pojo.Book;
import xdu.backend.pojo.User;

@Controller
@RequestMapping("/administer")
public class AdministerController {
    @Autowired
    BorrowMapper borrowMapper;
    @Autowired
    ReserveMapper reserveMapper;
    @Autowired
    BookMapper bookMapper;

    @GetMapping(value = "/addbook")
    @ResponseBody
    String addbook(@RequestParam(value="bookid",required=true) String bookid,
                        @RequestParam(value="bookname",required=true) String bookname,
                        @RequestParam(value="booknum",required=true) short booknum){
        if(bookid == null || bookname == null){
            return "failed";
        }
        Book book = new Book();
        book.setBook_id(bookid);
        book.setNname(bookname);
        book.setFree(booknum);
        int res = bookMapper.insert(book);
        if(res > 0){
            return "success";
        }
        return "failed";
    }

    @GetMapping(value = "/removebook")
    @ResponseBody
    String removebook(@RequestParam(value="bookid",required=true) String bookid){
        if(bookid == null){
            return "failed";
        }
        int res = bookMapper.deleteByPrimaryKey(bookid);
        if(res > 0){
            return "success";
        }
        return "failed";
    }

    @GetMapping(value = "/reserve")
    @ResponseBody
    String reserve(@RequestParam(value="bookid",required=true) String bookid,
                   @RequestParam(value="bookname") String bookname,
                   @RequestParam(value="booknum") short booknum){
        if(bookid == null){
            return "failed";
        }
        Book book = new Book();
        book.setBook_id(bookid);
        if (bookname != null)
            book.setNname(bookname);
        book.setFree(booknum);
        int res = bookMapper.updateByPrimaryKeySelective(book);
        if(res > 0){
            return "success";
        }
        return "failed";
    }

}
