package task.dao;

import task.entity.User;

import java.util.List;

public interface UserDAO {

    void save(User user);

    List getUsers(Integer start, Integer maxRows);

    List getAllUsers();

    Long getCountOfUsers();

    User getUserByID(Integer userId);

    void deleteUserByID(Integer userId);
}
