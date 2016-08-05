package task.dao;

import task.entity.User;

import java.util.List;

public interface UserDAO {

    void save(User user);

    List<User> getUsers(Integer start, Integer maxRows);

    List<User> getAll();

    Long getCountOfUsers();

    User getUserByID(Integer userId);

    void deleteUserByID(Integer userId);
}
