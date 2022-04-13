package xdu.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xdu.backend.Dao.UserDao;
import xdu.backend.pojo.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;

    @Override
    public int registerUser(User user) {
        User userDB = userDao.getUserByPhoneNumber(user.getPhoneNumber());
        if(user != null)
            return -1;
        return userDao.registerUser(user);
    }

    @Override
    public boolean login(User user, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        Long phoneNumber = user.getPhoneNumber();
        User userDB = userDao.getUserByPhoneNumber(phoneNumber);
        if(userDB.getPassword().equals(user.getPassword())){
            String uuid = UUID.randomUUID().toString().replace("-","");
            session.setAttribute("userTicket:" + uuid,userDB);
            Cookie cookieUsername = new Cookie("cookieUserName", uuid);
            cookieUsername.setMaxAge(30 * 24 * 60 * 60);
            cookieUsername.setPath(request.getContextPath());
            response.addCookie(cookieUsername);
            return true;
        }
        return false;
    }

    @Override
    public boolean changeNameById(String newName, Long phoneNumber) {
        if(newName == null)
            return false;
        User userDB = userDao.getUserByPhoneNumber(phoneNumber);
        if(userDB == null)
            return false;
        userDB.setUserName(newName);
        int res = userDao.changeNameById(newName,userDB.getPhoneNumber());
        if (res > 0)
            return true;
        else
            return false;
    }
}
