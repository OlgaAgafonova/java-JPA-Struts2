package task.dao;

import task.entity.Organization;

import java.sql.Date;
import java.util.List;

public interface OrganizationDAO {

    void save(Organization organization);

    void update(Integer orgId, Date editStart);

    Organization getOrganizationById(Integer id);

    List<Organization> getAll();

    List<Organization> getOrganizations(Integer start, Integer maxRows);

    List<Organization> getOrganizationsExceptByID(Integer id, Integer start, Integer maxRows);

    List<Organization> getEditHistoryByID(Integer id);

    Long getCountOfOrganization();

    void deleteOrganizationByID(Integer id);
}
