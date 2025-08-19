package org.cajun.navy.resource;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.cajun.navy.service.MissionService;
import org.cajun.navy.service.model.Mission;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Path("/api/missions")
@RequestScoped
public class MissionResource {

    @Inject
    MissionService service;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public CompletionStage<Response> allMissions() {
        return CompletableFuture.supplyAsync(() -> Response.status(Response.Status.ACCEPTED).entity(service.findAll()).build());
    }

    @POST
    @Path("clear")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response clearAll() {
        return Response.status(Response.Status.ACCEPTED).build();
    }


    @GET
    @Path("responders/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response missionByResponderId(@PathParam("id") String responderId) {
        Mission item = service.findByMissionId(responderId);
        if (item == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.ok(item).build();
        }
    }


}


