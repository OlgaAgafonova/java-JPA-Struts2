package task.dao;

import task.entity.UserRoles;

import java.util.List;

/**
 * Created by Оля on 09.07.2016.
 */
public interface UserRolesDAO {

    public void addUserRole(UserRoles userRoles);

    public List getAllUserRoles();

    public List getUserRoles(Integer userId);

    public void deleteUserRoleByID(UserRoles userRoles);

    public void deleteUserRoles(Integer userId);
}
