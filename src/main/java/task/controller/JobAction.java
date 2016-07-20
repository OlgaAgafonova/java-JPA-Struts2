package task.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import task.entity.JobPlace;
import task.service.UserManager;

import java.util.List;

public class JobAction extends ActionSupport {

    private String id;
    private UserManager userManager;
    private List<JobPlace> jobPlacesOfUser;

    public String showJobs() {
        if (id != null && !id.trim().isEmpty()) {
            jobPlacesOfUser = userManager.getJobPlaceOfUserById(Integer.valueOf(id));
            DataTableResponse tableResponse = new DataTableResponse();
            tableResponse.setAaData(jobPlacesOfUser);
            tableResponse.setiTotalRecords(jobPlacesOfUser.size());
            tableResponse.setiTotalDisplayRecords(jobPlacesOfUser.size());
            tableResponse.setsEcho(0);
            ActionContext.getContext().put("jsonJob", tableResponse);
        }
        return SUCCESS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<JobPlace> getJobPlacesOfUser() {
        return jobPlacesOfUser;
    }

    public void setJobPlacesOfUser(List<JobPlace> jobPlacesOfUser) {
        this.jobPlacesOfUser = jobPlacesOfUser;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
}
