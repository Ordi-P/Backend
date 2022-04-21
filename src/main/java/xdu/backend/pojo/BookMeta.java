package xdu.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookMeta {

    /** PRIMARY KEY */
    private String isbnCode;

    private String bookName;

    private String bookAuthor;

    private String location;

    private String isbnNumber;

    private int amount;

    @Override
    public String toString() {
        return "{Book Name:" + bookName +
                "  Book Author:" + bookAuthor +
                "  location:" + location +
                "  ISBN:" + isbnCode +
                "  Amount in library:" + amount + "}";
    }
}
