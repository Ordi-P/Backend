package xdu.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xdu.backend.Dao.BookMapper;
import xdu.backend.Dao.BorrowMapper;
import xdu.backend.Dao.CommentMapper;
import xdu.backend.Dao.ReserveMapper;
import xdu.backend.pojo.Book;
import xdu.backend.pojo.BookExample;
import xdu.backend.pojo.Comment;
import xdu.backend.pojo.User;

import java.util.List;

@RestController
@CrossOrigin
public class AdministerController {
    @Autowired
    BorrowMapper borrowMapper;
    @Autowired
    ReserveMapper reserveMapper;
    @Autowired
    BookMapper bookMapper;
    @Autowired
    CommentMapper commentMapper;

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

    @GetMapping(value = "/updatebook")
    @ResponseBody
    String updatebook(@RequestParam(value="bookid",required=true) String bookid,
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

    @GetMapping(value = "/searchbook")
    @ResponseBody
    JSONObject searchbook(@RequestParam(value="bookid",required=true) String bookid,
                   @RequestParam(value="bookname",required=true) String bookname){
        if(bookid == null && bookname == null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "failed");
            return jsonObject;
        }

        BookExample example1 = new BookExample();
        example1.createCriteria().andBook_idEqualTo(bookid);
        List<Book> res = bookMapper.selectByExample(example1);

        JSONObject ret = (JSONObject) JSON.toJSON(res);

        ret.put("result", "success");

        if(!res.isEmpty()){
            return ret;
        }
        return ret;
    }

    @GetMapping(value = "/comment")
    @ResponseBody
    String comment(@RequestParam(value="bookid",required=true) String bookid,
                   @RequestParam(value="comment",required=true) String comment){
        if(bookid == null || comment == null){
            return "failed";
        }

        Comment comment1 = new Comment();
        comment1.setBook_id(bookid);
        comment1.setCcomment(comment);

        int res = commentMapper.insert(comment1);
        if(res > 0){
            return "success";
        }
        return "failed";
    }

}
