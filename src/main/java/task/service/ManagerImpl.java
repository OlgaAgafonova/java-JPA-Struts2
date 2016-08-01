package task.service;

import org.springframework.transaction.annotation.Transactional;
import task.dao.*;
import task.entity.*;

import java.util.*;

public class ManagerImpl implements Manager {
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
    public void save(Organization organization) {
        organizationDAO.save(organization);
    }

    @Override
    @Transactional
    public void save(JobPlace jobPlace) {
        jobPlaceDAO.save(jobPlace);
    }

    @Override
    @Transactional
    public Long getTotalCountOfUsers() {
        return userDAO.getCountOfUsers();
    }

    @Override
    @Transactional
    public Long getTotalCountOfOrganizations() {
        return organizationDAO.getCountOfOrganization();
    }

    @Override
    @Transactional
    public List getUsers(Integer start, Integer maxRows) {
        return userDAO.getUsers(start, maxRows);
    }

    @Override
    @Transactional
    public List getAllUsers() {
        return userDAO.getAll();
    }

    @Override
    @Transactional
    public User getUserByID(Integer userId) {
        return userDAO.getUserByID(userId);
    }

    @Override
    @Transactional
    public List getJobPlacesByOrganizationID(Integer orgId) {
        return jobPlaceDAO.getJobPlacesByOrganizationID(orgId);
    }

    @Override
    @Transactional
    public List getAllRoles() {
        return roleDAO.getAllRoles();
    }

    @Override
    @Transactional
    public Role getRoleByID(Integer roleId) {
        return roleDAO.getRoleById(roleId);
    }

    @Override
    @Transactional
    public List getAllOrganizations() {
        return organizationDAO.getAll();
    }

    @Override
    @Transactional
    public List getOrganizations(Integer start, Integer maxRows) {
        return organizationDAO.getOrganizations(start, maxRows);
    }

    @Override
    @Transactional
    public Organization getOrganizationByID(Integer orgId) {
        return organizationDAO.getOrganizationById(orgId);
    }

    @Override
    @Transactional
    public List getAllPositions() {
        return positionDAO.getAll();
    }

    @Override
    @Transactional
    public Position getPositionByID(Integer posId) {
        return positionDAO.getPositionById(posId);
    }

    @Override
    @Transactional
    public List<JobPlace> getJobPlacesByUserID(Integer userId) {
        return (List<JobPlace>) jobPlaceDAO.getJobPlaceByUserID(userId);
    }

    @Override
    @Transactional
    public JobPlace getJobPlaceByID(Integer jobPlaceId) {
        return jobPlaceDAO.getJobPlaceById(jobPlaceId);
    }

    @Override
    @Transactional
    public void deleteUserByID(Integer userId) {
        userDAO.deleteUserByID(userId);
        userRolesDAO.deleteUserRoles(userId);
        jobPlaceDAO.deleteJobPlaceByUserID(userId);
    }

    @Override
    @Transactional
    public void deleteOrgByID(Integer orgId) {
        organizationDAO.deleteOrganizationByID(orgId);
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
