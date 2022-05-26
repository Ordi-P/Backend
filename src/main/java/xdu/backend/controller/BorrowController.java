package xdu.backend.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xdu.backend.exception.*;
import xdu.backend.pojo.Book;
import xdu.backend.service.BorrowServiceImpl;
import xdu.backend.vo.AbandonedBook;
import xdu.backend.vo.BookInfo;
import xdu.backend.vo.ReqParam;
import xdu.backend.vo.UserBorrowInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 邓乐丰
 */
@RestController
@CrossOrigin
public class BorrowController {

    @Autowired
    BorrowServiceImpl borrowService;

    @RequestMapping(value = "/searchbook", method = RequestMethod.GET)
    @ResponseBody
    public List<BookInfo> searchBook(@RequestParam(value = "info", required = false) String bookInfo) {
        return borrowService.searchBook(bookInfo);
    }


    @RequestMapping(value = "/getbook", method = RequestMethod.GET)
    @ResponseBody
    public List<Book> getBook(@RequestParam(value = "isbn_number") String isbnNumber) {
        return borrowService.getBook(isbnNumber);
    }


    @RequestMapping(value = "renew", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject renew(@RequestBody ReqParam param) {
        // 是否为有效请求（userID和bookID格式是否符合）
        String result = "success";
        // 如果result为“failed”，则错误信息为errorMsg
        String errorMsg = null;

        String transactionID = param.getTransactionID().trim();
        String bookID = param.getBookID().trim();
        String userID = param.getUserID().trim();

        // 判断参数是否合法
        if (isValidBookID(bookID) && isValidUserID(userID)) {
            // 参数ok，开启预订书籍事务
            try {
                borrowService.renew(Long.parseLong(transactionID), Long.parseLong(bookID), userID);
                result = "success";
            } catch (BorrowTimeExpireException |
                    UserNotExistsException |
                    BookNotExistsException |
                    UserOperationException e) {
                result = "failed";
                errorMsg = e.getMessage();
            }
        } else {
            result = "failed";
            errorMsg = "Parameter Error: Check the length of book ID and user ID.";
        }

        // 返回体
        JSONObject json = new JSONObject();
        json.put("result", result);
        json.put("errorMsg", errorMsg);

        return json;
    }


    @RequestMapping(value = "/mycurrentborrow", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject myCurrentBorrow(@RequestParam(value = "user_id", required = true) String userID) {
        userID = userID.trim();
        // 是否为有效请求
        String result = "success";
        // 如果result为“failed”，则错误信息为errorMsg
        String errorMsg = null;
        List<UserBorrowInfo> borrowList = new ArrayList<>();

        if (isValidUserID(userID)) {
            // 如果用户不存在，queryMyBorrow会抛出异常
            try {
                borrowList = borrowService.queryMyCurrentBorrow(userID);
            } catch (UserNotExistsException e) {
                borrowList = new ArrayList<>();
                result = "failed";
                errorMsg = e.getMessage();
            }
        } else {
            result = "failed";
            errorMsg = "Parameter Error: Check the length of user ID.";
        }

        // 把返回结果封装成JSON
        JSONObject json = new JSONObject();
        json.put("result", result);
        json.put("errorMsg", errorMsg);
        json.put("borrowList", borrowList);

        return json;
    }


    @RequestMapping(value = "/myborrowhistory", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject myBorrowHistory(@RequestParam("user_id") String userID) {
        userID = userID.trim();
        // 是否为有效请求
        String result = "success";
        // 如果result为“failed”，则错误信息为errorMsg
        String errorMsg = null;
        List<UserBorrowInfo> borrowList = new ArrayList<>();

        if (isValidUserID(userID)) {
            // 如果用户不存在，queryMyBorrow会抛出异常
            try {
                borrowList = borrowService.queryMyHistoryBorrow(userID);
            } catch (UserNotExistsException e) {
                borrowList = new ArrayList<>();
                result = "failed";
                errorMsg = e.getMessage();
            }
        } else {
            result = "failed";
            errorMsg = "Parameter Error: Check the length of user ID.";
        }

        // 把返回结果封装成JSON
        JSONObject json = new JSONObject();
        json.put("result", result);
        json.put("errorMsg", errorMsg);
        json.put("borrowList", borrowList);

        return json;
    }


    @RequestMapping(value = "/reservebook", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject reserveBook(@RequestParam("book_id") String bookID,
                                  @RequestParam("user_id") String userID) {
        // 参数预处理
        bookID = bookID.trim();
        userID = userID.trim();
        // 是否预订成功
        String result = "failed";
        // 错误信息
        String errorMsg = null;

        // 判断参数是否合法
        if (isValidBookID(bookID) && isValidUserID(userID)) {
            // 参数ok，开启预订书籍事务
            try {
                borrowService.reserveBook(Long.parseLong(bookID), userID);
                result = "success";
            } catch (ReserveConflictException |
                    UserOperationException |
                    UserNotExistsException |
                    BookNotExistsException e) {
                errorMsg = e.getMessage();
            }
        } else {
            result = "failed";
            errorMsg = "Parameter Error: Check the length of book ID and user ID.";
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

        // 参数预处理
        bookID = bookID.trim();
        userID = userID.trim();
        // 是否借书出库成功
        String result = "failed";
        // 错误信息
        String errorMsg = null;

        // 先判断参数是否符合
        if (isValidUserID(userID) && isValidBookID(bookID)) {
            // 没问题，再开启借书出库事务
            try {
                borrowService.lendOutBook(Long.parseLong(bookID), userID);
                result = "success";
            } catch (LendOutConflictException |
                    UserOperationException |
                    UserNotExistsException |
                    BookNotExistsException e) {
                errorMsg = e.getMessage();
            }
        } else {
            result = "failed";
            errorMsg = "Parameter Error: Check the length of book ID and user ID.";
        }

        // 返回体
        JSONObject json = new JSONObject();
        json.put("result", result);
        json.put("errorMsg", errorMsg);

        return json;
    }

    @RequestMapping(value = "/bookstates", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getBookStates() {
        // 获取书籍状态状态
        int[] states = borrowService.queryBookStates();
        JSONObject json = new JSONObject();

        // 状态与字段映射起来
        json.put("collectionNumber", states[0]);
        json.put("bookMetaNumber", states[1]);
        json.put("lentoutNumber", states[2]);
        json.put("damagedNumber", states[3]);
        json.put("lostNumber", states[4]);

        return json;
    }

    @RequestMapping(value = "/lostbooks", method = RequestMethod.GET)
    @ResponseBody
    public List<AbandonedBook> getLostBooks() {
        return borrowService.queryLostBooks();
    }

    @RequestMapping(value = "/damagedbooks", method = RequestMethod.GET)
    @ResponseBody
    public List<AbandonedBook> getDamagedBooks() {
        return borrowService.queryDamagedBooks();
    }

    /**
     * 判断userID是否符合id格式
     *
     * @param userID
     */
    private boolean isValidUserID(String userID) {
        return userID.matches("^\\d{11}$");
    }

    /**
     * 判断bookID是否符合id格式
     *
     * @param bookID
     */
    private boolean isValidBookID(String bookID) {
        return bookID.matches("^\\d{8}$");
    }

}
