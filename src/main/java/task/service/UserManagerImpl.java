package task.service;

import org.springframework.transaction.annotation.Transactional;
import task.dao.RoleDAO;
import task.dao.UserDAO;
import task.dao.UserRolesDAO;
import task.entity.Role;
import task.entity.User;
import task.entity.UserRoles;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Оля on 09.07.2016.
 */
public class UserManagerImpl implements UserManager {
    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private UserRolesDAO userRolesDAO;

    @Override
    @Transactional
    public void addUser(User user, Set<Role> roles) {
        userDAO.addUser(user);

        Iterator<Role> iterator = roles.iterator();
        for (int i = 0; i < roles.size(); i++) {
            UserRoles userRoles = new UserRoles();
            userRoles.setId_user(user.getId());
            userRoles.setId_role(roleDAO.getRoleByName(iterator.next().getName()).getId());
            userRolesDAO.addUserRole(userRoles);
        }
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
