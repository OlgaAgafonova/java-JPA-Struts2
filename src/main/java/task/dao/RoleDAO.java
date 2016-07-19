package task.dao;

import task.entity.Role;

import java.util.List;

public interface RoleDAO {

    void addRole(Role role);

    List getAllRoles();

    Role getRoleByName(String name);

    Role getRoleById(Integer id);

    void deleteRoleByID(Integer roleId);
}
