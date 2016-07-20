package task.dao;

import task.entity.Role;

import java.util.List;

public interface RoleDAO {

    void save(Role role);

    List getAllRoles();

    Role getRoleById(Integer id);

    void deleteRoleByID(Integer id);
}
