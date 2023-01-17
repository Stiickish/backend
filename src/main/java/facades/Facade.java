package facades;

import dtos.PerformanceDTO;
import entities.Performance;

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
    public List<PerformanceDTO> getAllShows() {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<Performance> query = em.createQuery("SELECT s FROM Performance s", Performance.class);
            List<Performance> shows = query.getResultList();
            return PerformanceDTO.getDTOs(shows);
        } finally {
            em.close();
        }

    }

    @Override
    public List<PerformanceDTO> getAssignedShow(String name) {
        List<PerformanceDTO> showDTOList = new ArrayList<>();
        EntityManager em = getEntityManager();
        TypedQuery<Performance> query = em.createQuery("SELECT s FROM Performance s JOIN s.guests g WHERE g.name=:name", Performance.class);
        query.setParameter("name", name);
        query.getResultList().forEach(show -> {
            showDTOList.add(new PerformanceDTO(show));
        });
        return showDTOList;

    }

    @Override
    public List getGuest() {
        return null;
    }
}
