package task.dao;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import task.entity.Form;

import java.util.List;

public class FormDAOImpl implements FormDAO {

    private SessionFactory sessionFactory;

    @Override
    public void save(Form form) {
        getCurrentSession().saveOrUpdate(form);
    }

    @Override
    public List<Form> getAll() {
        return (List<Form>) getCurrentSession()
                .createQuery("from Form")
                .list();
    }

    @Override
    public Form getFormByID(Integer id) {
        return (Form) getCurrentSession().get(Form.class, id);
    }

    @Override
    public List<Form> getFormsByOrganizationID(Integer orgId) {
        return (List<Form>) getCurrentSession()
                .createQuery("from Form where orgId = ?")
                .setInteger(0, orgId)
                .list();
    }

    @Override
    public void deleteByID(Integer id) {
        Form form = getFormByID(id);
        if (form != null) {
            getCurrentSession().delete(form);
        }
    }

    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
