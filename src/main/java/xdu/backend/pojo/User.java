package xdu.backend.pojo;

import java.io.Serializable;

public class User implements Serializable {

    private String id;

    private String userName;

    private Boolean enable;

    private String password;
    /** 邮箱初始为学生邮箱，可以后续自己更换 */
    private String email;

    /** No args constructor */
    public User(){}

    /** All args constructor */
    public User(String id, String userName, String password, String email) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public User(String id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = id + "@stu.xidian.edu.cn";
    }

    /** getter & setter here */

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean getEnable() {
        return enable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        if (this.email == null) {
            this.email = id + "@stu.xidian.edu.cn";
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
