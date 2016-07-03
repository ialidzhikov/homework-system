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

import bg.uni.sofia.fmi.homeworksystem.contracts.User;
import bg.uni.sofia.fmi.homeworksystem.dao.TraineeDAO;
import bg.uni.sofia.fmi.homeworksystem.dao.TrainerDAO;
import bg.uni.sofia.fmi.homeworksystem.utils.Role;

@RequestScoped
@Path("hmwsrest/v1/user")
public class HomeService {
	
	@Inject
	private TraineeDAO traineeDAO;
	
	@Inject
	private TrainerDAO trainerDAO;
	
	@Inject
	private CurrentUserContext userCtx;

	
	@Path("/login")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(String data) {
		JsonObject userData = new JsonParser().parse(data).getAsJsonObject();
		String username = userData.get("username").toString();
		String pass = userData.get("password").toString();
		User user = traineeDAO.getTraineeByUsernameAndPass(username, pass);
		if (user == null) {
			user = trainerDAO.getTrainerByUsernameAndPass(username, pass);
		}
		
		userCtx.setUser(user);
		return Response.ok(user).build();
	}
}
