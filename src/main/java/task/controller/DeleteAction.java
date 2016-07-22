package task.controller;

import com.opensymphony.xwork2.ActionSupport;
import task.service.Manager;

public class DeleteAction extends ActionSupport {

    private Integer userId;
    private Manager manager;

    public String deleteUser() {
        manager.deleteUserByID(userId);
        return SUCCESS;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
