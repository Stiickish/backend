package facades;

import dtos.FestivalDTO;
import dtos.GuestDTO;
import dtos.PerformanceDTO;
import entities.Festival;
import entities.Guest;
import entities.Performance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FacadeTest {

    private static EntityManagerFactory emf;

    private static Facade facade;

    Performance p1, p2;
    Festival f1, f2;

    Guest g1, g2;

    public FacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = Facade.getInstance(emf);
    }

    @BeforeEach
    void setup() {
        EntityManager em = emf.createEntityManager();
        p1 = new Performance("Wallmans", "2 hours", "Copenhagen", "1/1/2023", "18:30");
        p2 = new Performance("Nøddeknækkeren", "1.5 time", "DR byen", "23/1/2023", "17:00");

        f1 = new Festival("Roskilde festival", "Roskilde", "30/6/2023", "30 dage");
        f2 = new Festival("Copenhell", "Amager", "7/8/2023", "2 uger");

        g1 = new Guest("Børge","23345678","Boerge@hotmail.com","something");
        g2 = new Guest("Hans","56789034","hans@gmail.com","something");

        //Add user to show
        //g1.addShow(p1);
        //g1.addShow(p2);

        //Add user to festivals
        g1.setFestival(f1);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Festival.deleteAllRows").executeUpdate();
            em.createNamedQuery("Performance.deleteAllRows").executeUpdate();
            em.createNamedQuery("Guest.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            em.persist(f1);
            em.persist(f2);
            em.persist(g1);
            em.persist(g2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void getAllShows() {
        int expected = 2;
        List<PerformanceDTO> showDTOList = facade.getAllShows();
        assertEquals(expected, showDTOList.size());
    }

    @Test
    void getAssignedShow() {
        int expected = 0;
        int actual = facade.getAssignedShow("Henrik").size();
        assertEquals(expected, actual);

    }

    @Test
    void getAllGuests() {
        int expected = 2;
        List<GuestDTO> guestDTOList = facade.getAllGuests();
        assertEquals(expected, guestDTOList.size());

    }
    @Test
    void createNewFestival(){
        Festival festival = new Festival("Langelandsfestival","Langeland","23/5/2022","1 uge");
        FestivalDTO expected = new FestivalDTO(festival);
        FestivalDTO result = facade.createNewFestival(expected);

        assertNotNull(result);
        assertEquals(festival.getName(),result.getName());

    }

    @Test
    void createNewGuest(){
        Guest guest = new Guest("Møller","12345678","Moeller@gmail.dk","something");
        GuestDTO expected = new GuestDTO(guest);
        GuestDTO result = facade.createNewGuest(expected);

        assertNotNull(result);
        assertEquals(guest.getName(),result.getName());
    }
    @Test
    void createNewPerformance(){
        Performance performance = new Performance("Svanesøen","3 timer","Kgl. Teater","2/4/2023","17:00");
        PerformanceDTO expected = new PerformanceDTO(performance);
        PerformanceDTO result = facade.createNewPerformance(expected);

        assertNotNull(result);
        assertEquals(performance.getName(),result.getName());
    }
}
