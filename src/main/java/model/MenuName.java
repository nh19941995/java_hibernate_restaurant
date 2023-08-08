package model;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "menu_name")
public class MenuName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 500)
    private String name;

    @Column(name = "date_creat")
    private LocalDateTime dateCreat;

    @Column(name = "date_update")
    private Instant dateUpdate;

    @Column(name = "flag", nullable = false)
    private Integer flag;

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

    public LocalDateTime getDateCreat() {
        return dateCreat;
    }

    public void setDateCreat(LocalDateTime dateCreat) {
        this.dateCreat = dateCreat;
    }

    public Instant getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Instant dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}