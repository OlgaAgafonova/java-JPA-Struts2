package task.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import task.entity.User;
import task.entity.UserRoles;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Оля on 09.07.2016.
 */
public class UserRolesDAOImpl implements UserRolesDAO {

    private SessionFactory sessionFactory;

    @Override
    public void addUserRole(UserRoles userRoles) {
        this.sessionFactory.getCurrentSession().save(userRoles);
    }

    @Override
    public List getAllUserRoles() {
        return this.sessionFactory.getCurrentSession().createQuery("from UserRoles").list();
    }

    public List getUserRoles(Integer userId) {
        Query sqlQuery = this.sessionFactory.getCurrentSession().createSQLQuery("SELECT id_role FROM user_roles WHERE id_user = ?");
        sqlQuery.setInteger(0, userId);
        return sqlQuery.list();
    }

    @Override
    public void deleteUserRoleByID(UserRoles userRoles) {
        UserRoles userRoles1 = (UserRoles) this.sessionFactory.getCurrentSession().load(UserRoles.class, userRoles.getId());
        if (userRoles1 != null) {
            this.sessionFactory.getCurrentSession().delete(userRoles);
        }
    }

    public void deleteUserRoles(Integer userId) {
        List list = this.sessionFactory.getCurrentSession().createQuery("from UserRoles where id_user = ?").setInteger(0, userId).list();
        UserRoles userRoles;
        for (int i = 0; i < list.size(); i++) {
            userRoles = (UserRoles) list.get(i);
            deleteUserRoleByID(userRoles);
        }
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
