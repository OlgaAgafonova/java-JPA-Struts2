package task.dao;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import task.entity.Certification;

import java.util.Date;
import java.util.List;

public class CertificationDAOImpl implements CertificationDAO {

    private SessionFactory sessionFactory;

    @Override
    public void save(Certification certification) {
        getCurrentSession().save(certification);
    }

    @Override
    public Byte getCurrentStatusByOrganizationID(Integer orgId) {
        List<Certification> certifications = getCertificationsByOrganizationID(orgId);
        Date maxDate = certifications.get(0).getDate();
        Certification certification = certifications.get(0);
        for (Certification aCertification : certifications) {
            Date currDate = aCertification.getDate();
            if (maxDate.before(currDate)) {
                maxDate = currDate;
                certification = aCertification;
            }
        }
        return certification.getStatus();
    }

    @Override
    public List getCertificationsByOrganizationID(Integer orgId) {
        return getCurrentSession()
                .createQuery("from Certification where idOrg = ?")
                .setInteger(0, orgId)
                .list();
    }

    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
