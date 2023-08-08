package model;

import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "info_id", nullable = false)
    private BookingsInfo info;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_name_id", nullable = false)
    private MenuName menuName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "table_id", nullable = false)
    private TableList table;

    @Column(name = "flag")
    private Integer flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BookingsInfo getInfo() {
        return info;
    }

    public void setInfo(BookingsInfo info) {
        this.info = info;
    }

    public MenuName getMenuName() {
        return menuName;
    }

    public void setMenuName(MenuName menuName) {
        this.menuName = menuName;
    }

    public TableList getTable() {
        return table;
    }

    public void setTable(TableList table) {
        this.table = table;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}