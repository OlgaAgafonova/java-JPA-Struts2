package task.service;

import org.springframework.transaction.annotation.Transactional;
import task.dao.*;
import task.entity.Role;
import task.entity.User;
import task.entity.UserRoles;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class UserManagerImpl implements UserManager {
    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private UserRolesDAO userRolesDAO;
    private PositionDAO positionDAO;
    private OrganizationDAO organizationDAO;
    private JobPlaceDAO jobPlaceDAO;

    @Override
    @Transactional
    public void save(User user) {
        userDAO.save(user);

        Set<Role> roles = user.getRoles();
        Iterator<Role> iterator = roles.iterator();

        for (int i = 0; i < roles.size(); i++) {
            UserRoles userRoles = new UserRoles();
            userRoles.setId_user(user.getId());
            userRoles.setId_role(iterator.next().getId());
            userRolesDAO.save(userRoles);
        }
    }

    @Override
    @Transactional
    public List getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    @Transactional
    public User getUserByID(Integer userId) {
        return userDAO.getUserByID(userId);
    }

    @Override
    @Transactional
    public List getRoles() {
        return roleDAO.getAllRoles();
    }

    @Override
    @Transactional
    public Role getRoleByID(Integer roleId) {
        return roleDAO.getRoleById(roleId);
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

    public void setPositionDAO(PositionDAO positionDAO) {
        this.positionDAO = positionDAO;
    }

    public void setOrganizationDAO(OrganizationDAO organizationDAO) {
        this.organizationDAO = organizationDAO;
    }

    public void setJobPlaceDAO(JobPlaceDAO jobPlaceDAO) {
        this.jobPlaceDAO = jobPlaceDAO;
    }
}
