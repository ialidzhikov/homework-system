package bg.uni.sofia.fmi.homeworksystem.services;

import java.util.List;

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
import bg.uni.sofia.fmi.homeworksystem.dao.CourseDAO;
import bg.uni.sofia.fmi.homeworksystem.dao.TraineeDAO;
import bg.uni.sofia.fmi.homeworksystem.dao.TrainerDAO;
import bg.uni.sofia.fmi.homeworksystem.dao.UserDAO;
import bg.uni.sofia.fmi.homeworksystem.model.Course;
import bg.uni.sofia.fmi.homeworksystem.model.Trainer;
import bg.uni.sofia.fmi.homeworksystem.utils.Role;

@RequestScoped
@Path("hmwsrest/v1/user")
public class HomeService {
	
	@Inject
	private TraineeDAO traineeDAO;
	
	@Inject
	private UserDAO userDAO;
	
	@Inject
	private TrainerDAO trainerDAO;
	
	@Inject
	private CourseDAO courseDAO;
	
	@Inject
	private CurrentUserContext userCtx;

	
	@Path("/login")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(String data) {
		Trainer trainer = new Trainer("trainer", "1234", "PhD", "Stoyan Vellev", "stoyan.vellev@sap.com");
		trainerDAO.save(trainer);
		
		Course courseOne = new Course("Java EE", "Some description", false, trainer);
		Course courseTwo = new Course("AngularJS", "Some description", false, trainer);
		courseDAO.save(courseOne);
		courseDAO.save(courseTwo);
		
		JsonObject userData = new JsonParser().parse(data).getAsJsonObject();
		String username = userData.get("username").getAsString();
		String pass = userData.get("password").getAsString();
		User user = userDAO.validateCredentials(username, pass);
		
		userCtx.setUser(user);
		return Response.ok("{\"status\": 200}").build();
	}
}
