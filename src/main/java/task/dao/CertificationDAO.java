package task.dao;

import task.entity.Certification;

import java.io.File;
import java.util.List;

public interface CertificationDAO {

    void save(Certification certification);

    void addDocument(Integer orgId, String filename, File file);

    Certification getCurrentCertificationByOrganizationID(Integer orgId);

    List<Certification> getCertificationsByOrganizationID(Integer orgId);

    void certify(Integer orgId);

    void refuse(Integer orgId);

    void remove(Integer orgId);
}
