package xdu.backend.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xdu.backend.exception.LendOutConflictException;
import xdu.backend.exception.ReserveConflictException;
import xdu.backend.exception.UserNotExistsException;
import xdu.backend.exception.UserOperationException;
import xdu.backend.pojo.Book;
import xdu.backend.service.BorrowServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class BorrowController {

    @Autowired
    BorrowServiceImpl borrowService;

    @RequestMapping(value = "/searchbook", method = RequestMethod.GET)
    @ResponseBody
    public List<Book> searchBook(@RequestParam(value = "info", required = false) String bookInfo) {
        List<Book> bookList = borrowService.searchBook(bookInfo);
        return bookList;
    }

    @RequestMapping(value = "/getbook", method = RequestMethod.GET)
    @ResponseBody
    public List<Book> getBook(@RequestParam(value = "isbn_code") String ISBNCode) {
        List<Book> bookList = borrowService.getBook(ISBNCode);
        return bookList;
    }

    @RequestMapping(value = "/myborrow", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject myBorrow(@RequestParam("user_id") String userID) {
        // 是否为有效请求（userID是否存在）
        String result = "success";
        // 如果result为“failed”，则错误信息为errorMsg
        String errorMsg = null;
        List<Book> bookList;

        // 如果用户不存在，queryMyBorrow会抛出异常
        try {
            bookList = borrowService.queryMyBorrow(userID);
        } catch (UserNotExistsException e) {
            bookList = new ArrayList<>();
            result = "failed";
            errorMsg = e.getMessage();
        }

        // 把返回结果封装成JSON
        JSONObject json = new JSONObject();
        json.put("result", result);
        json.put("errorMsg", errorMsg);
        json.put("bookList", bookList);

        return json;
    }

    @RequestMapping(value = "/reservebook", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject reserveBook(@RequestParam("book_id") String bookID,
                                  @RequestParam("user_id") String userID) {
        // 是否预订成功
        String result = "failed";
        // 错误信息
        String errorMsg = null;

        try {
            borrowService.reserveBook(bookID, userID);
            result = "success";
        } catch (ReserveConflictException e) {
            errorMsg = e.getMessage();
        } catch (UserOperationException e) {
            errorMsg = e.getMessage();
        }

        // 返回体
        JSONObject json = new JSONObject();
        json.put("result", result);
        json.put("errorMsg", errorMsg);

        return json;
    }

    @RequestMapping(value = "/lendout", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject lendOut(@RequestParam("book_id") String bookID,
                              @RequestParam("user_id") String userID) {
        // 是否借书出库成功
        String result = "failed";
        // 错误信息
        String errorMsg = null;

        try {
            borrowService.lendOutBook(bookID, userID);
            result = "success";
        } catch (LendOutConflictException e) {
            errorMsg = e.getMessage();
        } catch (UserOperationException e) {
            errorMsg = e.getMessage();
        } catch (UserNotExistsException e) {
            errorMsg = e.getMessage();
        }

        // 返回体
        JSONObject json = new JSONObject();
        json.put("result", result);
        json.put("errorMsg", errorMsg);

        return json;
    }

    /**
     * 判断userID下的用户是否存在
     *
     * @param userID
     * @return "success" / "failed"
     */
    private String userExists(String userID) {
        return "failed";
    }

}
