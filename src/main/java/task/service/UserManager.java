package task.service;

import task.entity.Role;
import task.entity.User;

import java.util.List;

/**
 * Created by Оля on 09.07.2016.
 */
public interface UserManager {

    public void addUser(User user, Role role);

    public List getAllUsers();

    public List getRoles();

    public List getAllUsersRoles();

    public List getUserRoles(Integer userId);

    public void deleteUserByID(Integer userId);
}
