package xdu.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 邓乐丰
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BookMeta {

    private String isbnCode;

    private String bookName;

    private String bookAuthor;

    /** New column in Release 3, 书的种类 */
    private String category;

    private String location;

    private String isbnNumber;

    private Integer amount;

    @Override
    public String toString() {
        return "{Book Name:" + bookName +
                "  Book Author:" + bookAuthor +
                "  location:" + location +
                "  ISBN:" + isbnCode +
                "  Amount in library:" + amount + "}";
    }
}
