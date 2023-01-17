package facades;

import dtos.FestivalDTO;
import dtos.GuestDTO;
import dtos.PerformanceDTO;

import java.util.List;

public interface IFacade<T> {

    List<T> getAllShows();

    List<T> getAssignedShow(String name);

    List<T> getAllGuests();

    T signGuestToShow(String name, String guestName);

    T createNewPerformance(PerformanceDTO performanceDTO);

    T createNewFestival(FestivalDTO festivalDTO);

    T createNewGuest(GuestDTO guestDTO);

    T deleteAPerformance(Integer id);

    T updateFestival(FestivalDTO festivalDTO);
    T updateGuest(GuestDTO guestDTO);
    T updatePerformance(PerformanceDTO performanceDTO);





}
