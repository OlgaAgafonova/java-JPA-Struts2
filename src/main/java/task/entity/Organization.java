package task.entity;

import javax.persistence.*;

@Entity
@Table(name = "ORGANIZATION")
public class Organization {

    @Id
    @Column(name = "ID_ORGANIZATION")
    @GeneratedValue
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_ADDRESS")
    private Address address;

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

    @Override
    public String toString() {
        return "Organization{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", address=" + address
                + '}';
    }
}
