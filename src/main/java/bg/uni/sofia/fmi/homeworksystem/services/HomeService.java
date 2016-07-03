package bg.uni.sofia.fmi.homeworksystem.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
	
	@Path("/authenticated")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAuthenticated() {
		User user = userCtx.getUser();
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		String response = String.format("{\"role\": \"%s\", \"username\": \"%s\"}", 
				user.getUserRole(), user.getUsername());
		return Response.ok(response).build();
	}
	
	@Path("/logout")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout() {
		userCtx.setUser(null);
		
		return Response.ok("{}").build();
	}
}
