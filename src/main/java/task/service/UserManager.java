package task.service;

import task.entity.Role;
import task.entity.User;

import java.util.List;
import java.util.Set;

/**
 * Created by Оля on 09.07.2016.
 */
public interface UserManager {

    public void addUser(User user, Set<Role> roles);

    public List getAllUsers();

    public List getRoles();

    public void deleteUserByID(Integer userId);
}
