package task.dao;

import task.entity.UserRoles;

import java.util.List;

public interface UserRolesDAO {

    void addUserRole(UserRoles userRoles);

    List getAllUserRoles();

    List getUserRoles(Integer userId);

    void deleteUserRoleByID(UserRoles userRoles);

    void deleteUserRoles(Integer userId);
}
