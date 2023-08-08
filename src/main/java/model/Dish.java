package model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "dish")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "dish_name", nullable = false, length = 500)
    private String dishName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id", nullable = false)
    private DishType type;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "date_creat")
    private LocalDateTime dateCreat;

    @Column(name = "flag", nullable = false)
    private Integer flag;

    @Column(name = "dish_comment", length = 1000)
    private String dishComment;

    @Column(name = "date_update")
    private LocalDateTime dateUpdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public DishType getType() {
        return type;
    }

    public void setType(DishType type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getDateCreat() {
        return dateCreat;
    }

    public void setDateCreat(LocalDateTime dateCreat) {
        this.dateCreat = dateCreat;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getDishComment() {
        return dishComment;
    }

    public void setDishComment(String dishComment) {
        this.dishComment = dishComment;
    }

    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

}