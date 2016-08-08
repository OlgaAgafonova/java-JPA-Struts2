package task.dao;

import org.apache.commons.io.FileUtils;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.engine.jdbc.LobCreator;
import task.entity.Certification;
import task.entity.CertificationView;
import task.enums.CertificationStatus;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

public class CertificationDAOImpl implements CertificationDAO {

    private SessionFactory sessionFactory;

    @Override
    public void save(Certification certification) {
        getCurrentSession().save(certification);
    }

    @Override
    public void addDocument(Integer orgId, String filename, File file) {
        Certification certification = new Certification();

        try {
            byte[] bytes = FileUtils.readFileToByteArray(file);
            LobCreator lobCreator = Hibernate.getLobCreator(getCurrentSession());
            Blob document = lobCreator.createBlob(bytes);

            certification.setIdOrg(orgId);
            certification.setDate(new Date());
            certification.setFilename(filename);
            certification.setDocument(document);
            certification.setStatus(CertificationStatus.CONSIDERED.getValue());
            save(certification);
        } catch (IOException ignored) {
        }
    }

    @Override
    public CertificationView getCurrentCertificationByOrganizationID(Integer orgId) {
        List<CertificationView> certifications = getCertificationsByOrganizationID(orgId);
        int size = certifications.size();
        return certifications.get(size - 1);
    }

    @Override
    public List<CertificationView> getCertificationsByOrganizationID(Integer orgId) {
        return (List<CertificationView>) getCurrentSession()
                .createQuery("from CertificationView where idOrg = ? order by date")
                .setInteger(0, orgId)
                .list();
    }

    @Override
    public Certification getCertificationByID(Integer id) {
        return (Certification) getCurrentSession()
                .get(Certification.class, id);
    }

    @Override
    public void certify(Integer orgId) {
        Certification certification = new Certification();
        certification.setIdOrg(orgId);
        certification.setDate(new Date());
        certification.setStatus(CertificationStatus.CERTIFIED.getValue());
        save(certification);
    }

    @Override
    public void refuse(Integer orgId) {
        Certification certification = new Certification();
        certification.setIdOrg(orgId);
        certification.setDate(new Date());
        certification.setStatus(CertificationStatus.REFUSED.getValue());
        save(certification);
    }

    @Override
    public void remove(Integer orgId) {
        Certification certification = new Certification();
        certification.setIdOrg(orgId);
        certification.setDate(new Date());
        certification.setStatus(CertificationStatus.REMOVED.getValue());
        save(certification);
    }

    @Override
    public void deleteByID(Integer id) {
        Certification certification = getCertificationByID(id);
        if (certification != null) {
            getCurrentSession().delete(certification);
        }
    }

    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
