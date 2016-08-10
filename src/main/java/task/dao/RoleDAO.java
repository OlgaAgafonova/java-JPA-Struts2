package task.dao;

import task.entity.Role;

import java.util.List;

public interface RoleDAO {

    void save(Role role);

    List<Role> getAllRoles();

    Role getRoleById(Integer id);

    void deleteByID(Integer id);
}
