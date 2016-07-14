package task.controller;

import com.opensymphony.xwork2.ActionSupport;
import task.entity.User;
import task.service.UserManager;

import java.util.Arrays;

/**
 * Created by Оля on 12.07.2016.
 */
public class AddAction extends ActionSupport {

    private String
            firstname,
            lastname,
            login,
            email;
    private String[] roles;

    private User user;
    private UserManager userManager;

    public String addUser() {
        System.out.println("ADD USER");
        System.out.println(toString());
        System.out.println("roles.length = "+roles.length);

        /*user = new User();
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

        System.out.println(user);
        userManager.addUser(user);*/
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

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
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
                ", roles=" + Arrays.toString(roles) +
                '}';
    }
}
