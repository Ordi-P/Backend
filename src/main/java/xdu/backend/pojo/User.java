package xdu.backend.pojo;

import java.io.Serializable;


public class User implements Serializable {

    private String userId;
    private String userName;
    private String nickName;
    private Long phoneNumber;
    private String password;
    private Integer privilege;
    private String address;

    public User() {
    }

    public User(String userId, String userName, String nickName, Long phoneNumber, String password, Integer privilege, String address) {
        this.userId = userId;
        this.userName = userName;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.privilege = privilege;
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Integer privilege) {
        this.privilege = privilege;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", privilege=" + privilege +
                ", address='" + address + '\'' +
                '}';
    }
}
