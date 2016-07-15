package task.service;

import task.entity.User;

import java.util.List;

/**
 * Created by Оля on 09.07.2016.
 */
public interface UserManager {

    public void addUser(User user);

    public List getAllUsers();

    public List getRoles();

    public void deleteUserByID(Integer userId);
}
