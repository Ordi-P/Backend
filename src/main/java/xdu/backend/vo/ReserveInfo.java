package xdu.backend.vo;

import lombok.AllArgsConstructor;
import xdu.backend.pojo.Book;

import java.util.List;

@AllArgsConstructor
public class ReserveInfo {
    String result;

    List<Book> bookList;
}
