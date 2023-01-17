package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@NamedQuery(name="Festival.deleteAllRows",query = "DELETE FROM Festival")
@Table(name = "festival")
public class Festival {
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
    @Column(name = "city", nullable = false, length = 45)
    private String city;

    @Size(max = 45)
    @NotNull
    @Column(name = "startDate", nullable = false, length = 45)
    private String startDate;

    @Size(max = 45)
    @NotNull
    @Column(name = "duration", nullable = false, length = 45)
    private String duration;

    @OneToMany(mappedBy = "festival")
    private List<Guest> guests = new ArrayList<>();


    public Festival() {
    }

    public Festival(String name, String city, String startDate, String duration) {
        this.name = name;
        this.city = city;
        this.startDate = startDate;
        this.duration = duration;
    }

    public Festival(Integer id, String name, String city, String startDate, String duration) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.startDate = startDate;
        this.duration = duration;
    }
//Husk at bryde loopet
    public void addGuest(Guest guest) {
        this.guests.add(guest);
        if (!guest.getFestival().equals(this)) {
            guest.setFestival(this);
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Festival)) return false;
        Festival festival = (Festival) o;
        return Objects.equals(getId(), festival.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}