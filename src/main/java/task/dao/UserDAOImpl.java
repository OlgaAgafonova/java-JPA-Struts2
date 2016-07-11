package task.dao;

import org.hibernate.SessionFactory;
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
        this.sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return this.sessionFactory.getCurrentSession().createQuery("from User").list();
    }

    @Override
    public void deleteUserByID(Integer userId) {
        User user = (User) this.sessionFactory.getCurrentSession().load(User.class, userId);
        if (user != null) {
            this.sessionFactory.getCurrentSession().delete(user);
        }
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
