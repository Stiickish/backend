package facades;

import dtos.PerformanceDTO;
import entities.Festival;
import entities.Performance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FacadeTest {

    private static EntityManagerFactory emf;

    private static Facade facade;

    Performance s1, s2;
    Festival f1, f2;

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
        s1 = new Performance("Wallmans", "2 hours", "Copenhagen", "1/1/2023", "18:30");
        s2 = new Performance("Nøddeknækkeren", "1.5 time", "DR byen", "23/1/2023", "17:00");

        f1 = new Festival("Roskilde festival", "Roskilde", "30/6/2023", "30 dage");
        f2 = new Festival("Copenhell", "Amager", "7/8/2023", "2 uger");


        try {
            em.getTransaction().begin();
            em.createNamedQuery("Festival.deleteAllRows").executeUpdate();
            em.createNamedQuery("Performance.deleteAllRows").executeUpdate();
            em.persist(s1);
            em.persist(s2);
            em.persist(f1);
            em.persist(f2);
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
        int expected = 2;
        int actual = facade.getAssignedShow("Henrik").size();
        assertEquals(expected, actual);

    }
}
