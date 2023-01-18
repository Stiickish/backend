package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.FestivalDTO;
import dtos.GuestDTO;
import dtos.PerformanceDTO;
import entities.Festival;
import entities.Guest;
import entities.Performance;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
import static org.hamcrest.Matchers.*;

public class FacadeResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    Performance p1, p2;
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
        p1 = new Performance("Wallmans", "2 hours", "Copenhagen", "1/1/2023", "18:30");
        p2 = new Performance("Nøddeknækkeren", "1.5 time", "DR byen", "23/1/2023", "17:00");

        f1 = new Festival("Roskilde festival", "Roskilde", "30/6/2023", "30 dage");
        f2 = new Festival("Copenhell", "Amager", "7/8/2023", "2 uger");

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Performance.deleteAllRows").executeUpdate();
            em.createNamedQuery("Festival.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            em.persist(f1);
            em.persist(f2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

   /* @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/festival").then().statusCode(200);
    }

    @Test
    public void testLogRequest() {
        System.out.println("Testing logging request details");
        given().log().all()
                .when().get("/festival")
                .then().statusCode(200);
    }

    @Test
    public void testLogResponse() {
        System.out.println("Testing logging response details");
        given()
                .when().get("/festival")
                .then().log().body().statusCode(200);
    }*/

    @Test
    public void getAllShows() {
        List<PerformanceDTO> showDTO;

        showDTO = given()
                .contentType("application/json")
                .when()
                .get("/festival/performance")
                .then()
                .extract().body().jsonPath().getList("", PerformanceDTO.class);

        PerformanceDTO performanceDTO = new PerformanceDTO(p1);
        PerformanceDTO performanceDTO1 = new PerformanceDTO(p2);
        assertThat(showDTO, containsInAnyOrder(performanceDTO1, performanceDTO));

    }

   /* @Test
    void getAssignedShow() {
        List<PerformanceDTO> showDTOS;

        showDTOS = given()
                .contentType("application/json")
                .when()
                .get("/festival/assign" + g1.getName())
                .then()
                .extract().body().jsonPath().getList("", PerformanceDTO.class);
        assertThat(showDTOS, containsInAnyOrder(p1, p2));
    }*/

    @Test
    void createGuest() {
        Guest guest = new Guest("something", "something", "something", "something");
        GuestDTO guestDTO = new GuestDTO(guest);
        String requestBody = GSON.toJson(guestDTO);
        System.out.println(guestDTO);

        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/festival/createGuest")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue())
                .body("email", equalTo(guestDTO.getEmail()))
                .body("name", equalTo(guestDTO.getName()))
                .body("phone", equalTo(guestDTO.getPhone()))
                .body("status", equalTo(guestDTO.getStatus()));

    }

    @Test
    void createPerformance() {
        Performance performance = new Performance("something", "something", "something", "something", "something");
        PerformanceDTO performanceDTO = new PerformanceDTO(performance);
        String requestBody = GSON.toJson(performanceDTO);
        System.out.println(performanceDTO);

        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/festival/createPerformance")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue())
                .body("duration", equalTo(performanceDTO.getDuration()))
                .body("location", equalTo(performanceDTO.getLocation()))
                .body("name", equalTo(performanceDTO.getName()))
                .body("startDate", equalTo(performanceDTO.getStartDate()))
                .body("startTime", equalTo(performanceDTO.getStartTime()));
    }

    @Test
    void createFestival() {
        Festival festival = new Festival("something", "something", "something", "something");
        FestivalDTO festivalDTO = new FestivalDTO(festival);
        String requestBody = GSON.toJson(festivalDTO);
        System.out.println(festivalDTO);

        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/festival/createFestival")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue())
                .body("city", equalTo(festivalDTO.getCity()))
                .body("duration", equalTo(festivalDTO.getDuration()))
                .body("name", equalTo(festivalDTO.getName()))
                .body("startDate", equalTo(festivalDTO.getStartDate()));
    }

    /*@Test
    public void updateGuest() {
        g1.setName("Arne");
        GuestDTO guestDTO = new GuestDTO(g1);
        String requestBody = GSON.toJson(guestDTO);
        g1 = new Guest("Junior", "1234567", "something", "taken");

        given()
                .header("Content-type", ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/guest/" + g1.getId())
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(g1.getId()))
                .body("name", equalTo("Junior"))
                .body("phone", equalTo("1234567"))
                .body("email", equalTo("something"))
                .body("status", equalTo("taken"));
    }

    @Test
    public void deletePerformance() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", p1.getId())
                .delete("/performance/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(p1.getId()));
    }*/
}
