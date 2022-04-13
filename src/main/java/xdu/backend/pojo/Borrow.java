package xdu.backend.pojo;

import java.io.Serializable;

public class Borrow implements Serializable {
    private String book_id;

    private String user_id;

    private Integer data_from;

    private Integer data_to;

    private static final long serialVersionUID = 1L;

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id == null ? null : book_id.trim();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    public Integer getData_from() {
        return data_from;
    }

    public void setData_from(Integer data_from) {
        this.data_from = data_from;
    }

    public Integer getData_to() {
        return data_to;
    }

    public void setData_to(Integer data_to) {
        this.data_to = data_to;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", book_id=").append(book_id);
        sb.append(", user_id=").append(user_id);
        sb.append(", data_from=").append(data_from);
        sb.append(", data_to=").append(data_to);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}