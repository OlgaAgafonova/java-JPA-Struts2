package task.dao;

import task.entity.JobPlace;

import java.util.List;

public interface JobPlaceDAO {

    void save(JobPlace jobPlace);

    List<JobPlace> getAll();

    JobPlace getJobPlaceById(Integer id);

    List<JobPlace> getJobPlacesByUserID(Integer userId);

    List<JobPlace> getJobPlacesByOrganizationID(Integer orgId);

    void deleteJobPlaceByUserID(Integer userId);
}
