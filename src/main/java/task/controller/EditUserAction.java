package task.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import task.entity.Role;
import task.entity.User;
import task.service.Manager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EditUserAction extends ActionSupport implements Preparable {

    private List<User> users;
    private List<Role> roles;

    private User user;
    private Role role;

    private Integer userId;
    private String[] changedRoles;

    private Manager manager;

    public String index() {
        return SUCCESS;
    }

    public String listUsers() {
        users = manager.getAllUsers();
        DataTableResponse tableResponse = new DataTableResponse();
        tableResponse.setAaData(users);
        tableResponse.setiTotalRecords(users.size());
        tableResponse.setiTotalDisplayRecords(users.size());
        tableResponse.setsEcho(0);
        ActionContext.getContext().put("jsonRoot", tableResponse);
        return SUCCESS;
    }

    public String deleteUser() {
        manager.deleteUserByID(userId);
        return SUCCESS;
    }

    public String editRolesOfUser() {
        DataTableResponse tableResponse = new DataTableResponse();
        roles = manager.getRoles();
        tableResponse.setAaData(roles);
        tableResponse.setiTotalRecords(roles.size());
        tableResponse.setiTotalDisplayRecords(roles.size());
        tableResponse.setsEcho(0);
        ActionContext.getContext().put("jsonAllRoles", tableResponse);
        return SUCCESS;
    }

    public String getRolesOfUser() {
        users = manager.getAllUsers();
        DataTableResponse tableResponse = new DataTableResponse();
        user = manager.getUserByID(userId);
        List<Object> rolesList = Arrays.asList(user.getRoles().toArray());
        tableResponse.setAaData(rolesList);
        tableResponse.setiTotalRecords(rolesList.size());
        tableResponse.setiTotalDisplayRecords(rolesList.size());
        tableResponse.setsEcho(0);
        ActionContext.getContext().put("jsonUserRoles", tableResponse);
        return SUCCESS;
    }

    public String editUser() {
        user = manager.getUserByID(userId);
        Set<Role> newRolesOfUser = new HashSet<>();
        for (int i = 0; i < changedRoles.length; i++) {
            Integer roleId = Integer.valueOf(changedRoles[i]);
            role = manager.getRoleByID(roleId);
            newRolesOfUser.add(role);
        }
        user.setRoles(newRolesOfUser);
        manager.save(user);
        return SUCCESS;
    }

    @Override
    public void prepare() throws Exception {
        user = null;
        role = null;
        roles = manager.getRoles();
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String[] getChangedRoles() {
        return changedRoles;
    }

    public void setChangedRoles(String[] changedRoles) {
        this.changedRoles = changedRoles;
    }
}
