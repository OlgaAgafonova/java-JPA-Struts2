package task.controller;

import com.opensymphony.xwork2.ActionSupport;
import task.entity.Role;
import task.entity.User;
import task.service.UserManager;

import java.util.Set;

/**
 * Created by Оля on 12.07.2016.
 */
public class AddAction extends ActionSupport {

    private String
            firstname,
            lastname,
            login,
            email;
    private Set<Role> roles;

    private User user;
    private UserManager userManager;

    public String addUser(){
        System.out.println("ADD USER");
        System.out.println(toString());
        user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setLogin(login);
        user.setRoles(roles);
        System.out.println(user);
        userManager.addUser(user, roles);
        return SUCCESS;
    }

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
                ", roles=" + roles +
                '}';
    }
}
