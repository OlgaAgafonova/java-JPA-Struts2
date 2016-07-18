package task.service;

import task.entity.Role;
import task.entity.User;

import java.util.List;

/**
 * Created by Оля on 09.07.2016.
 */
public interface UserManager {

    public void save(User user);

    public List getAllUsers();

    public User getUserByID(Integer userId);

    public List getRoles();

    public Role getRoleByID(Integer roleId);

    public void deleteUserByID(Integer userId);
}
