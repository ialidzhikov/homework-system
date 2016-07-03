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
import bg.uni.sofia.fmi.homeworksystem.dao.UserDAO;

@RequestScoped
@Path("hmwsrest/v1/user")
public class HomeService {
	
	@Inject
	private UserDAO userDAO;
	
	@Inject
	private CurrentUserContext userCtx;

	
	@Path("/login")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(String data) {
		JsonObject userData = new JsonParser().parse(data).getAsJsonObject();
		String username = userData.get("username").getAsString();
		String pass = userData.get("password").getAsString();
		User user = userDAO.validateCredentials(username, pass);
		
		userCtx.setUser(user);
		return Response.ok("{}").build();
	}
}
