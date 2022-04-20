package xdu.backend.Dao;

import org.springframework.stereotype.Repository;
import xdu.backend.pojo.User;

@Repository
public interface UserDao {

    int registerUser(User user);

    User getUserById(String id);

    int changeNameById(String newName,String id);

    boolean getUserEnable(String id);
}
