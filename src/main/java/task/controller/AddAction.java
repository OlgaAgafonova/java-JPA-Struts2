package task.controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import task.entity.Role;
import task.entity.User;
import task.service.Manager;

import java.util.*;

public class AddAction extends ActionSupport implements Preparable {

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
        System.out.println("AddAction index");
        rolesList = manager.getAllRoles();
        setFormFields();
        return SUCCESS;
    }

    @Validations(
            requiredFields = {
                    @RequiredFieldValidator(fieldName = "roles", message = "You must select a role."),
                    @RequiredFieldValidator(fieldName = "firstname", message = "You must enter a first name.", key = "First name"),
                    @RequiredFieldValidator(fieldName = "lastname", message = "You must enter a last name."),
                    @RequiredFieldValidator(fieldName = "login", message = "You must enter a login."),
                    @RequiredFieldValidator(fieldName = "email", message = "You must enter an email address.")}

    )
    public String addUser() {
        System.out.println("AddAction addUser");
        System.out.println(new Date());
        System.out.println(toString()); System.out.println("AddAction index");
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
        //manager.save(user);
        return SUCCESS;
    }

    private void setFormFields() {
        System.out.println("AddAction setFormFields");
        if (id != null && !id.trim().isEmpty()) {
            user = manager.getUserByID(Integer.valueOf(id));
            firstname = user.getFirstname();
            lastname = user.getLastname();
            login = user.getLogin();
            email = user.getEmail();
        }
    }

    @Override
    public void prepare() throws Exception {
        System.out.println("AddAction prepare()");
        rolesList = manager.getAllRoles();
        setFormFields();
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
