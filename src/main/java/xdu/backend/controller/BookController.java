package xdu.backend.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xdu.backend.Dao.BookDao;
import xdu.backend.Dao.BookMetaDao;
import xdu.backend.pojo.Book;
import xdu.backend.pojo.BookMeta;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class BookController {
    @Autowired
    BookDao bookDao;
    @Autowired
    BookMetaDao bookMetaDao;

    @GetMapping(value = "/addbook")
    public JSONObject addBook(@RequestParam(value = "book_name", required = true) String book_name,
                   @RequestParam(value = "book_author", required = true) String book_author,
                   @RequestParam(value = "isbn_code", required = false) String isbn_code,
                   @RequestParam(value = "isbn_number", required = true) String isbn_number,
                   @RequestParam(value = "num", required = true) int num
                   ) {
        JSONObject json = new JSONObject();
        ArrayList<Long> book_id_list = new ArrayList<>();

        if (isbn_code == null) { // 如果isbn_code为空，就尝试着自己生成
            isbn_code = generateISBNCode(isbn_number);
        }

        try{
            for (int i = 0; i < num; i++) { // 添加num本书

                Date date = new Date();
                long tem = date.getTime() - 4 * 60 * 60 * 1000 - 1;
                date.setTime(tem);
                Timestamp reserve_time = new Timestamp(date.getTime());

                // 因为isbn_number的5、6位是出版社号，所有书几乎一样，就改成10、11位，增加随机性
                Book book = new Book(book_name, book_author, "A-" + isbn_number.substring(10, 12), isbn_code, isbn_number, true, reserve_time, "13300000001");

                bookDao.addBook(book);
                book_id_list.add(book.getBookID());
            }
            List<BookMeta> bookMetas = bookMetaDao.queryBookMetaByISBNCode(isbn_code);
            if (bookMetas.size() == 0){
                BookMeta bookMeta = new BookMeta(isbn_code, book_name, book_author, "A-10", isbn_number, 0);
                bookMetaDao.insertBookMeta(bookMeta);
            }
            bookMetaDao.updateBookMeta(isbn_code, num);
        } catch (Exception e){
            e.printStackTrace();
            json.put("result", "failed");
            return json;
        }

        json.put("result", "success");
        json.put("list", book_id_list);

        return json;
    }

    @GetMapping(value = "/deletebook")
    public JSONObject deleteBook(@RequestParam(value = "book_id", required = true) Long book_id){
        JSONObject json = new JSONObject();

        try{
            Book book = bookDao.queryBookByID(book_id);
            if (bookDao.deleteBook(book_id) > 0) {
                List<BookMeta> list = bookMetaDao.queryBookMetaByISBNCode(book.getIsbnCode());
                if (list.size() != 0){
                    if (list.get(0).getAmount() > 1) {
                        bookMetaDao.updateBookMeta(book.getIsbnCode(), -1);
                    } else {
                        bookMetaDao.deleteBookMeta(book.getIsbnCode());
                    }
                    json.put("result", "success");
                } else {
                    json.put("result", "failed");
                }
            }
            else
                json.put("result", "failed");
        } catch (Exception e){
            e.printStackTrace();
            json.put("result", "failed");
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
     * @param isbn_number
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


}
