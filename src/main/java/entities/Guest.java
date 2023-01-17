package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "guest")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Size(max = 45)
    @NotNull
    @Column(name = "phone", nullable = false, length = 45)
    private String phone;

    @Size(max = 45)
    @NotNull
    @Column(name = "email", nullable = false, length = 45)
    private String email;

    @Size(max = 45)
    @NotNull
    @Column(name = "status", nullable = false, length = 45)
    private String status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "festival_id", nullable = false)
    private Festival festival;

    @ManyToMany
    @JoinTable(name = "performance_has_guest",
            joinColumns = @JoinColumn(name = "guest_id"),
            inverseJoinColumns = @JoinColumn(name = "show_id"))
    private List<Performance> shows = new ArrayList<>();

    public Guest() {
    }

    public Guest(Integer id, String name, String phone, String email, String status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

    public Guest(String name, String phone, String email, String status) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

    //Husk at bryde loopet
    public void addShow(Performance show) {
        this.shows.add(show);
        if (!show.getGuests().contains(this)) {
            show.addGuest(this);
        }
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
        festival.getGuests().add(this);
    }

    public List<Performance> getShows() {
        return shows;
    }

    public void setShows(List<Performance> shows) {
        this.shows = shows;
    }
}