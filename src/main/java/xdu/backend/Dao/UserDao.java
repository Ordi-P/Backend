package xdu.backend.Dao;

import org.springframework.stereotype.Repository;
import xdu.backend.pojo.Admin;
import xdu.backend.pojo.User;

@Repository
public interface UserDao {

    int registerUser(User user);

    User getUserById(String id);

    int changeNameById(String newName,String id);

    Admin getAdminById(Integer id);

    int updatePasswordById(String id, String password);
}


