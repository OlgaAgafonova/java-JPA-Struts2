package task.dao;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import task.entity.JobPlace;

public class JobPlaceDAOImpl implements JobPlaceDAO {

    private SessionFactory sessionFactory;

    @Override
    public void save(JobPlace jobPlace) {
        getCurrentSession().saveOrUpdate(jobPlace);
    }

    @Override
    public JobPlace getJobPlaceById(Integer id) {
        return (JobPlace) getCurrentSession().get(JobPlace.class, id);
    }

    @Override
    public void deleteJobPlaceByID(Integer id) {
        JobPlace jobPlace = getJobPlaceById(id);
        if (jobPlace != null) {
            getCurrentSession().delete(jobPlace);
        }
    }

    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
