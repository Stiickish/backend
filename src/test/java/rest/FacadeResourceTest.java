package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PerformanceDTO;
import entities.Festival;
import entities.Guest;
import entities.Performance;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class FacadeResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    Performance s1, s2;
    Festival f1, f2;

    Guest g1, g2;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setup() {
        EntityManager em = emf.createEntityManager();
        s1 = new Performance("Wallmans", "2 hours", "Copenhagen", "1/1/2023", "18:30");
        s2 = new Performance("Nøddeknækkeren", "1.5 time", "DR byen", "23/1/2023", "17:00");

        f1 = new Festival("Roskilde festival", "Roskilde", "30/6/2023", "30 dage");
        f2 = new Festival("Copenhell", "Amager", "7/8/2023", "2 uger");

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Performance.deleteAllRows").executeUpdate();
            em.createNamedQuery("Festival.deleteAllRows").executeUpdate();
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
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/moviefestival").then().statusCode(200);
    }

    @Test
    public void testLogRequest() {
        System.out.println("Testing logging request details");
        given().log().all()
                .when().get("/moviefestival")
                .then().statusCode(200);
    }

    @Test
    public void testLogResponse() {
        System.out.println("Testing logging response details");
        given()
                .when().get("/moviefestival")
                .then().log().body().statusCode(200);
    }

    @Test
    public void getAllShows() {
        List<PerformanceDTO> showDTO;

        showDTO = given()
                .contentType("application/json")
                .when()
                .get("/moviefestival/shows")
                .then()
                .extract().body().jsonPath().getList("", PerformanceDTO.class);

        PerformanceDTO showDTO1 = new PerformanceDTO(s1);
        PerformanceDTO showDTO2 = new PerformanceDTO(s2);
        assertThat(showDTO, containsInAnyOrder(showDTO1, showDTO2));

    }

    @Test
    void getAssignedShow() {
        List<PerformanceDTO> showDTOS;

        showDTOS = given()
                .contentType("application/json")
                .when()
                .get("/moviefestival/assignedShow" + g1.getName())
                .then()
                .extract().body().jsonPath().getList("", PerformanceDTO.class);
        assertThat(showDTOS, containsInAnyOrder(s1, s2));
    }
}
