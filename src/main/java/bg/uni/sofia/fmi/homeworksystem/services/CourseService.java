package bg.uni.sofia.fmi.homeworksystem.services;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bg.uni.sofia.fmi.homeworksystem.contracts.User;
import bg.uni.sofia.fmi.homeworksystem.dao.CourseDAO;
import bg.uni.sofia.fmi.homeworksystem.dao.LectureDAO;
import bg.uni.sofia.fmi.homeworksystem.dao.TrainerDAO;
import bg.uni.sofia.fmi.homeworksystem.model.Course;
import bg.uni.sofia.fmi.homeworksystem.model.Lecture;
import bg.uni.sofia.fmi.homeworksystem.model.Trainer;

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
		
		
		Course course = this.courseDAO.getById(Course.class, id);
		return Response.ok(course).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCourses() {
		if (this.userContext.getUser() == null) {
			Response.status(Status.FORBIDDEN).build();
		}
		
		List<Course> courses = this.courseDAO.getAllCourses();
		JsonArray coursesJson = new JsonArray();
		for (Course course : courses) {
			coursesJson.add(course.toJson());
		}
		
		return Response.ok(coursesJson.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	@Path("/add")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postAddCourse(String data) {
		User user = this.userContext.getUser();
		if (this.userContext.getUser() == null) {
			Response.status(Status.FORBIDDEN).build();
		}
		
		Long userId = ((Trainer) user).getId();
		Trainer trainer = this.trainerDAO.getById(Trainer.class, userId);
		
		JsonObject courseData = new JsonParser().parse(data).getAsJsonObject();
		String name = courseData.get("name").getAsString();
		String description = courseData.get("description").getAsString();
		
		Course course = new Course(name, description, false, trainer);
		this.courseDAO.save(course);
		
		List<Course> courses = this.courseDAO.getAllCourses();
		
		String response = String.format("{\"name\": \"%s\"}", name);
		return Response.ok(response).build();
	}
	
}
