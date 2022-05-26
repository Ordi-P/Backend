package xdu.backend.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xdu.backend.Dao.BookDao;
import xdu.backend.Dao.BookMetaDao;
import xdu.backend.pojo.Book;
import xdu.backend.pojo.BookMeta;
import xdu.backend.vo.BookInfo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class BookController {
    private static final String DAMAGED_STATE = "damaged";
    private static final String LOST_STATE = "lost";

    @Autowired
    BookDao bookDao;
    @Autowired
    BookMetaDao bookMetaDao;

    @RequestMapping(value = "/addbook", method = RequestMethod.POST)
    public JSONObject addBook(@RequestParam(value = "book_name", required = true) String book_name,
                              @RequestParam(value = "book_author", required = true) String book_author,
                              @RequestParam(value = "isbn_code", required = false) String isbn_code,
                              @RequestParam(value = "isbn_number", required = true) String isbn_number,
                              @RequestParam(value = "num", required = true) int num,
                              @RequestParam(value = "location", required = true) String location,
                              @RequestParam(value = "category", required = true) String category
                   ) {
        JSONObject json = new JSONObject();
        ArrayList<Long> book_id_list = new ArrayList<>();

        // 如果isbn_code为空，就尝试着自己生成
        if (isbn_code == null) {
            isbn_code = generateISBNCode(isbn_number);
        }

        try{
            for (int i = 0; i < num; i++) {
                // 添加num本书

                Date date = new Date();
                long tem = date.getTime() - 4L * 60 * 60 * 1000 - 1;
                date.setTime(tem);
                Timestamp reserve_time = new Timestamp(date.getTime());

                Book book = new Book(book_name, book_author, category, location, isbn_code, isbn_number,
                        true, reserve_time, "13300000001", false, "reason");

                bookDao.addBook(book);
                book_id_list.add(book.getBookID());
            }
            List<BookInfo> bookMetas = bookMetaDao.queryBookMetaByISBNCode(isbn_code);
            if (bookMetas.size() == 0){
                BookMeta bookMeta = new BookMeta(isbn_code, book_name, book_author, category, location, isbn_number, 0);
                bookMetaDao.insertBookMeta(bookMeta);
            }
            bookMetaDao.updateBookMeta(isbn_number, num);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", "failed");
            return json;
        }

        json.put("result", "success");
        json.put("list", book_id_list);

        return json;
    }


    @GetMapping(value = "/deletebook")
    public JSONObject deleteBook(@RequestParam(value = "book_id", required = true) Long book_id,
                                 @RequestParam(value = "reason", required = true) String reason) {

        JSONObject json = new JSONObject();

        try{
            Book book = bookDao.queryBookByID(book_id);
            // author: xduTD，这本书目前不在图书馆中，且删除原因不是丢失，不能删除书籍
            if (!LOST_STATE.equals(reason) && !bookDao.queryBookAvailability(book_id)) {
                json.put("result", "failed");
                json.put("errorMsg", "Cannot delete this copy, the book is neither lost nor in the library");
                return json;
            }

            List<BookMeta> bookMetaList = bookMetaDao.queryBookMetaByISBNNumber(book.getIsbnNumber());
            if (bookMetaList.size() != 0) {
                // 更改数据库中的记录
                bookDao.updateBookAbandonedByID(book_id, reason);
                bookMetaDao.updateBookMeta(book.getIsbnNumber(), -1);
                json.put("result", "success");
            } else {
                json.put("result", "failed");
                json.put("errorMsg", "Database synchronizing failed, this book may have been deleted.");
            }
        } catch (Exception e){
            json.put("result", "failed");
            json.put("errorMsg", e.getMessage());
            return json;
        }

        return json;
    }

    /**
     * 在前端未将isbn_code作为参数传递到后端时，此方
     * 法可根据isbn_number生成isbn_code，目前匹配
     * 的isbn_code格式有两种，XXX-X-XXX-XXXXX-X
     * 和 XXX-X-XX-XXXXXX-X
     *
     * @param isbn_number 书本的不含'-'的ISBN串
     * @return 生成的isbn
     */
    private String generateISBNCode(String isbn_number) {
        StringBuffer sb = new StringBuffer();
        char[] ch = isbn_number.toCharArray();

        // 前面6位基本不变，都是 '978-X-'
        for (int i = 0; i < 3; i++) {
            sb.append(ch[i]);
        }
        sb.append('-');
        sb.append(ch[3]);
        sb.append('-');

        // 当第7位为0时，都是'XX-XXXXXX'格式，
        // 当第7位为1~3时，都是'XXX-XXXXX'格式，
        // 当第7位是5时，都是'XXXX-XXXX'格式
        if (ch[4] == '0') {
            // 0
            for (int i = 4; i < 6; i++) {
                sb.append(ch[i]);
            }
            sb.append('-');
            for (int i = 6; i < 12; i++) {
                sb.append(ch[i]);
            }
        } else if (ch[4] >= '1' && ch[4] <= '3') {
            // 1 ~ 3
            for (int i = 4; i < 7; i++) {
                sb.append(ch[i]);
            }
            sb.append('-');
            for (int i = 7; i < 12; i++) {
                sb.append(ch[i]);
            }
        } else if (ch[4] == '5') {
            // 5
            for (int i = 4; i < 8; i++) {
                sb.append(ch[i]);
            }
            sb.append('-');
            for (int i = 8; i < 12; i++) {
                sb.append(ch[i]);
            }
        } else {
            for (int i = 4; i < 7; i++) {
                sb.append(ch[i]);
            }
            sb.append('-');
            for (int i = 7; i < 12; i++) {
                sb.append(ch[i]);
            }
        }

        // 最后也是固定的1位校验
        sb.append('-');
        sb.append(ch[12]);

        return sb.toString();
    }

    /**
     * 根据isbn来修改location和category
     * @param isbnNumber isbn
     * @param location 新的location
     * @param category 新的category
     * @return 是否正确执行
     */
    @GetMapping(value = "/changeLocationAndCategory")
    public JSONObject changeLocationAndCategory(@RequestParam(value = "isbnNum", required = true) String isbnNumber,
                                                @RequestParam(value = "location", required = true) String location,
                                                @RequestParam(value = "category", required = true) String category
    ) {
        JSONObject json = new JSONObject();

        try{
            List<Book> books = bookDao.queryBookByISBN(isbnNumber);
            // 结果为空
            if (books.isEmpty()) {
                json.put("result", "failed");
                json.put("errorMsg", "No book with ISBN number:" + isbnNumber);
                return json;
            }

            bookDao.updateLocationAndCategoryByISBNNumber(location, category, isbnNumber);
            bookMetaDao.updateLocationAndCategoryByISBNNumber(location, category, isbnNumber);

            json.put("result", "success");
        } catch (Exception e){
            json.put("result", "failed");
            json.put("errorMsg", e.getMessage());
            return json;
        }

        return json;
    }

}
