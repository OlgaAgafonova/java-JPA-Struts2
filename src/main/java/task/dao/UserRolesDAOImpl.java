package task.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import task.entity.UserRoles;

import java.util.List;

public class UserRolesDAOImpl implements UserRolesDAO {

    private SessionFactory sessionFactory;

    @Override
    public void save(UserRoles userRoles) {
        getCurrentSession().saveOrUpdate(userRoles);
    }

    @Override
    public List getAllUserRoles() {
        return getCurrentSession().createQuery("from UserRoles").list();
    }

    public List getUserRoles(Integer userId) {
        Query sqlQuery = getCurrentSession().createSQLQuery("SELECT id_role FROM user_roles WHERE id_user = ?");
        sqlQuery.setInteger(0, userId);
        return sqlQuery.list();
    }

    @Override
    public void deleteUserRoleByID(UserRoles userRoles) {
        UserRoles userRoles1 = (UserRoles) getCurrentSession().load(UserRoles.class, userRoles.getId());
        if (userRoles1 != null) {
            getCurrentSession().delete(userRoles);
        }
    }

    public void deleteUserRoles(Integer userId) {
        List list = getCurrentSession().createQuery("from UserRoles where id_user = ?").setInteger(0, userId).list();
        UserRoles userRoles;
        for (int i = 0; i < list.size(); i++) {
            userRoles = (UserRoles) list.get(i);
            deleteUserRoleByID(userRoles);
        }
    }

    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
