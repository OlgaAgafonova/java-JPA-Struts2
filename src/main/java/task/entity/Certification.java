package task.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CERTIFICATION")
public class Certification {

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

    @Override
    public String toString() {
        return "Certification{" +
                "id=" + id +
                ", idOrg=" + idOrg +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
