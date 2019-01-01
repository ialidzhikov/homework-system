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

import bg.uni.sofia.fmi.homeworksystem.dao.TraineeDAO;
import bg.uni.sofia.fmi.homeworksystem.model.Trainee;

@Path("hmwsrest/v1/trainees")
public class TraineeService {

	@Inject
	private TraineeDAO traineeDAO;

	@Inject
	private CurrentUserContext userCtx;

	@Path("/{id}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTraineeById(@PathParam("id") Long id) {
		Trainee trainee = traineeDAO.getById(Trainee.class, id);
		if (trainee != null) {
			return Response.ok(trainee).build();
		}

		return Response.status(Status.BAD_REQUEST).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Trainee> getAll() {
		if (userCtx.getUser() == null) {
			Response.status(Status.FORBIDDEN).build();
		}

		return this.traineeDAO.getAllTrainees();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(Trainee trainee) {
		if (traineeDAO.save(trainee)) {
			return Response.status(Status.CREATED).build();
		}

		return Response.status(Status.BAD_REQUEST).build();
	}

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") Long id) {
		Trainee traineeToDelete = traineeDAO.getById(Trainee.class, id);
		if (traineeDAO.delete(traineeToDelete)) {
			return Response.ok().build();
		}

		return Response.status(Status.BAD_REQUEST).build();
	}
}
