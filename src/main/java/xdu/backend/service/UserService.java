package xdu.backend.service;

import xdu.backend.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface UserService {
    int registerUser(User user);

    boolean login(User user, HttpSession session, HttpServletRequest request, HttpServletResponse response);

    boolean changeNameById(String newName, String id);
}
