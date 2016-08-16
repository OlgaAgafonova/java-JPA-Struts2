package task.service;

import task.entity.*;

import java.io.File;
import java.sql.Date;
import java.util.List;

public interface Manager {

    void save(User user);

    void save(Organization organization);

    void save(JobPlace jobPlace);

    void save(Certification certification);

    void save(Form form);

    void update(Integer orgId, Date editStart);

    void addDocument(Integer orgId, String filename, File file);

    void addCertification(Integer orgId);

    void refuseCertification(Integer orgId);

    void removeCertification(Integer orgId);

    Long getTotalCountOfUsers();

    Long getTotalCountOfOrganizations();

    List<User> getUsers(Integer start, Integer maxRows);

    List<User> getAllUsers();

    User getUserByID(Integer userId);

    List<Role> getAllRoles();

    Role getRoleByID(Integer roleId);

    List<Organization> getAllOrganizations();

    List<Organization> getOrganizations(Integer start, Integer maxRows);

    List<Organization> getOrganizationsExceptByID(Integer orgId, Integer start, Integer maxRows);

    Organization getOrganizationByID(Integer orgId);

    OrganizationView getOrganizationViewByID(Integer orgId);

    List<Organization> getOrganizationEditHistoryByID(Integer orgId);

    List<Position> getAllPositions();

    Position getPositionByID(Integer posId);

    List<JobPlace> getJobPlacesByUserID(Integer userId);

    List<JobPlace> getJobPlacesByOrganizationID(Integer orgId);

    JobPlace getJobPlaceByID(Integer jobPlaceId);

    List<CertificationView> getCertificationsByOrganizationID(Integer orgId);

    CertificationView getCurrentCertificationByOrganizationID(Integer orgId);

    List<Form> getFormsByOrganizationID(Integer orgId);

    Form getFormByID(Integer formId);

    void deleteUserByID(Integer userId);

    void deleteOrgByID(Integer orgId);

    void deleteCertificationByID(Integer cerfId);
}
