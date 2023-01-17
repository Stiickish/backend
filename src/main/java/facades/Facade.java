package facades;

import dtos.ShowDTO;
import entities.Guest;
import entities.Show;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class Facade implements IFacade {

    public static EntityManagerFactory emf;

    private static EntityManager em;

    private static Facade instance;

    public Facade() {
    }

    public static Facade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new Facade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public List<ShowDTO> getAllShows() {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<Show> query = em.createQuery("SELECT s FROM Show s", Show.class);
            List<Show> shows = query.getResultList();
            return ShowDTO.getDTOs(shows);
        } finally {
            em.close();
        }

    }

    @Override
    public List<ShowDTO> getAssignedShow(String name) {
        List<ShowDTO> showDTOList = new ArrayList<>();
        EntityManager em = getEntityManager();
        TypedQuery<Show> query = em.createQuery("SELECT s FROM Show s JOIN s.guests g WHERE g.name=:name", Show.class);
        query.setParameter("name", name);
        query.getResultList().forEach(show -> {
            showDTOList.add(new ShowDTO(show));
        });
        return showDTOList;

    }

    @Override
    public List getGuest() {
        return null;
    }
}
