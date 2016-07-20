package task.dao;

import task.entity.JobPlace;

import java.util.List;

public interface JobPlaceDAO {

    void save(JobPlace jobPlace);

    JobPlace getJobPlaceById(Integer id);

    List getJobPlaceByUserID(Integer userId);

    void deleteJobPlaceByUserID(Integer userId);
}
