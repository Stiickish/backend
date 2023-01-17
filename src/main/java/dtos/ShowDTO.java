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
public class ShowDTO implements Serializable {
    private final Integer id;
    @Size(max = 45)
    @NotNull
    private final String name;
    @Size(max = 45)
    @NotNull
    private final String phone;
    @Size(max = 45)
    @NotNull
    private final String email;
    @Size(max = 45)
    @NotNull
    private final String status;

    public ShowDTO(Integer id, String name, String phone, String email, String status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

    public ShowDTO(Guest guest) {
        this.id = guest.getId();
        this.name = guest.getName();
        this.phone = guest.getPhone();
        this.email = guest.getEmail();
        this.status = guest.getStatus();
    }

    public static List<ShowDTO> getDTOs(List<Guest> guests) {
        List<ShowDTO> guestDTOList = new ArrayList<>();
        guests.forEach(guest -> {
            guestDTOList.add(new ShowDTO(guest));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowDTO entity = (ShowDTO) o;
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