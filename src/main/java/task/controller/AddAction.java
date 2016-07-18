package task.controller;

import com.opensymphony.xwork2.ActionSupport;
import task.entity.Role;
import task.entity.User;
import task.service.UserManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddAction extends ActionSupport {

    private String firstname;
    private String lastname;
    private String login;
    private String email;
    private String[] roles;


    private List<Role> rolesList;

    private User user;
    private UserManager userManager;

    public String index() {
        rolesList = userManager.getRoles();
        return SUCCESS;
    }

    public String addUser() {
        user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setLogin(login);
        Role role;
        Set<Role> roleSet = new HashSet<>();

        for (int i = 0; i < roles.length; i++) {
            role = userManager.getRoleByID(Integer.valueOf(roles[i]));
            roleSet.add(role);
        }
        user.setRoles(roleSet);

        userManager.save(user);
        return SUCCESS;
    }

/*    public void validate() {
        if (firstname.trim().isEmpty()) {
            addFieldError("firstname", "First name must be not empty");
        }
        if (lastname.trim().isEmpty()) {
            addFieldError("lastname", "Last name must be not empty");
        }
        if (login.trim().isEmpty()) {
            addFieldError("login", "Login must be not empty");
        }
        if (email.trim().isEmpty()) {
            addFieldError("email", "Email must be not empty");
        }
        if (roles == null) {
            addFieldError("roles", "You should select user's role");
        }
    }*/

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public List<Role> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<Role> rolesList) {
        this.rolesList = rolesList;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public String toString() {
        return "AddAction{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + Arrays.toString(roles) +
                '}';
    }
}
