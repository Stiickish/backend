package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.Facade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

@Path("moviefestival")
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
    public Response getAssignedShow(@PathParam("name")String name){
        return Response.ok().entity(GSON.toJson(facade.getAssignedShow(name))).build();
    }

}
