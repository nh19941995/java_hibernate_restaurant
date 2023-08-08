package model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings_info")
public class BookingsInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "info", nullable = false, length = 500)
    private String info;

    @Column(name = "deposit")
    private Double deposit;

    @Column(name = "date_creat", nullable = false)
    private LocalDateTime dateCreat;

    @Column(name = "start", nullable = false)
    private LocalDateTime start;

    @Column(name = "end", nullable = false)
    private LocalDateTime end;

    @Column(name = "flag")
    private Integer flag;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public LocalDateTime getDateCreat() {
        return dateCreat;
    }

    public void setDateCreat(LocalDateTime dateCreat) {
        this.dateCreat = dateCreat;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}