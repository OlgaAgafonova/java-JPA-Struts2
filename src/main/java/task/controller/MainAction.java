package task.controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import task.entity.Role;
import task.entity.User;
import task.service.Manager;
import task.service.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainAction extends ActionSupport implements Preparable {

    private List users;
    private List roles;

    private User user;
    private Role role;

    private Integer userId;
    private String[] changedRoles;

    private Manager manager;

    public String index() {
        roles = manager.getAllRoles();
        return SUCCESS;
    }

    public String showUsers() {
        users = manager.getAllUsers();
        Utils.toResponse(users, "jsonRoot");
        return SUCCESS;
    }

    public String getAllRoles() {
        roles = manager.getAllRoles();
        Utils.toResponse(roles, "jsonAllRoles");
        return SUCCESS;
    }

    public String getRolesOfUser() {
        users = manager.getAllUsers();
        user = manager.getUserByID(userId);
        List<Object> rolesList = new ArrayList<>();
        rolesList.addAll(user.getRoles());
        Utils.toResponse(rolesList, "jsonUserRoles");
        return SUCCESS;
    }

    public String editUser() {
        user = manager.getUserByID(userId);
        Set<Role> newRolesOfUser = new HashSet<>();
        for (String changedRole : changedRoles) {
            Integer roleId = Integer.valueOf(changedRole);
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
