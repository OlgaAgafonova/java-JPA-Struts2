package task.dao;

import task.entity.Organization;

import java.util.List;

public interface OrganizationDAO {

    void save(Organization organization);

    Organization getOrganizationById(Integer id);

    List getAll();

    void deleteOrganizationByID(Integer id);
}
