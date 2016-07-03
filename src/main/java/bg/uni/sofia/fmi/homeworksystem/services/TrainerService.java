package bg.uni.sofia.fmi.homeworksystem.services;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bg.uni.sofia.fmi.homeworksystem.dao.TrainerDAO;
import bg.uni.sofia.fmi.homeworksystem.model.Trainer;

@Path("hmwsrest/v1/trainers")
public class TrainerService{
	
	@Inject
	private TrainerDAO trainerDAO;
	
	@Inject
	private CurrentTrainerContext trainerContext;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		if(trainerContext.getTrainer() == null){
			Response.status(Status.FORBIDDEN).build();
		}
		List<Trainer> allTrainers = this.trainerDAO.getAllTrainers();
		GenericEntity<List<Trainer>> genericEntity = new GenericEntity<List<Trainer>>(allTrainers) {};
		return Response.ok(genericEntity).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(String data) {
		Trainer tempTrainerObj = createTempTrainerObj(data);
		if(trainerDAO.save(tempTrainerObj)){
			return Response.status(Status.CREATED).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delete(String data) {
		Trainer tempTrainerObj = createTempTrainerObj(data);
		Trainer trainerToDelete = trainerDAO.findTrainerByUserName(tempTrainerObj.getUserName());
		if(trainerDAO.delete(trainerToDelete)){
			return Response.status(Status.OK).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	private Trainer createTempTrainerObj(String data){
		
		JsonObject trainerData = new JsonParser().parse(data).getAsJsonObject();
		String userName= trainerData.get("userName").getAsString();
		String name= trainerData.get("name").getAsString();
		String password = trainerData.get("password").getAsString();
		String degree = trainerData.get("degree").getAsString();
		String email = trainerData.get("email").getAsString();
		
		return new Trainer(userName, password, degree, name, email);
	}
}
