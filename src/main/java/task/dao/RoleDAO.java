package task.dao;

import task.entity.Role;

import java.util.List;

/**
 * Created by Оля on 09.07.2016.
 */
public interface RoleDAO {

    public void addRole(Role role);

    public List getAllRoles();

    public Role getRoleByName(String name);

    public Role getRoleById(Integer id);

    public void deleteRoleByID(Integer roleId);
}
