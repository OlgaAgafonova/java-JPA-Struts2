package task.dao;

import task.entity.User;

import java.util.List;

/**
 * Created by Оля on 09.07.2016.
 */
public interface UserDAO {

    public void addUser(User user);

    public List getAllUsers();

    public void deleteUserByID(Integer userId);
}
