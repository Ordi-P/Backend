package xdu.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private String userID;

    private String userName;

    private boolean enable;

    private String password;

    public User(String userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + userID + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
