package task.dao;

import task.entity.JobPlace;

public interface JobPlaceDAO {

    void save(JobPlace jobPlace);

    JobPlace getJobPlaceById(Integer id);

    void deleteJobPlaceByID(Integer jobPlaceId);
}
