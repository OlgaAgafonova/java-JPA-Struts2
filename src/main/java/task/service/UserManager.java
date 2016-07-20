package task.service;

import task.entity.Role;
import task.entity.User;

import java.util.List;

public interface UserManager {

    void save(User user);

    List getAllUsers();

    User getUserByID(Integer userId);

    List getRoles();

    Role getRoleByID(Integer roleId);

    void deleteUserByID(Integer userId);
}
