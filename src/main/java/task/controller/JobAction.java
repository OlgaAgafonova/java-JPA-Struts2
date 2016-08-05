package task.controller;

import com.opensymphony.xwork2.ActionSupport;
import task.entity.JobPlace;
import task.entity.Organization;
import task.entity.Position;
import task.entity.User;
import task.service.Manager;
import task.service.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class JobAction extends ActionSupport {

    private Integer id_user;
    private Integer id_org;
    private Integer id_job;
    private Integer id_pos;
    private String user;
    private String organization;
    private String position;
    private String start;
    private String end;
    private String type;

    private Manager manager;
    private List<User> users;
    private List<Organization> organizations;
    private List<Position> positions;
    private List<JobPlace> jobPlacesOfUser;

    public String index() {
        users = manager.getAllUsers();
        organizations = manager.getAllOrganizations();
        positions = manager.getAllPositions();
        setFields();
        return SUCCESS;
    }

    public String showJobs() {
        if (id_user != null) {
            jobPlacesOfUser = manager.getJobPlacesByUserID(id_user);
            Utils.toResponse(jobPlacesOfUser, "jsonJob");
        }
        return SUCCESS;
    }

    public String addJob() {
        System.out.println(id_user);
        if (validation()) {
            return ERROR;
        }

        JobPlace jobPlace = new JobPlace();
        if (id_user != null && id_job == null) {
            jobPlace = makeJobPlaceFromUser();
            if (!checkCrossingWithMainJobs(
                    jobPlace.getType(),
                    jobPlace.getStart(),
                    jobPlace.getEnd(),
                    jobPlace.getUser().getId())) {
                Utils.toErrorResponse("This period of work matches other main work. Please check data.", "jsonError");
                return ERROR;
            }
        }
        if (id_org != null && id_job == null) {
            jobPlace = makeJobPlaceFromOrganization();
            if (!checkCrossingWithMainJobs(
                    jobPlace.getType(),
                    jobPlace.getStart(),
                    jobPlace.getEnd(),
                    jobPlace.getUser().getId())) {
                Utils.toErrorResponse("This period of work matches other main work. Please check data.", "jsonError");
                return ERROR;
            }
        }
        if (id_job != null) {
            jobPlace = makeJobPlaceFromEdit();
        }
        manager.save(jobPlace);
        return SUCCESS;
    }

    private void setFields() {
        if (id_job != null) {
            JobPlace jobPlace = manager.getJobPlaceByID(id_job);
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            start = format.format(jobPlace.getStart());
            if (jobPlace.getEnd() != null) {
                end = format.format(jobPlace.getEnd());
            }
        }
    }

    private JobPlace makeJobPlaceFromOrganization() {
        JobPlace jobPlace = new JobPlace();
        Organization organization = manager.getOrganizationByID(this.id_org);
        Position position = manager.getPositionByID(Integer.valueOf(this.position));
        User user = manager.getUserByID(Integer.valueOf(this.user));

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = null, endDate = null;
        try {
            startDate = format.parse(start);
            endDate = format.parse(end);
        } catch (ParseException ignored) {
        }
        if (!checkDates(startDate, endDate)) {
            Date tmp;
            tmp = startDate;
            startDate = endDate;
            endDate = tmp;
            String tmp2;
            tmp2 = start;
            start = end;
            end = tmp2;
        }

        jobPlace.setUser(user);
        jobPlace.setOrganization(organization);
        jobPlace.setPosition(position);
        jobPlace.setStart(startDate);
        jobPlace.setEnd(endDate);
        jobPlace.setType(Byte.valueOf(type));
        return jobPlace;
    }

    private JobPlace makeJobPlaceFromUser() {
        JobPlace jobPlace = new JobPlace();

        Organization organization = manager.getOrganizationByID(Integer.valueOf(this.organization));
        Position position = manager.getPositionByID(Integer.valueOf(this.position));
        User user = manager.getUserByID(this.id_user);

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = null, endDate = null;
        try {
            startDate = format.parse(start);
            endDate = format.parse(end);
        } catch (ParseException ignored) {
        }

        if (!checkDates(startDate, endDate)) {
            Date tmp;
            tmp = startDate;
            startDate = endDate;
            endDate = tmp;
            String tmp2;
            tmp2 = start;
            start = end;
            end = tmp2;
        }

        jobPlace.setUser(user);
        jobPlace.setOrganization(organization);
        jobPlace.setPosition(position);
        jobPlace.setStart(startDate);
        jobPlace.setEnd(endDate);

        jobPlace.setType(Byte.valueOf(type));
        return jobPlace;
    }

    private JobPlace makeJobPlaceFromEdit() {
        JobPlace jobPlace = new JobPlace();

        Organization organization = manager.getOrganizationByID(this.id_org);
        Position position = manager.getPositionByID(this.id_pos);
        User user = manager.getUserByID(this.id_user);

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = null, endDate = null;
        try {
            startDate = format.parse(start);
            endDate = format.parse(end);
        } catch (ParseException ignored) {
        }

        if (!checkDates(startDate, endDate)) {
            Date tmp;
            tmp = startDate;
            startDate = endDate;
            endDate = tmp;
            String tmp2;
            tmp2 = start;
            start = end;
            end = tmp2;
        }
        jobPlace.setId(id_job);
        jobPlace.setUser(user);
        jobPlace.setOrganization(organization);
        jobPlace.setPosition(position);
        jobPlace.setStart(startDate);
        jobPlace.setEnd(endDate);
        jobPlace.setType(Byte.valueOf(type));

        return jobPlace;
    }

    private boolean validation() {
        return isStringEmpty(user) && (id_user != null)
                || isStringEmpty(organization) && (id_org != null)
                || isStringEmpty(position) && (id_pos != null)
                || isStringEmpty(start);

    }

    private boolean isStringEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }

    private boolean isCrossingWithMainJobs(Date start, Date end, Integer id_user) {
        boolean flag = false;
        long aStart = start.getTime();
        long aEnd = end.getTime();
        jobPlacesOfUser = manager.getJobPlacesByUserID(id_user);
        for (Object aJobPlace : jobPlacesOfUser) {
            JobPlace place = (JobPlace) aJobPlace;
            if (place.getType() != 0) {
                long startMain = place.getStart().getTime();
                long endMain = place.getEnd().getTime();
                if (startMain <= aEnd && endMain >= aStart) flag = true;
            }
        }
        return flag;
    }

    private boolean checkCrossingWithMainJobs(byte type, Date start, Date end, Integer id_user) {
        return !((type == 1) && isCrossingWithMainJobs(start, end, id_user));
    }

    private boolean checkDates(Date start, Date end) {
        if (end == null) {
            return true;
        } else if (start.after(end)) {
            return false;
        }
        return true;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public Integer getId_org() {
        return id_org;
    }

    public void setId_org(Integer id_org) {
        this.id_org = id_org;
    }

    public Integer getId_job() {
        return id_job;
    }

    public void setId_job(Integer id_job) {
        this.id_job = id_job;
    }

    public Integer getId_pos() {
        return id_pos;
    }

    public void setId_pos(Integer id_pos) {
        this.id_pos = id_pos;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public List<Position> getPositions() {
        return positions;
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

    public List getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "JobAction{"
                + "id_user='" + id_user + '\''
                + ", id_org='" + id_org + '\''
                + ", id_job='" + id_job + '\''
                + ", id_pos='" + id_pos + '\''
                + ", start='" + start + '\''
                + ", end='" + end + '\''
                + '}';
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
