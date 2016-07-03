package bg.uni.sofia.fmi.homeworksystem.services;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bg.uni.sofia.fmi.homeworksystem.contracts.User;
import bg.uni.sofia.fmi.homeworksystem.dao.CourseDAO;
import bg.uni.sofia.fmi.homeworksystem.dao.LectureDAO;
import bg.uni.sofia.fmi.homeworksystem.dao.TrainerDAO;
import bg.uni.sofia.fmi.homeworksystem.model.Course;
import bg.uni.sofia.fmi.homeworksystem.model.Lecture;

@Path("hmwsrest/v1/courses")
public class CourseService {
	
	@Inject
	private CurrentUserContext userContext;
	
	@Inject
	private CourseDAO courseDAO;
	@Inject
	private TrainerDAO trainerDAO;
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourse(@PathParam("id") Long id) {
		if (this.userContext.getUser() == null) {
			Response.status(Status.FORBIDDEN).build();
		}
		
		//Lecture lecture = this.courseDAO.getLectureById(id);
		return null;//Response.ok(lecture).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCourses() {
		if (this.userContext.getUser() == null) {
			Response.status(Status.FORBIDDEN).build();
		}
		
		List<Course> courses = this.courseDAO.getAllCourses();
		GenericEntity<List<Course>> genericEntity = new GenericEntity<List<Course>>(courses) {}; 
		return Response.ok(genericEntity).build();
	}
	
	@Path("/add")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response postAddCourse(String data) {
		/*
		User user = this.userContext.getUser();
		if (this.userContext.getUser() == null) {
			Response.status(Status.FORBIDDEN).build();
		}
		*/
		
		JsonObject courseData = new JsonParser().parse(data).getAsJsonObject();
		String name = courseData.get("name").toString();
		String description = courseData.get("description").toString();
		
		return null;//Response.ok(lecture).build();
	}
}
