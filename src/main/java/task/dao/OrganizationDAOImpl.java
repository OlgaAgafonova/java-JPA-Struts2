package task.dao;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;
import task.entity.Organization;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Repository
public class OrganizationDAOImpl implements OrganizationDAO {

    private SessionFactory sessionFactory;

    @Override
    public void save(Organization organization) {
        getCurrentSession().clear();
        getCurrentSession().save(organization);
    }

    @Override
    public void update(Organization organization) {
        Transaction transaction = getCurrentSession().getTransaction();
        transaction.begin();
        Organization lastEdit = getOrganizationById(organization.getId());
        Date ds = organization.getEditStart();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(ds.getTime()));
        calendar.add(Calendar.DATE, -1);
        String de = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.DATE);
        lastEdit.setEditEnd(Date.valueOf(de));
        getCurrentSession().update(lastEdit);
        transaction.commit();
    }

    @Override
    public Organization getOrganizationById(Integer id) {
        return (Organization) getCurrentSession()
                .createQuery("from Organization where id=? and current_date() between editStart and editEnd")
                .setInteger(0, id).uniqueResult();
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
                .createQuery("from Organization where current_date() between editStart and editEnd order by name")
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
