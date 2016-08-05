package task.dao;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;
import task.entity.User;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private SessionFactory sessionFactory;

    @Override
    public void save(User user) {
        getCurrentSession().saveOrUpdate(user);
    }

    @Override
    public Long getCountOfUsers() {
        return (Long) getCurrentSession()
                .createCriteria(User.class)
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }

    @Override
    public List<User> getUsers(Integer start, Integer maxRows) {
        return (List<User>) getCurrentSession()
                .createQuery("from User order by lastname")
                .setFirstResult(start)
                .setMaxResults(maxRows)
                .list();
    }

    @Override
    public List<User> getAll() {
        return (List<User>) getCurrentSession()
                .createQuery("from User")
                .list();
    }

    @Override
    public User getUserByID(Integer userId) {
        return (User) getCurrentSession().get(User.class, userId);
    }

    @Override
    public void deleteUserByID(Integer userId) {
        User user = getUserByID(userId);
        if (user != null) {
            getCurrentSession().delete(user);
        }
    }

    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
