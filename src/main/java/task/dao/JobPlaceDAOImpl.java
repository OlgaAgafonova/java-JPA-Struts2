package task.dao;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import task.entity.JobPlace;

import java.util.List;

@Repository
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
    public List<JobPlace> getJobPlaceByUserID(Integer userId) {
        return getCurrentSession().createQuery("from JobPlace where id_user = ?").setInteger(0, userId).list();
    }

    @Override
    public void deleteJobPlaceByUserID(Integer id) {
        List<JobPlace> jobPlacesOfUser = getJobPlaceByUserID(id);
        if (jobPlacesOfUser != null) {
            for (int i = 0; i < jobPlacesOfUser.size(); i++) {
                getCurrentSession().delete(jobPlacesOfUser.get(i).getId());
            }
        }
    }

    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
