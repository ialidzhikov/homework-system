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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bg.uni.sofia.fmi.homeworksystem.dao.TraineeDAO;
import bg.uni.sofia.fmi.homeworksystem.model.Course;
import bg.uni.sofia.fmi.homeworksystem.model.Trainee;

@Path("hmwsrest/v1/trainees") 
public class TraineeService{
	
	@Inject
	private TraineeDAO traineeDAO;
	
	@Inject
	private CurrentTraineeContext traineeContext;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		if(traineeContext.getTrainee() == null){
			Response.status(Status.FORBIDDEN).build();
		}
		/*List<Trainee> allTrainees = this.traineeDAO.getAllTrainees();
		GenericEntity<List<Trainee>> genericEntity = new GenericEntity<List<Trainee>>(allTrainees) {};
		return Response.ok(genericEntity).build();*/
		
		List<Trainee> trainees = this.traineeDAO.getAllTrainees();
		JsonArray traineesJSON = new JsonArray();
		for (Trainee trainee : trainees) {
			traineesJSON.add(trainee.toJson());
		}
		return Response.ok(traineesJSON.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(String data) {
		Trainee tempTrainee = createTempTraineeObj(data);		
		if(traineeDAO.save(tempTrainee)){
			return Response.status(Status.CREATED).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delete(String data) {
		Trainee tempTrainee = createTempTraineeObj(data);
		Trainee traineeToDelete = traineeDAO.findStudentByFacultyNumber(tempTrainee.getFacultyNumber());
		if(traineeDAO.delete(traineeToDelete)){
			return Response.status(Status.OK).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	private Trainee createTempTraineeObj(String data){
		JsonObject traineeData = new JsonParser().parse(data).getAsJsonObject();
		String name = traineeData.get("name").getAsString();
		String password = traineeData.get("name").getAsString();
		String email = traineeData.get("email").getAsString();
		String facultyNumber = traineeData.get("facultyNumber").getAsString();
		String fieldOfStudy = traineeData.get("fieldOfStudy").getAsString();
		return new Trainee(facultyNumber, password, name, email, fieldOfStudy);
	}

}
