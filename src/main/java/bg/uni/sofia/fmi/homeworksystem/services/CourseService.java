package bg.uni.sofia.fmi.homeworksystem.services;

import java.util.Date;
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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bg.uni.sofia.fmi.homeworksystem.contracts.User;
import bg.uni.sofia.fmi.homeworksystem.dao.CourseDAO;
import bg.uni.sofia.fmi.homeworksystem.dao.LectureDAO;
import bg.uni.sofia.fmi.homeworksystem.dao.TraineeDAO;
import bg.uni.sofia.fmi.homeworksystem.dao.TrainerDAO;
import bg.uni.sofia.fmi.homeworksystem.dao.TrainerEmailSchedulerDAO;
import bg.uni.sofia.fmi.homeworksystem.dao.UploadedSubmissionDAO;
import bg.uni.sofia.fmi.homeworksystem.dao.UserDAO;
import bg.uni.sofia.fmi.homeworksystem.model.Course;
import bg.uni.sofia.fmi.homeworksystem.model.Lecture;
import bg.uni.sofia.fmi.homeworksystem.model.Trainee;
import bg.uni.sofia.fmi.homeworksystem.model.Trainer;
import bg.uni.sofia.fmi.homeworksystem.model.TrainerEmailScheduler;
import bg.uni.sofia.fmi.homeworksystem.model.UploadedSubmission;

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
	
	///TODO
	@Inject
	private TrainerEmailSchedulerDAO trainerEmailSchedulerDAO;
	@Inject
	private LectureDAO lectureDAO;

	@Inject
	private UserDAO userDAO;

	public void Test() {
		addTrainers();
		addTrainees();
		addCourses();
		addLectures();
		addUploadedSubmissions();
		validateCredentials();
		addCoursesToTrainees();
		List<Trainer> trainers = trainerDAO.getAllTrainers();
		List<Trainee> trainees = traineeDAO.getAllTrainees();
		List<Course> courses = courseDAO.getAllCourses();
		List<Lecture> lectures = lectureDAO.getAllLectures();
		List<UploadedSubmission> uploadedSubmissions = uploadedsubmissionDAO.getAllUploadedSubmissions();
		List<UploadedSubmission> userUplSubmissions = trainerDAO.getAllUploadedSubmisiions(trainers.get(0));
		List<TrainerEmailScheduler> trainerEmailSchedulers = trainerEmailSchedulerDAO.getAllTrainerEmailSchedulers();
		
		UploadedSubmission uploadedSubmission = uploadedsubmissionDAO.getAllUploadedSubmissions().get(0);
		uploadedsubmissionDAO.evaluateUploadedSubmission(uploadedSubmission, 5.0);
		
		System.err.println("Done");
	}

	private void addCoursesToTrainees() {
		for (int i = 0; i < 5; i++) {
			Trainee trainee = traineeDAO.getAllTrainees().get(i);
			Course course = courseDAO.getAllCourses().get(i);
			traineeDAO.addCourse(trainee, course);
		}

	}

	@Inject
	private UploadedSubmissionDAO uploadedsubmissionDAO;

	private void addUploadedSubmissions() {
		for (int i = 0; i < 5; i++) {
			Trainee trainee = (Trainee) userDAO.validateCredentials("facultyNumber" + 0, "password");
			Lecture lecture = lectureDAO.getAllLectures().get(0);
			UploadedSubmission us = new UploadedSubmission(lecture, trainee, new Date(), null);
			uploadedsubmissionDAO.save(us);
		}
	}

	private void addLectures() {
		for (int i = 0; i < 5; i++) {
			Course course = courseDAO.getAllCourses().get(i);
			Lecture lecture = new Lecture("task" + i, new Date(), course);
			lectureDAO.save(lecture);
		}
	}

	private void validateCredentials() {
		User user = userDAO.validateCredentials("userName0", "password");
		user = userDAO.validateCredentials("facultyNumber0", "password");
		System.out.println();
	}

	private void addCourses() {
		for (int i = 0; i < 5; i++) {
			Trainer trainer = trainerDAO.findTrainerByUserName("userName" + 0);
			Course course = new Course("course" + i, "description"+i, true, trainer);
			if (i % 2 == 0) {
				course.setIsFavouriteToTrainer(true);
			}
			courseDAO.save(course);
		}
	}

	@Inject
	private TraineeDAO traineeDAO;

	private void addTrainees() {
		for (int i = 0; i < 5; i++) {
			Trainee trainee = new Trainee("facultyNumber" + i, "password", "name", "hristokirilov7@gmail.com",
					"fieldOfStudy");
			traineeDAO.save(trainee);
		}

	}

	private void addTrainers() {
		for (int i = 0; i < 5; i++) {
			Trainer trainer = new Trainer("userName" + i, "password", "degree", "name", "hristokirilov7@gmail.com");
			trainerDAO.save(trainer);
		}

	}
	///TODO
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCourses() {
		Test();
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
		
		String response = String.format("{\"name\": \"%s\"}", name);
		return Response.ok(response).build();
	}
	
}
