package xdu.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xdu.backend.Dao.UserDao;
import xdu.backend.pojo.Admin;
import xdu.backend.pojo.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;

    @Override
    public int registerUser(User user) {
        User userDB = userDao.getUserById(user.getId());
        if(userDB != null)
            return -1;
        return userDao.registerUser(user);
    }

    @Override
    public boolean login(User user, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        String id = user.getId();
        User userDB = userDao.getUserById(id);
        if(userDB == null) {
            System.out.println("userDB null");
            return false;
        }
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
    public boolean changeNameById(String newName, String id) {
        if(newName == null)
            return false;
        User userDB = userDao.getUserById(id);
        if(userDB == null)
            return false;
        userDB.setUserName(newName);
        int res = userDao.changeNameById(newName,userDB.getId());
        if (res > 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean adminLogin(Admin admin, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        Integer id = admin.getAdminId();
        Admin adminDB = userDao.getAdminById(id);
        if(adminDB == null) return false;
        if(adminDB.getPassword().equals(admin.getPassword())){
            String uuid = UUID.randomUUID().toString().replace("-","");
            session.setAttribute(uuid,adminDB);
            Cookie cookie = new Cookie("adminCookie", uuid);
            cookie.setMaxAge(30 * 24 * 60 * 60);
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePasswordById(String id, String password) {
        User userDB = userDao.getUserById(id);
        if(userDB == null) return false;
        int res = userDao.updatePasswordById(id,password);
        if (res > 0) return true;
        return false;
    }
}
