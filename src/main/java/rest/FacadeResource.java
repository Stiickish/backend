package rest;

import com.google.gson.*;
import dtos.FestivalDTO;
import dtos.GuestDTO;
import dtos.PerformanceDTO;
import errorhandling.API_Exception;
import facades.Facade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("festival")
public class FacadeResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Facade facade = Facade.getInstance(EMF);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Path("/shows")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllShows() {
        return Response.ok().entity(GSON.toJson(facade.getAllShows())).build();

    }

    @GET
    @Path("guest/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAssignedShow(@PathParam("name") String name) {
        return Response.ok().entity(GSON.toJson(facade.getAssignedShow(name))).build();
    }

    @GET
    @Path("/guest")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllGuests() {
        return Response.ok().entity(GSON.toJson(facade.getAllGuests())).build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("assign")
    public Response assignToShow(String jsonInput) throws API_Exception {

        String performanceName;
        String guestName;

        try {
            JsonObject jsonObject = JsonParser.parseString(jsonInput).getAsJsonObject();
            performanceName = jsonObject.get("performanceName").getAsString();
            guestName = jsonObject.get("guestName").getAsString();
        } catch (Exception e) {
            throw new API_Exception("Something went wrong");
        }
        return Response.ok().entity(GSON.toJson(facade.signGuestToShow(performanceName, guestName))).build();
    }

    @POST
    @Path("createPerformance")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createNewShow(String jsonInput) {
        PerformanceDTO performanceDTO = GSON.fromJson(jsonInput, PerformanceDTO.class);
        performanceDTO = facade.createNewPerformance(performanceDTO);
        return Response.ok().entity(GSON.toJson(performanceDTO)).build();
    }

    @POST
    @Path("createGuest")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewUser(String jsonInput) {
        GuestDTO guestDTO = GSON.fromJson(jsonInput, GuestDTO.class);
        guestDTO = facade.createNewGuest(guestDTO);
        return Response.ok().entity(GSON.toJson(guestDTO)).build();
    }

    @POST
    @Path("/createFestival")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewFestival(String jsonInput) {
        FestivalDTO festivalDTO = GSON.fromJson(jsonInput, FestivalDTO.class);
        festivalDTO = facade.createNewFestival(festivalDTO);
        return Response.ok().entity(GSON.toJson(festivalDTO)).build();

    }

    @DELETE
    @Path("/performance/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteAPerformance(@PathParam("id") int id) {
        PerformanceDTO deleted = facade.deleteAPerformance(id);
        return Response.ok().entity(GSON.toJson(deleted)).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/festival/{id}")
    public Response updateFestival(@PathParam("id") int id, String jsonInput) {
        FestivalDTO festivalDTO = GSON.fromJson(jsonInput, FestivalDTO.class);
        festivalDTO.setId(id);
        festivalDTO = facade.updateFestival(festivalDTO);
        return Response.ok().entity(GSON.toJson(festivalDTO)).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/performance/{id}")
    public Response updatePerformance(@PathParam("id") int id, String jsonInput) {
        PerformanceDTO performanceDTO = GSON.fromJson(jsonInput, PerformanceDTO.class);
        performanceDTO.setId(id);
        performanceDTO = facade.updatePerformance(performanceDTO);
        return Response.ok().entity(GSON.toJson(performanceDTO)).build();
    }

    @PUT
    @Path("guest/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateGuest(@PathParam("id") int id, String jsonInput) {
        GuestDTO guestDTO = GSON.fromJson(jsonInput, GuestDTO.class);
        guestDTO.setId(id);
        guestDTO = facade.updateGuest(guestDTO);
        return Response.ok().entity(GSON.toJson(guestDTO)).build();
    }

}
