package dtos;

import entities.Guest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link entities.Guest} entity
 */
public class GuestDTO implements Serializable {
    private Integer id;
    @Size(max = 45)
    @NotNull
    private String name;
    @Size(max = 45)
    @NotNull
    private String phone;
    @Size(max = 45)
    @NotNull
    private String email;
    @Size(max = 45)
    @NotNull
    private String status;

    public GuestDTO(Integer id, String name, String phone, String email, String status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

    public GuestDTO(Guest guest) {
        this.id = guest.getId();
        this.name = guest.getName();
        this.phone = guest.getPhone();
        this.email = guest.getEmail();
        this.status = guest.getStatus();
    }

    public static List<GuestDTO> getDTOs(List<Guest> guests) {
        List<GuestDTO> guestDTOList = new ArrayList<>();
        guests.forEach(guest -> {
            guestDTOList.add(new GuestDTO(guest));
        });
        return guestDTOList;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuestDTO entity = (GuestDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.phone, entity.phone) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.status, entity.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone, email, status);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "phone = " + phone + ", " +
                "email = " + email + ", " +
                "status = " + status + ")";
    }
}