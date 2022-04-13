package xdu.backend.pojo;

import java.io.Serializable;

public class Book implements Serializable {
    private String book_id;

    private String nname;

    private Short free;

    private static final long serialVersionUID = 1L;

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id == null ? null : book_id.trim();
    }

    public String getNname() {
        return nname;
    }

    public void setNname(String nname) {
        this.nname = nname == null ? null : nname.trim();
    }

    public Short getFree() {
        return free;
    }

    public void setFree(Short free) {
        this.free = free;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", book_id=").append(book_id);
        sb.append(", nname=").append(nname);
        sb.append(", free=").append(free);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}