package dtos;

import entities.Festival;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link entities.Festival} entity
 */
public class FestivalDTO implements Serializable {
    private final Integer id;
    @Size(max = 45)
    @NotNull
    private final String name;
    @Size(max = 45)
    @NotNull
    private final String city;
    @Size(max = 45)
    @NotNull
    private final String startDate;
    @Size(max = 45)
    @NotNull
    private final String duration;

    public FestivalDTO(Integer id, String name, String city, String startDate, String duration) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.startDate = startDate;
        this.duration = duration;
    }

    public FestivalDTO(Festival festival) {
        this.id = festival.getId();
        this.name = festival.getName();
        this.city = festival.getCity();
        this.startDate = festival.getStartDate();
        this.duration = festival.getDuration();
    }

    public static List<FestivalDTO> getDTOs(List<Festival> festivals) {
        List<FestivalDTO> festivalDTOList = new ArrayList<>();
        festivals.forEach(festival -> {
            festivalDTOList.add(new FestivalDTO(festival));
        });
        return festivalDTOList;
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FestivalDTO entity = (FestivalDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.city, entity.city) &&
                Objects.equals(this.startDate, entity.startDate) &&
                Objects.equals(this.duration, entity.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, city, startDate, duration);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "city = " + city + ", " +
                "startDate = " + startDate + ", " +
                "duration = " + duration + ")";
    }
}