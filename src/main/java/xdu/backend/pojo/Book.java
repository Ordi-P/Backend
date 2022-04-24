package xdu.backend.pojo;

import lombok.*;

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

    private Timestamp reserveTime;

    private String reserveUser;

    public Timestamp getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(Timestamp reserveTime) {
        this.reserveTime = reserveTime;
    }

    public String getReserveUser() {
        return reserveUser;
    }

    public void setReserveUser(String reserveUser) {
        this.reserveUser = reserveUser;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
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

    public Book(String bookName, String bookAuthor, String location, String isbnCode, String isbnNumber, boolean available, Timestamp reserveTime, String reserveUser) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.location = location;
        this.isbnCode = isbnCode;
        this.isbnNumber = isbnNumber;
        this.available = available;
        this.reserveTime = reserveTime;
        this.reserveUser = reserveUser;
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