package task.controller;

import com.opensymphony.xwork2.ActionSupport;
import task.service.Manager;

public class DeleteUserAction extends ActionSupport {

    private Integer id_user;
    private Manager manager;

    public String deleteUser() {
        manager.deleteUserByID(id_user);
        return SUCCESS;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
