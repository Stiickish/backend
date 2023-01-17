package facades;

import dtos.ShowDTO;
import entities.Show;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
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
    public List getSpecificShow(String name) {
        return null;
    }

    @Override
    public List getGuest() {
        return null;
    }
}
