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
    public List<JobPlace> getJobPlacesByUserID(Integer userId) {
        return (List<JobPlace>) getCurrentSession()
                .createQuery("from JobPlace where user.id = ?")
                .setInteger(0, userId)
                .list();
    }

    @Override
    public List<JobPlace> getJobPlacesByOrganizationID(Integer orgId) {
        return (List<JobPlace>) getCurrentSession()
                .createQuery("from JobPlace where organization.id = ?")
                .setInteger(0, orgId)
                .list();
    }

    @Override
    public void deleteJobPlaceByUserID(Integer id) {
        List<JobPlace> jobPlacesOfUser = getJobPlacesByUserID(id);
        if (jobPlacesOfUser != null) {
            for (JobPlace aJobPlacesOfUser : jobPlacesOfUser) {
                getCurrentSession().delete(aJobPlacesOfUser.getId());
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
