package task.service;

import task.entity.Organization;
import task.entity.Role;
import task.entity.User;

import java.util.List;

public interface UserManager {

    void save(User user);

    void save(Organization organization);

    List getAllUsers();

    User getUserByID(Integer userId);

    List getRoles();

    Role getRoleByID(Integer roleId);

    List getAllOrganizations();

    Organization getOrganizationById(Integer orgId);

    List getJobPlaceOfUserById(Integer userId);

    void deleteUserByID(Integer userId);
}
