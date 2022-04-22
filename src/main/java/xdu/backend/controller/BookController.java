package xdu.backend.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xdu.backend.Dao.BookDao;
import xdu.backend.Dao.BookMetaDao;
import xdu.backend.pojo.Book;
import xdu.backend.pojo.BookMeta;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class BookController {
    @Autowired
    BookDao bookDao;
    @Autowired
    BookMetaDao bookMetaDao;

    @GetMapping(value = "/addbook")
    JSONObject addBook(@RequestParam(value = "book_name", required = true) String book_name,
                   @RequestParam(value = "book_author", required = true) String book_author,
                   @RequestParam(value = "isbn_code", required = true) String isbn_code,
                   @RequestParam(value = "isbn_number", required = true) String isbn_number,
                   @RequestParam(value = "num", required = true) int num
                   ){
        JSONObject json = new JSONObject();
        ArrayList<Long> book_id_list = new ArrayList<>();

        try{
            for (int i = 0; i < num; i++) {
                Book book = new Book(book_name, book_author, "A-10", isbn_code, isbn_number, true);
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
    JSONObject deleteBook(@RequestParam(value = "book_id", required = true) Long book_id){
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


}
