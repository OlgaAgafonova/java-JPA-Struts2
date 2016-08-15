package task.entity;

import javax.persistence.*;
import java.sql.Date;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_USER")
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "org_id", referencedColumnName = "id_organization")
    private OrganizationView organization;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_POSITION")
    private Position position;

    @Column(name = "TYPE", columnDefinition = "TINYINT", nullable = false)
    private byte type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrganizationView getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationView organization) {
        this.organization = organization;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "JobPlace{"
                + "start=" + start
                + ", end=" + end
                + ", user=" + user
                + ", organization=" + organization
                + ", position=" + position
                + '}';
    }
}
