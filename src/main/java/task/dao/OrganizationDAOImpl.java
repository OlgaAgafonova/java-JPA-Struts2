package task.dao;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;
import task.entity.Organization;

import java.util.List;

@Repository
public class OrganizationDAOImpl implements OrganizationDAO {

    private SessionFactory sessionFactory;

    @Override
    public void save(Organization organization) {
        getCurrentSession().saveOrUpdate(organization.getAddress());
        getCurrentSession().saveOrUpdate(organization);
    }

    @Override
    public Organization getOrganizationById(Integer id) {
        return (Organization) getCurrentSession()
                .get(Organization.class, id);
    }

    @Override
    public List<Organization> getAll() {
        return (List<Organization>) getCurrentSession()
                .createQuery("from Organization ")
                .list();
    }

    @Override
    public List<Organization> getOrganizations(Integer start, Integer maxRows) {
        return (List<Organization>) getCurrentSession()
                .createQuery("from Organization order by name")
                .setFirstResult(start)
                .setMaxResults(maxRows)
                .list();
    }

    @Override
    public List<Organization> getOrganizationsWithoutID(Integer orgId, Integer start, Integer maxRows) {
        return (List<Organization>) getCurrentSession()
                .createQuery("from Organization where id!=? order by name")
                .setInteger(0, orgId)
                .setFirstResult(start)
                .setMaxResults(maxRows)
                .list();
    }

    @Override
    public Long getCountOfOrganization() {
        return (Long) getCurrentSession()
                .createCriteria(Organization.class)
                .setProjection(Projections.rowCount())
                .uniqueResult();
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
