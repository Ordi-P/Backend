package xdu.backend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查找书籍时的返回信息，相比BookMeta新增了
 * available，表示此书在馆的藏书数，删减了
 * ISBNCode字段
 *
 * @author 邓乐丰
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookInfo {

    private String bookName;

    private String bookAuthor;

    /** New column in Release 3, 书的种类 */
    private String category;

    private String location;

    private String isbnNumber;

    private Integer amount;

    private Integer available;

}
