package task.dao;

import task.entity.Form;

import java.util.List;

public interface FormDAO {

    void save(Form form);

    List<Form> getAll();

    Form getFormByID(Integer id);

    List<Form> getFormsByOrganizationID(Integer orgId);

    void deleteByID(Integer id);
}
