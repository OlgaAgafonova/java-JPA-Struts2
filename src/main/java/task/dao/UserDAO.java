package task.dao;

import task.entity.User;

import java.util.List;

public interface UserDAO {

    void save(User user);

    List getAllUsers();

    User getUserByID(Integer userId);

    void deleteUserByID(Integer userId);
}
