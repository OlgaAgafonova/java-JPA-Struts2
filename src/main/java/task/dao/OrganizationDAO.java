package task.dao;

import task.entity.Organization;

public interface OrganizationDAO {

    void save(Organization organization);

    Organization getOrganizationById(Integer id);

    void deleteOrganizationByID(Integer id);
}
