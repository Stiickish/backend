package facades;

import dtos.ShowDTO;
import entities.Show;
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

    Show s1, s2;

    public FacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = Facade.getInstance(emf);
    }

    @BeforeEach
    void setup(){
        EntityManager em = emf.createEntityManager();
        s1 = new Show("Wallmans","2 hours","Copenhagen","1/1/2023","18:30");
        s2 = new Show("Nøddeknækkeren","1.5 time","DR byen","23/1/2023","17:00");


        try{
            em.getTransaction().begin();
            em.createNamedQuery("Show.deleteAllRows").executeUpdate();
            em.persist(s1);
            em.persist(s2);
            em.getTransaction().commit();
        }finally {
            em.close();
        }
    }

    @Test
    void getAllShows(){
        int expected = 2;
        List<ShowDTO> showDTOList = facade.getAllShows();
        assertEquals(expected,showDTOList.size());
    }
}
