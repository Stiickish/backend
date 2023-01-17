package facades;

import dtos.FestivalDTO;
import dtos.GuestDTO;
import dtos.PerformanceDTO;
import entities.Performance;

import java.util.List;

public interface IFacade<T> {

    List<T> getAllShows();

    List<T> getAssignedShow(String name);

    List<T> getAllGuests();

    T signUserToShow(String name, String guestName);

    T createNewPerformance(PerformanceDTO performanceDTO);

    T createNewFestival(FestivalDTO festivalDTO);

    T createNewGuest(GuestDTO guestDTO);



}
