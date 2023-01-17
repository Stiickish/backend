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

    Performance p1, p2, p3, p4;
    Festival f1, f2, f3, f4;

    Guest g1, g2, g3, g4;

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

        p3 = new Performance("VM i Curling","5 timer","Royal Arena","1/4/2023","13:40");
        p4 = new Performance("Troldespejlet","hele dagen","i tv'et","1/1/2023","00:00");


        f1 = new Festival("Roskilde festival", "Roskilde", "30/6/2023", "30 dage");
        f2 = new Festival("Copenhell", "Amager", "7/8/2023", "2 uger");

        g1 = new Guest("Børge", "23345678", "Boerge@hotmail.com", "something");
        g2 = new Guest("Hans", "56789034", "hans@gmail.com", "something");

        g3 = new Guest("William","12345678","Will@iam.dk","something");
        g4 = new Guest("Bertha","56789023","b@hotmail.com","something");

        //Add user to show
        /*g1.addShow(p1);
        g1.addShow(p2);
        g3.addShow(p1);*/

        //Add user to festivals
        /*g1.setFestival(f1);
        g2.setFestival(f1);*/

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Festival.deleteAllRows").executeUpdate();
            em.createNamedQuery("Performance.deleteAllRows").executeUpdate();
            em.createNamedQuery("Guest.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.persist(p4);
            em.persist(f1);
            em.persist(f2);
            em.persist(g1);
            em.persist(g2);
            em.persist(g3);
            em.persist(g4);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void getAllShows() {
        int expected = 4;
        List<PerformanceDTO> showDTOList = facade.getAllShows();
        assertEquals(expected, showDTOList.size());
    }

    @Test
    void getAssignedShow() {
        int expected = 0;
        int actual = facade.getAssignedShow("Hans").size();
        assertEquals(expected, actual);

    }

    @Test
    void getAllGuests() {
        int expected = 4;
        List<GuestDTO> guestDTOList = facade.getAllGuests();
        assertEquals(expected, guestDTOList.size());

    }

    @Test
    void createNewFestival() {
        Festival festival = new Festival("Langelandsfestival", "Langeland", "23/5/2022", "1 uge");
        FestivalDTO expected = new FestivalDTO(festival);
        FestivalDTO result = facade.createNewFestival(expected);

        assertNotNull(result);
        assertEquals(festival.getName(), result.getName());

    }

    @Test
    void createNewGuest() {
        Guest guest = new Guest("Møller", "12345678", "Moeller@gmail.dk", "something");
        GuestDTO expected = new GuestDTO(guest);
        GuestDTO result = facade.createNewGuest(expected);

        assertNotNull(result);
        assertEquals(guest.getName(), result.getName());
    }

    @Test
    void createNewPerformance() {
        Performance performance = new Performance("Svanesøen", "3 timer", "Kgl. Teater", "2/4/2023", "17:00");
        PerformanceDTO expected = new PerformanceDTO(performance);
        PerformanceDTO result = facade.createNewPerformance(expected);

        assertNotNull(result);
        assertEquals(performance.getName(), result.getName());
    }
}
