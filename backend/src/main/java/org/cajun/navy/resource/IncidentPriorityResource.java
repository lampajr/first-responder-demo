package org.cajun.navy.resource;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/incident-priority-service")
@RequestScoped
public class IncidentPriorityResource {

    @GET
    @Path("/priority-zones")
    @Produces(MediaType.APPLICATION_JSON)
    public Response shelters() {
        return Response.status(Response.Status.ACCEPTED).entity(new ArrayList<String>()).build();
    }
}
