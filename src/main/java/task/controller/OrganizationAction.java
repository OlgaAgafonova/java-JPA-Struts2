package task.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import task.entity.Organization;
import task.service.UserManager;

import java.util.List;

public class OrganizationAction extends ActionSupport {

    private UserManager userManager;
    private List<Organization> organizations;

    public String showOrgs() {
        organizations = userManager.getAllOrganizations();
        DataTableResponse tableResponse = new DataTableResponse();
        tableResponse.setAaData(organizations);
        tableResponse.setiTotalRecords(organizations.size());
        tableResponse.setiTotalDisplayRecords(organizations.size());
        tableResponse.setsEcho(0);
        ActionContext.getContext().put("jsonOrg", tableResponse);
        return SUCCESS;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
}
