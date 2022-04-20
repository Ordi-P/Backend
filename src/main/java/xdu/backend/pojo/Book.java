package xdu.backend.pojo;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {

    private String bookID;

    private String bookName;

    private String bookAuthor;

    private String location;

    private String isbnCode;

    private String isbnNumber;

    private boolean available;

    @Override
    public String toString() {
        String ava = available ? "in library" : "not in library";
        return bookName +
                "  author:" + bookAuthor +
                "  location:" + location +
                "  id:" + bookID +
                ava;
    }

}