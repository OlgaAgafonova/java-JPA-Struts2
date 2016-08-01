package task.controller;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import task.entity.Role;
import task.entity.User;
import task.service.Manager;
import task.service.Utils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainAction extends ActionSupport {

    private User user;

    private Integer id_user;
    private String[] changedRoles;

    private Manager manager;

    public String index() {
        return SUCCESS;
    }

    public String showUsers() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Integer iDisplayStart = Integer.valueOf(request.getParameterMap().get("iDisplayStart")[0]);
        Integer iDisplayLength = Integer.valueOf(request.getParameterMap().get("iDisplayLength")[0]);

        List users = manager.getUsers(iDisplayStart, iDisplayLength);
        int totalSize = manager.getTotalCountOfUsers().intValue();
        Utils.toResponse(users, "jsonRoot", totalSize, totalSize);
        return SUCCESS;
    }

    public String getAllRoles() {
        List roles = manager.getAllRoles();
        Utils.toResponse(roles, "jsonAllRoles");
        return SUCCESS;
    }

    public String getRolesOfUser() {
        user = manager.getUserByID(id_user);
        List<Object> rolesList = new ArrayList<>();
        rolesList.addAll(user.getRoles());
        Utils.toResponse(rolesList, "jsonUserRoles");
        return SUCCESS;
    }

    public String editUser() {
        if (id_user != null) {
            user = manager.getUserByID(id_user);
            Set<Role> newRolesOfUser = new HashSet<>();
            for (String changedRole : changedRoles) {
                Integer roleId = Integer.valueOf(changedRole);
                Role role = manager.getRoleByID(roleId);
                newRolesOfUser.add(role);
            }
            user.setRoles(newRolesOfUser);
            manager.save(user);
        }
        return SUCCESS;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public void setChangedRoles(String[] changedRoles) {
        this.changedRoles = changedRoles;
    }
}
