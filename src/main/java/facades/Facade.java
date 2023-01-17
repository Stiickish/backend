package facades;

import dtos.FestivalDTO;
import dtos.GuestDTO;
import dtos.PerformanceDTO;
import entities.Festival;
import entities.Guest;
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
            TypedQuery<Performance> query = em.createQuery("SELECT p FROM Performance p", Performance.class);
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
        TypedQuery<Performance> query = em.createQuery("SELECT p FROM Performance p JOIN p.guests g WHERE g.name=:name", Performance.class);
        query.setParameter("name", name);
        query.getResultList().forEach(show -> {
            showDTOList.add(new PerformanceDTO(show));
        });
        return showDTOList;

    }

   /* @Override
    public List<PerformanceDTO> getAssignedShow(String name){
       TypedQuery<Guest> query = em.createQuery("SELECT g FROM Guest g WHERE g.name=:name",Guest.class);
       query.setParameter("name",name);
       Guest guest = query.getSingleResult();
       return PerformanceDTO.getDTOs(guest.getPerformances());
    }*/

    @Override
    public List<GuestDTO> getAllGuests() {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<Guest> query = em.createQuery("SELECT g from Guest g", Guest.class);
            List<Guest> guests = query.getResultList();
            return GuestDTO.getDTOs(guests);
        } finally {
            em.close();
        }
    }

    @Override
    public PerformanceDTO signGuestToShow(String name, String guestName) {
        EntityManager em = getEntityManager();
        TypedQuery<Performance> query = em.createQuery("SELECT p from Performance p WHERE p.name=:name", Performance.class);
        query.setParameter("name", name);
        Performance performances = (Performance) query.getResultList();
        TypedQuery<Guest> query1 = em.createQuery("SELECT g FROM Guest g WHERE g.name=:guestName", Guest.class);
        query1.setParameter("guestName", guestName);
        Guest guest = query1.getSingleResult();
        guest.addShow(performances);

        try {
            em.getTransaction().begin();
            em.merge(guest);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PerformanceDTO(performances);
    }

    @Override
    public PerformanceDTO createNewPerformance(PerformanceDTO performanceDTO) {
        EntityManager em = getEntityManager();
        Performance performance = new Performance(performanceDTO.getName(), performanceDTO.getDuration(), performanceDTO.getLocation(), performanceDTO.getStartDate(), performanceDTO.getStartTime());

        try {
            em.getTransaction().begin();
            em.persist(performance);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PerformanceDTO(performance);
    }

    @Override
    public FestivalDTO createNewFestival(FestivalDTO festivalDTO) {
        EntityManager em = getEntityManager();
        Festival festival = new Festival(festivalDTO.getName(), festivalDTO.getCity(), festivalDTO.getStartDate(), festivalDTO.getDuration());

        try {
            em.getTransaction().begin();
            em.persist(festival);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new FestivalDTO(festival);
    }

    @Override
    public GuestDTO createNewGuest(GuestDTO guestDTO) {
        EntityManager em = getEntityManager();
        Guest guest = new Guest(guestDTO.getName(), guestDTO.getPhone(), guestDTO.getEmail(), guestDTO.getStatus());

        try {
            em.getTransaction().begin();
            em.persist(guest);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new GuestDTO(guest);
    }

    @Override
    public PerformanceDTO deleteAPerformance(Integer id) {
        EntityManager em = getEntityManager();
        Performance performance = em.find(Performance.class, id);

        try {
            em.getTransaction().begin();
            em.remove(performance);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return new PerformanceDTO(performance);
    }

    @Override
    public FestivalDTO updateFestival(FestivalDTO festivalDTO) {
        EntityManager em = getEntityManager();
        Festival festival = em.find(Festival.class, festivalDTO.getId());

        try {
            em.getTransaction().begin();
            festival.setCity(festivalDTO.getCity());
            festival.setDuration(festivalDTO.getDuration());
            festival.setName(festivalDTO.getName());
            festival.setStartDate(festival.getStartDate());
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return new FestivalDTO(festival);
    }

    @Override
    public GuestDTO updateGuest(GuestDTO guestDTO) {
        EntityManager em = getEntityManager();
        Guest guest = em.find(Guest.class,guestDTO.getId());

        try{
            em.getTransaction().begin();
            guest.setEmail(guestDTO.getEmail());
            guest.setName(guestDTO.getName());
            guest.setPhone(guestDTO.getPhone());
            guest.setStatus(guestDTO.getStatus());
        }finally {
            em.close();
        }
        return new GuestDTO(guest);
    }

    @Override
    public PerformanceDTO updatePerformance(PerformanceDTO performanceDTO) {

        EntityManager em = getEntityManager();
        Performance performance = em.find(Performance.class,performanceDTO.getId());

        try{
            em.getTransaction().begin();
            performance.setDuration(performanceDTO.getDuration());
            performance.setLocation(performanceDTO.getLocation());
            performance.setDuration(performanceDTO.getDuration());
            performance.setStartDate(performanceDTO.getStartDate());
            performance.setStartTime(performance.getStartTime());
            em.getTransaction().commit();
        }finally {
            em.close();
        }
        return new PerformanceDTO(performance);
    }
}
