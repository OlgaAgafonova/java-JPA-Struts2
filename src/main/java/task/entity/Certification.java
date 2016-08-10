package task.entity;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Date;

@Entity
@Table(name = "CERTIFICATION")
public class Certification {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @Column(name = "ID_ORG")
    private Integer idOrg;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "STATUS", columnDefinition = "TINYINT")
    private byte status;

    @Column(name="DOCUMENT_NAME")
    private String filename;

    @Column(name="DOCUMENT", columnDefinition = "LONGBLOB")
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private Blob document;

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

    public Blob getDocument() {
        return document;
    }

    public void setDocument(Blob document) {
        this.document = document;
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
