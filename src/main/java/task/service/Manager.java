package task.service;

import task.entity.*;

import java.io.File;
import java.util.List;

public interface Manager {

    void save(User user);

    void save(Organization organization);

    void save(JobPlace jobPlace);

    void save(Certification certification);

    void addDocument(Integer orgId, String filename, File file);

    Long getTotalCountOfUsers();

    Long getTotalCountOfOrganizations();

    List<User> getUsers(Integer start, Integer maxRows);

    List<User> getAllUsers();

    User getUserByID(Integer userId);

    List<Role> getAllRoles();

    Role getRoleByID(Integer roleId);

    List<Organization> getAllOrganizations();

    List<Organization> getOrganizations(Integer start, Integer maxRows);

    Organization getOrganizationByID(Integer orgId);

    List<Position> getAllPositions();

    Position getPositionByID(Integer posId);

    List<JobPlace> getJobPlacesByUserID(Integer userId);

    List<JobPlace> getJobPlacesByOrganizationID(Integer orgId);

    JobPlace getJobPlaceByID(Integer jobPlaceId);

    List<Certification> getCertificationsByOrganizationID(Integer orgId);

    Certification getCurrentCertificationByOrganizationID(Integer orgId);

    void deleteUserByID(Integer userId);

    void deleteOrgByID(Integer orgId);
}
