package bg.uni.sofia.fmi.homeworksystem.services;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import bg.uni.sofia.fmi.homeworksystem.dao.TrainerDAO;
import bg.uni.sofia.fmi.homeworksystem.model.Trainer;

@Path("hmwsrest/v1/trainers")
public class TrainerService{
	
	@Inject
	private TrainerDAO trainerDAO;
	
	@Inject
	private CurrentUserContext userCtx;
	
	@Path("/{id}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTraineeById(@PathParam("id") Long id) {
		Trainer trainer = trainerDAO.getById(Trainer.class, id);
		if (trainer != null) {
			return Response.ok(trainer).build();
		}
		
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		if(userCtx.getUser() == null){
			Response.status(Status.FORBIDDEN).build();
		}
		List<Trainer> allTrainers = this.trainerDAO.getAllTrainers();
		
		return Response.ok(allTrainers).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Trainer trainer) {
		if (trainerDAO.save(trainer)) {
			return Response.status(Status.CREATED).build();
		}

		return Response.status(Status.BAD_REQUEST).build();
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		Trainer trainerToDelete = trainerDAO.getById(Trainer.class, id);
		if (trainerDAO.delete(trainerToDelete)) {
			return Response.ok().build();
		}

		return Response.status(Status.BAD_REQUEST).build();
	}
}
