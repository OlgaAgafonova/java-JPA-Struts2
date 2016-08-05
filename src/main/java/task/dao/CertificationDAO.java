package task.dao;

import task.entity.Certification;
import task.entity.CertificationView;

import java.io.File;
import java.util.List;

public interface CertificationDAO {

    void save(Certification certification);

    void addDocument(Integer orgId, String filename, File file);

    CertificationView getCurrentCertificationByOrganizationID(Integer orgId);

    List<CertificationView> getCertificationsByOrganizationID(Integer orgId);

    void certify(Integer orgId);

    void refuse(Integer orgId);

    void remove(Integer orgId);

    void deleteByID(Integer id);
}
