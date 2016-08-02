package task.dao;

import task.entity.Certification;

import java.util.List;

public interface CertificationDAO {

    void save(Certification certification);

    Byte getCurrentStatusByOrganizationID(Integer orgId);

    List getCertificationsByOrganizationID(Integer orgId);
}
