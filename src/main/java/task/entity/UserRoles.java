package task.entity;

import javax.persistence.*;

/**
 * Created by Оля on 09.07.2016.
 */
@Entity
@Table(name = "USER_ROLES")
public class UserRoles {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @Column(name = "ID_USER")
    private Integer id_user;

    @Column(name = "ID_ROLE")
    private Integer id_role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public Integer getId_role() {
        return id_role;
    }

    public void setId_role(Integer id_role) {
        this.id_role = id_role;
    }
}
