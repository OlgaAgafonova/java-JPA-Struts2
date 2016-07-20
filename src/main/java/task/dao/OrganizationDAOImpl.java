package task.dao;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import task.entity.Organization;

import java.util.List;

@Repository
public class OrganizationDAOImpl implements OrganizationDAO {

    private SessionFactory sessionFactory;

    @Override
    public void save(Organization organization) {
        getCurrentSession().saveOrUpdate(organization);
    }

    @Override
    public Organization getOrganizationById(Integer id) {
        return (Organization) getCurrentSession().get(Organization.class, id);
    }

    @Override
    public List<Organization> getAll() {
        return getCurrentSession().createQuery("from Organization ").list();
    }

    @Override
    public void deleteOrganizationByID(Integer id) {
        Organization organization = getOrganizationById(id);
        if (organization != null) {
            getCurrentSession().delete(organization);
        }
    }

    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
