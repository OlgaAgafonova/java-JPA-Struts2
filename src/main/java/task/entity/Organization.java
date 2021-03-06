package task.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "ORGANIZATION")
public class Organization implements Serializable{

    @Id
    @Column(name = "ID_ORGANIZATION")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_ADDRESS")
    private Address address;

    @Id
    @Column(name = "ds")
    private Date editStart;

    @Column(name = "de")
    private Date editEnd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getEditStart() {
        return editStart;
    }

    public void setEditStart(Date editStart) {
        this.editStart = editStart;
    }

    public Date getEditEnd() {
        return editEnd;
    }

    public void setEditEnd(Date editEnd) {
        this.editEnd = editEnd;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", editStart=" + editStart +
                ", editEnd=" + editEnd +
                '}';
    }
}
