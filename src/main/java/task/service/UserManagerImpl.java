package task.service;

import org.springframework.transaction.annotation.Transactional;
import task.dao.RoleDAO;
import task.dao.UserDAO;
import task.dao.UserRolesDAO;
import task.entity.Role;
import task.entity.User;
import task.entity.UserRoles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Оля on 09.07.2016.
 */
public class UserManagerImpl implements UserManager {
    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private UserRolesDAO userRolesDAO;

    @Override
    @Transactional
    public void addUser(User user, Role role) {

        userDAO.addUser(user);

        UserRoles userRoles = new UserRoles();
        userRoles.setId_user(user.getId());
        userRoles.setId_role(roleDAO.getRoleByName(role.getName()).getId());

        userRolesDAO.addUserRole(userRoles);
    }

    @Override
    @Transactional
    public List getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    @Transactional
    public List getRoles() {
        return roleDAO.getAllRoles();
    }

    @Override
    @Transactional
    public List getAllUsersRoles() {
        return userRolesDAO.getAllUserRoles();
    }

    @Override
    @Transactional
    public List getUserRoles(Integer userId) {
        List<String> roles = new ArrayList<>();
        List userRoles = userRolesDAO.getUserRoles(userId);
        Integer id;

        for (int i = 0; i < userRoles.size(); i++) {
            id = Integer.valueOf(userRoles.get(i).toString());
            roles.add(roleDAO.getRoleById(id).getName());
        }
        return roles;
    }

    @Override
    @Transactional
    public void deleteUserByID(Integer userId) {
        userDAO.deleteUserByID(userId);
        userRolesDAO.deleteUserRoles(userId);
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setRoleDAO(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    public void setUserRolesDAO(UserRolesDAO userRolesDAO) {
        this.userRolesDAO = userRolesDAO;
    }
}
