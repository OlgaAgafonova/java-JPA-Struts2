package task.dao;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import task.entity.Role;

import java.util.List;

public class RoleDAOImpl implements RoleDAO {

    private SessionFactory sessionFactory;

    @Override
    public void save(Role role) {
        getCurrentSession().save(role);
    }

    @Override
    public List getAllRoles() {
        return getCurrentSession().createQuery("from Role ").list();
    }

    @Override
    public Role getRoleById(Integer id) {
        return (Role) getCurrentSession().get(Role.class, id);
    }

    @Override
    public void deleteRoleByID(Integer id) {
        Role role = getRoleById(id);
        if (role != null) {
            getCurrentSession().delete(role);
        }
    }

    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
