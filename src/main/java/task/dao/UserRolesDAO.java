package task.dao;

import task.entity.UserRoles;

import java.util.List;

public interface UserRolesDAO {

    void save(UserRoles userRoles);

    List getAllUserRoles();

    List getUserRoles(Integer userId);

    void deleteUserRoleByID(UserRoles userRoles);

    void deleteUserRoles(Integer userId);
}
