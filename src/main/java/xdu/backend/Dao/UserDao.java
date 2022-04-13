package xdu.backend.Dao;

import org.springframework.stereotype.Repository;
import xdu.backend.pojo.User;

@Repository
public interface UserDao {

    int registerUser(User user);

    User getUserByPhoneNumber(Long phoneNumber);

    int changeNameById(User user);
}
