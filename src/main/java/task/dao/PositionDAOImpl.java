package task.dao;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import task.entity.Position;

import java.util.List;

@Repository
public class PositionDAOImpl implements PositionDAO {

    private SessionFactory sessionFactory;

    @Override
    public void save(Position position) {
        getCurrentSession().saveOrUpdate(position);
    }

    @Override
    public Position getPositionById(Integer id) {
        return (Position) getCurrentSession().get(Position.class, id);
    }

    @Override
    public List<Position> getAll() {
        return getCurrentSession().createQuery("from Position ").list();
    }

    @Override
    public void deletePositionByID(Integer id) {
        Position position = getPositionById(id);
        if (position != null) {
            getCurrentSession().delete(position);
        }
    }

    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
