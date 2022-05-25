package xdu.backend.service;

import xdu.backend.pojo.Admin;
import xdu.backend.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface UserService {
    int registerUser(User user);

    boolean login(User user, HttpSession session, HttpServletRequest request, HttpServletResponse response);

    boolean changeNameById(String newName, String id);

    boolean adminLogin(Admin admin, HttpSession session, HttpServletRequest request, HttpServletResponse response);

    boolean updatePasswordById(String id, String password);

    int updateEmailByUserId(String id, String email);
}
