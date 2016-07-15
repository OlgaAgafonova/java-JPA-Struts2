package task.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import task.entity.Role;
import task.entity.User;
import task.service.UserManager;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Оля on 09.07.2016.
 */
public class EditUserAction extends ActionSupport implements Preparable {

    private List<User> users;
    private List<Role> roles;

    private User user;
    private Role role;

    private Integer userId;

    private UserManager userManager;

    public String index(){
        return SUCCESS;
    }


    public String listUsers() {
        users = userManager.getAllUsers();
        DataTableResponse tableResponse = new DataTableResponse();
        tableResponse.setAaData(users);
        tableResponse.setiTotalRecords(users.size());
        tableResponse.setiTotalDisplayRecords(users.size());
        tableResponse.setsEcho(0);
        ActionContext.getContext().put("jsonRoot", tableResponse);
        return SUCCESS;
    }

    public String deleteUser() {
        System.out.println(user.getId());
        userManager.deleteUserByID(user.getId());
        return SUCCESS;
    }

    public String editRolesOfUser(){
        DataTableResponse tableResponse = new DataTableResponse();
        roles = userManager.getRoles();
        tableResponse.setAaData(roles);
        tableResponse.setiTotalRecords(roles.size());
        tableResponse.setiTotalDisplayRecords(roles.size());
        tableResponse.setsEcho(0);
        ActionContext.getContext().put("jsonAllRoles", tableResponse);
        return SUCCESS;
    }

    public String getRolesOfUser(){
        users = userManager.getAllUsers();
        DataTableResponse tableResponse = new DataTableResponse();
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId() == userId){
                user = users.get(i);
                break;
            }
        }
        List<Object> rolesList = Arrays.asList(user.getRoles().toArray());
        tableResponse.setAaData(rolesList);
        tableResponse.setiTotalRecords(rolesList.size());
        tableResponse.setiTotalDisplayRecords(rolesList.size());
        tableResponse.setsEcho(0);
        ActionContext.getContext().put("jsonUserRoles", tableResponse);
        return SUCCESS;
    }

    @Override
    public void prepare() throws Exception {
        user = null;
        role = null;
        roles = userManager.getRoles();
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
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
}
