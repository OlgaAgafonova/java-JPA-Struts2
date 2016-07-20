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

    public Organization() {
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Organization{"
                + "name='" + name + '\''
                + '}';
    }
}
