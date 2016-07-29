package task.service;

import task.entity.*;

import java.util.List;

public interface Manager {

    void save(User user);

    void save(Organization organization);

    void save(JobPlace jobPlace);

    List getAllUsers();

    User getUserByID(Integer userId);

    List getAllRoles();

    Role getRoleByID(Integer roleId);

    List getAllOrganizations();

    Organization getOrganizationByID(Integer orgId);

    List getAllPositions();

    Position getPositionByID(Integer posId);

    List getJobPlacesByUserID(Integer userId);

    List getJobPlacesByOrganizationID(Integer orgId);

    JobPlace getJobPlaceByID(Integer jobPlaceId);

    void deleteUserByID(Integer userId);

    void deleteOrgByID(Integer orgId);
}
