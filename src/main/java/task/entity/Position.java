package task.entity;

import javax.persistence.*;

@Entity
@Table(name = "POSITION")
public class Position {

    @Id
    @Column(name = "ID_POSITION")
    @GeneratedValue
    private Integer id;

    @Column(name = "NAME")
    private String name;

    public Position() {
    }

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

    @Override
    public String toString() {
        return "Position{"
                + "name='" + name + '\''
                + '}';
    }
}
