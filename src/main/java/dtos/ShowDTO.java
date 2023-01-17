package dtos;

import entities.Guest;
import entities.Show;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link entities.Show} entity
 */
public class ShowDTO implements Serializable {
    private final Integer id;
    @Size(max = 45)
    @NotNull
    private final String name;
    @Size(max = 45)
    @NotNull
    private final String duration;
    @Size(max = 45)
    @NotNull
    private final String location;
    @Size(max = 45)
    @NotNull
    private final String startDate;
    @Size(max = 45)
    @NotNull
    private final String startTime;

    public ShowDTO(Integer id, String name, String duration, String location, String startDate, String startTime) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.location = location;
        this.startDate = startDate;
        this.startTime = startTime;
    }

    public ShowDTO(Show show) {
        this.id = show.getId();
        this.name = show.getName();
        this.duration = show.getDuration();
        this.location = show.getLocation();
        this.startDate = show.getStartDate();
        this.startTime = show.getStartTime();
    }


    public static List<ShowDTO> getDTOs(List<Show> shows) {
        List<ShowDTO> showDTOList = new ArrayList<>();
        shows.forEach(show -> {
            showDTOList.add(new ShowDTO(show));
        });
        return showDTOList;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }

    public String getLocation() {
        return location;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowDTO entity = (ShowDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.duration, entity.duration) &&
                Objects.equals(this.location, entity.location) &&
                Objects.equals(this.startDate, entity.startDate) &&
                Objects.equals(this.startTime, entity.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration, location, startDate, startTime);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "duration = " + duration + ", " +
                "location = " + location + ", " +
                "startDate = " + startDate + ", " +
                "startTime = " + startTime + ")";
    }
}