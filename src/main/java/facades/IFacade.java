package facades;

import java.util.List;

public interface IFacade<T> {

    List<T> getAllShows();

    List<T>getSpecificShow(String name);

    List<T>getGuest();


}
