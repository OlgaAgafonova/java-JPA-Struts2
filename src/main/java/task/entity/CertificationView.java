package task.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "certification_view")
public class CertificationView {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @Column(name = "ID_ORG")
    private Integer idOrg;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE")
    private Date date;

    @Column(name = "STATUS", columnDefinition = "TINYINT")
    private byte status;

    @Column(name = "DOCUMENT_NAME")
    private String filename;

    @Column(name = "isLast")
    private Integer isLast;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdOrg() {
        return idOrg;
    }

    public void setIdOrg(Integer idOrg) {
        this.idOrg = idOrg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getIsLast() {
        return isLast;
    }

    public void setIsLast(Integer isLast) {
        this.isLast = isLast;
    }

    @Override
    public String toString() {
        return "CertificationView{" +
                "id=" + id +
                ", idOrg=" + idOrg +
                ", date=" + date +
                ", status=" + status +
                ", filename='" + filename + '\'' +
                ", isLast=" + isLast +
                '}';
    }
}
