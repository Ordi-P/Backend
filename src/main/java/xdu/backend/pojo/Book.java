package xdu.backend.pojo;

import lombok.*;
import org.apache.ibatis.annotations.Options;

import java.io.Serializable;
import java.sql.Timestamp;

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

    private Timestamp reserve_time;

    private String reserve_user;

    public Timestamp getReserve_time() {
        return reserve_time;
    }

    public void setReserve_time(Timestamp reserve_time) {
        this.reserve_time = reserve_time;
    }

    public String getReserve_user() {
        return reserve_user;
    }

    public void setReserve_user(String reserve_user) {
        this.reserve_user = reserve_user;
    }

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

    public Book(String bookName, String bookAuthor, String location, String isbnCode, String isbnNumber, boolean available, Timestamp reserve_time, String reserve_user) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.location = location;
        this.isbnCode = isbnCode;
        this.isbnNumber = isbnNumber;
        this.available = available;
        this.reserve_time = reserve_time;
        this.reserve_user = reserve_user;
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