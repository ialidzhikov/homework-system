package bg.uni.sofia.fmi.homeworksystem.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import bg.uni.sofia.fmi.homeworksystem.contracts.User;
import bg.uni.sofia.fmi.homeworksystem.dao.CourseDAO;
import bg.uni.sofia.fmi.homeworksystem.dao.TrainerDAO;
import bg.uni.sofia.fmi.homeworksystem.model.Course;
import bg.uni.sofia.fmi.homeworksystem.model.Trainee;
import bg.uni.sofia.fmi.homeworksystem.model.Trainer;
import bg.uni.sofia.fmi.homeworksystem.rest.request.FavoriteRequest;
import bg.uni.sofia.fmi.homeworksystem.utils.Role;

@Path("hmwsrest/v1/courses")
public class CourseService {
	
	@Inject
	private CurrentUserContext userContext;
	
	@Inject
	private CourseDAO courseDao;
	
	@Inject
	private TrainerDAO trainerDao;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> getAll() {
		return courseDao.getAllCourses();
	}

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourse(@PathParam("id") Long id) {
		if (this.userContext.getUser() == null) {
			Response.status(Status.FORBIDDEN).build();
		}
		
		Course course = this.courseDao.getById(Course.class, id);
		return Response.ok(course).build();
	}
	
	@POST
	@Path("{id}/enroll")
	public Response enroll(@PathParam("id") Long id) {
		Course course = this.courseDao.getById(Course.class, id);
		Trainee trainee = (Trainee) this.userContext.getUser();
		trainee.getCourses().add(course);
		course.getTrainees().add(trainee);

		return Response.ok().build();
	}
	
	@POST
	@Path("{id}/favourite")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response markCourseAsTrainerFavourite(@PathParam("id") Long id, FavoriteRequest request) {
		Course course = this.courseDao.getById(Course.class, id);
		course.setIsFavouriteToTrainer(request.isFavourite());
		courseDao.update(course);

		return Response.ok().build();
	}

	@Path("/my")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCoursesByUserRole() {
		User user = this.userContext.getUser();
		if (user == null) {
			Response.status(Status.FORBIDDEN).build();
		}

		List<Course> courses = new ArrayList<>();
		if (user.getUserRole().equals(Role.TRAINEE)) {
			courses = ((Trainee) user).getCourses();
		} else if (user.getUserRole().equals(Role.TRAINER)) {
			Trainer trainer = (Trainer) user;
			courses = courseDao.getCoursesForTrainer(trainer.getId());
		}

		return Response.ok(courses).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postAddCourse(Course course) {
		User user = this.userContext.getUser();
		if (this.userContext.getUser() == null) {
			Response.status(Status.FORBIDDEN).build();
		}

		Long userId = ((Trainer) user).getId();
		Trainer trainer = this.trainerDao.getById(Trainer.class, userId);
		course.setTrainer(trainer);

		this.courseDao.save(course);

		return Response.status(Status.CREATED).entity(course).build();
	}
}
