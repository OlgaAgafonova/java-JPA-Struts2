package task.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "JOB_PLACE")
public class JobPlace {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @Column(name = "START_DATE")
    private Date start;

    @Column(name = "END_DATE")
    private Date end;

    @Column(name = "ID_USER")
    private Integer id_user;

    @Column(name = "ID_ORGANIZATION")
    private Integer id_organization;

    @Column(name = "ID_POSITION")
    private Integer id_position;

    public JobPlace() {
    }

    public Integer getId_position() {
        return id_position;
    }

    public void setId_position(Integer id_position) {
        this.id_position = id_position;
    }

    public Integer getId_organization() {
        return id_organization;
    }

    public void setId_organization(Integer id_organization) {
        this.id_organization = id_organization;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "JobPlace{"
                + "start=" + start
                + ", end=" + end
                + ", id_user=" + id_user
                + ", id_organization=" + id_organization
                + ", id_position=" + id_position
                + '}';
    }
}
