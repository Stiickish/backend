package dtos;

import entities.Performance;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link Performance} entity
 */
public class PerformanceDTO implements Serializable {
    private Integer id;
    @Size(max = 45)
    @NotNull
    private String name;
    @Size(max = 45)
    @NotNull
    private String duration;
    @Size(max = 45)
    @NotNull
    private String location;
    @Size(max = 45)
    @NotNull
    private String startDate;
    @Size(max = 45)
    @NotNull
    private String startTime;

    public PerformanceDTO(Integer id, String name, String duration, String location, String startDate, String startTime) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.location = location;
        this.startDate = startDate;
        this.startTime = startTime;
    }

    public PerformanceDTO(Performance show) {
        this.id = show.getId();
        this.name = show.getName();
        this.duration = show.getDuration();
        this.location = show.getLocation();
        this.startDate = show.getStartDate();
        this.startTime = show.getStartTime();
    }


    public static List<PerformanceDTO> getDTOs(List<Performance> shows) {
        List<PerformanceDTO> showDTOList = new ArrayList<>();
        shows.forEach(show -> {
            showDTOList.add(new PerformanceDTO(show));
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerformanceDTO entity = (PerformanceDTO) o;
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