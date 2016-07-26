package task.controller;

import com.opensymphony.xwork2.ActionSupport;
import task.entity.Role;
import task.entity.User;
import task.service.Manager;

import java.util.*;

public class AddAction extends ActionSupport {

    private String id;
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
        if (validation()) {
            return ERROR;
        }
        System.out.println(new Date());
        System.out.println(toString());
        user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setLogin(login);
        Role role;
        Set<Role> roleSet = new HashSet<>();
        for (int i = 0; i < roles.length; i++) {
            role = manager.getRoleByID(Integer.valueOf(roles[i]));
            roleSet.add(role);
        }
        user.setRoles(roleSet);
        if (id != null && !id.trim().isEmpty()) {
            user.setId(Integer.valueOf(id));
        }
        manager.save(user);
        return SUCCESS;
    }

    private boolean validation() {
        if (isStringEmpty(firstname)) {
            return true;
        }
        if (isStringEmpty(lastname)) {
            return true;
        }
        if (isStringEmpty(login)) {
            return true;
        }
        if (isStringEmpty(email)) {
            return true;
        }
        if (roles == null) {
            return true;
        }
        return false;
    }

    private boolean isStringEmpty(String string) {
        if (string == null || string.trim().isEmpty()) {
            return true;
        }
        return false;
    }

    /*    public void validate() {
        if (firstname == null || firstname.trim().isEmpty()) {
            addFieldError("firstname", "First name must be not empty");
        }
        if (lastname == null || lastname.trim().isEmpty()) {
            addFieldError("lastname", "Last name must be not empty");
        }
        if (login == null || login.trim().isEmpty()) {
            addFieldError("login", "Login must be not empty");
        }
        if (email == null || email.trim().isEmpty()) {
            addFieldError("email", "Email must be not empty");
        }
        if (roles == null) {
            addFieldError("roles", "You should select user's role");
        }
    }*/

    private void setFormFields() {
        if (id != null && !id.trim().isEmpty()) {
            user = manager.getUserByID(Integer.valueOf(id));
            firstname = user.getFirstname();
            lastname = user.getLastname();
            login = user.getLogin();
            email = user.getEmail();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "AddAction{"
                + "firstname='" + firstname + '\''
                + ", lastname='" + lastname + '\''
                + ", login='" + login + '\''
                + ", email='" + email + '\''
                + ", roles=" + Arrays.toString(roles)
                + '}';
    }
}
