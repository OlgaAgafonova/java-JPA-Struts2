package task.dao;

import task.entity.Organization;

import java.util.List;

public interface OrganizationDAO {

    void save(Organization organization);

    Organization getOrganizationById(Integer id);

    List<Organization> getAll();

    void deleteOrganizationByID(Integer id);
}
