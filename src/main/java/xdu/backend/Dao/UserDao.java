package xdu.backend.Dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xdu.backend.pojo.Admin;
import xdu.backend.pojo.User;

@Repository
public interface UserDao {

    Integer registerUser(User user);

    User getUserById(String id);

    Integer changeNameById(String newName,String id);

    Admin getAdminById(Integer id);

    Integer updatePasswordById(String id, String password);
  
    Boolean getUserEnable(String id);

    void updateUserEnable(@Param("enable") Boolean enable, @Param("id") String id);

    String queryEmailAddressByID(String userID);
}


