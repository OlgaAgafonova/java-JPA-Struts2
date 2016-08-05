package task.controller;

import com.opensymphony.xwork2.ActionSupport;
import task.entity.Role;
import task.entity.User;
import task.service.Manager;

import java.util.*;

public class AddUserAction extends ActionSupport {

    private Integer id_user;
    private String firstname;
    private String lastname;
    private String login;
    private String email;
    private String[] roles;

    private List<Role> rolesList;

    private User user;
    private Manager manager;

    public String index() {
        rolesList = manager.getAllRoles();
        setFormFields();
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
        for (String role1 : roles) {
            role = manager.getRoleByID(Integer.valueOf(role1));
            roleSet.add(role);
        }
        user.setRoles(roleSet);
        if (id_user != null) {
            user.setId(id_user);
        }
        manager.save(user);
        return SUCCESS;
    }

    private void setFormFields() {
        if (id_user != null) {
            user = manager.getUserByID(id_user);
            firstname = user.getFirstname();
            lastname = user.getLastname();
            login = user.getLogin();
            email = user.getEmail();
        }
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
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

    public List<Role> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<Role> rolesList) {
        this.rolesList = rolesList;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "AddUserAction{"
                + "firstname='" + firstname + '\''
                + ", lastname='" + lastname + '\''
                + ", login='" + login + '\''
                + ", email='" + email + '\''
                + ", roles=" + Arrays.toString(roles)
                + '}';
    }
}
