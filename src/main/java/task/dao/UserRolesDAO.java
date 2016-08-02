package task.dao;

import task.entity.UserRoles;

public interface UserRolesDAO {

    void save(UserRoles userRoles);

    void deleteUserRoleByID(UserRoles userRoles);

    void deleteUserRoles(Integer userId);
}
