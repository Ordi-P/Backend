package xdu.backend.pojo;

import lombok.*;
import org.apache.ibatis.annotations.Options;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {

    private long bookID;

    private String bookName;

    private String bookAuthor;

    private String location;

    private String isbnCode;

    private String isbnNumber;

    private boolean available;

    public Long getBookID() {
        return bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getLocation() {
        return location;
    }

    public String getIsbnCode() {
        return isbnCode;
    }

    public String getIsbnNumber() {
        return isbnNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setBookID(long bookID) {
        this.bookID = bookID;
    }

    public Book(String bookName, String bookAuthor, String location, String isbnCode, String isbnNumber, boolean available) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.location = location;
        this.isbnCode = isbnCode;
        this.isbnNumber = isbnNumber;
        this.available = available;
    }

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