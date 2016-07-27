package task.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import task.entity.JobPlace;
import task.entity.Organization;
import task.entity.Position;
import task.service.Manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class JobAction extends ActionSupport {

    private String id;
    private List<Organization> organizations;
    private String organization;
    private List<Position> positions;
    private String position;
    private String start;
    private String end;
    private String type;

    private Manager manager;
    private List<JobPlace> jobPlacesOfUser;

    public String index() {
        organizations = manager.getAllOrganizations();
        positions = manager.getAllPositions();
        return SUCCESS;
    }

    public String showJobs() {
        if (id != null && !id.trim().isEmpty()) {
            jobPlacesOfUser = manager.getJobPlacesOfUserByID(Integer.valueOf(id));
            DataTableResponse tableResponse = new DataTableResponse();
            tableResponse.setAaData(jobPlacesOfUser);
            tableResponse.setiTotalRecords(jobPlacesOfUser.size());
            tableResponse.setiTotalDisplayRecords(jobPlacesOfUser.size());
            tableResponse.setsEcho(0);
            ActionContext.getContext().put("jsonJob", tableResponse);
        }
        return SUCCESS;
    }

    public String addJob() {
        if (validation()) {
            return ERROR;
        }
        Organization organization = manager.getOrganizationByID(Integer.valueOf(this.organization));
        Position position = manager.getPositionByID(Integer.valueOf(this.position));
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = null, endDate = null;
        try {
            startDate = format.parse(start);
            endDate = format.parse(end);
        } catch (ParseException e) {
        }
        JobPlace jobPlace = new JobPlace();
        jobPlace.setId_user(Integer.valueOf(id));
        jobPlace.setOrganization(organization);
        jobPlace.setPosition(position);
        jobPlace.setStart(startDate);
        jobPlace.setEnd(endDate);

        if (type.equals("1") && isCrossingWithMainJobs(startDate, endDate)) {
            addActionError("Two main job in the same time");
            return ERROR;
        }

        //manager.save(jobPlace);
        return SUCCESS;
    }

    private boolean validation() {
        if (isStringEmpty(organization)) {
            return true;
        }
        if (isStringEmpty(position)) {
            return true;
        }
        if (isStringEmpty(start)) {
            return true;
        }
        return false;
    }

    private boolean isStringEmpty(String string) {
        if (string == null || string.trim().isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean isCrossingWithMainJobs(Date start, Date end) {
        boolean flag = false;
        jobPlacesOfUser = manager.getJobPlacesOfUserByID(Integer.valueOf(id));
        Iterator<JobPlace> iterator = jobPlacesOfUser.iterator();
        for (int i = 0; i < jobPlacesOfUser.size(); i++) {
            JobPlace place = iterator.next();
            if (place.getType() != 0) {
                Date startMain = place.getStart();
                Date endMain = place.getEnd();
                if (end != null && end.before(startMain)) flag = true;
                if (endMain != null && start.after(endMain)) flag = true;
            }
        }
        return flag;
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

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "JobAction{" +
                "id='" + id + '\'' +
                ", organization=" + organization +
                ", position=" + position +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
