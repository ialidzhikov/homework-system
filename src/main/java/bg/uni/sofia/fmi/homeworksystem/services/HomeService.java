package bg.uni.sofia.fmi.homeworksystem.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bg.uni.sofia.fmi.homeworksystem.dao.TraineeDAO;
import bg.uni.sofia.fmi.homeworksystem.dao.TrainerDAO;

@RequestScoped
@Path("hmwsrest/v1/user")
public class HomeService {
	
	@Inject
	private TraineeDAO traineeDAO;
	
	@Inject
	private TrainerDAO trainerDAO;

	
	@Path("/login")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(String data) {
		JsonObject userData = new JsonParser().parse(data).getAsJsonObject();
		String username = userData.get("username").toString();
		String pass = userData.get("password").toString();
		traineeDAO.getAllTrainees();
		
		return Response.ok("{\"role\": \"trainee\", \"fullname\" : \"Gosho Petrov\"}", MediaType.APPLICATION_JSON).build();
	}
	
	@Path("/admin/register")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(String data) {
		
		JsonObject userData = new JsonParser().parse(data).getAsJsonObject();
		String username = userData.get("username").toString();
		String pass = userData.get("password").toString();
		
		return null;
	}
}
