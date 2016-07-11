package task.controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.apache.log4j.Logger;
import task.entity.Role;
import task.entity.User;
import task.service.UserManager;

import java.util.List;

/**
 * Created by Оля on 09.07.2016.
 */
public class EditUserAction extends ActionSupport implements Preparable {

    private static final Logger logger = Logger.getLogger(EditUserAction.class);

    private List<User> users;
    private List<Role> roles;

    private User user;

    private Role role;

    private UserManager userManager;

    public String addUser() {
        userManager.addUser(user, role);
        return SUCCESS;
    }

    public String listUsers() {
        users = userManager.getAllUsers();
        return SUCCESS;
    }

    public String deleteUser() {
        userManager.deleteUserByID(user.getId());
        return SUCCESS;
    }

    public String getRolesOfUser(Integer userId){
        return userManager.getUserRoles(userId).toString();
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
}
