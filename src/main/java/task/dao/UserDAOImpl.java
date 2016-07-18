package task.dao;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import task.entity.User;

import java.util.List;

/**
 * Created by Оля on 09.07.2016.
 */
@Repository
public class UserDAOImpl implements UserDAO {

    private SessionFactory sessionFactory;

    @Override
    public void addUser(User user) {
        getCurrentSession().saveOrUpdate(user);
    }

    @Override
    public List<User> getAllUsers() {
        return getCurrentSession().createQuery("from User").list();
    }

    @Override
    public User getUserByID(Integer userId) {
        User user = (User) this.sessionFactory.getCurrentSession().get(User.class, userId);
        return user;
    }

    @Override
    public void deleteUserByID(Integer userId) {
        User user = (User) getCurrentSession().load(User.class, userId);
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
