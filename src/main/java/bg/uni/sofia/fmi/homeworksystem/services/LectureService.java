package bg.uni.sofia.fmi.homeworksystem.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bg.uni.sofia.fmi.homeworksystem.dao.CourseDAO;
import bg.uni.sofia.fmi.homeworksystem.dao.LectureDAO;
import bg.uni.sofia.fmi.homeworksystem.model.Course;
import bg.uni.sofia.fmi.homeworksystem.model.Lecture;

@Path("hmwsrest/v1/course")
public class LectureService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(SubmissionService.class);
	private final String ERROR_PARSING_DATE_MESSAGE = "Error accured while date is being parsed to specific format.";
	
	@Inject
	private CourseDAO courseDAO;
	
	@Inject
	private LectureDAO lectureDAO;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{courseId}/lecture")
	public Response getLecturesByCourseId(@PathParam("courseId") String courseIdStr) {
		long courseId = Long.parseLong(courseIdStr);

		Course course = courseDAO.getById(Course.class, courseId);
		if (course == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.ok(course.getLectures()).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/lecture")
	public Response addLecture(String data) {
		JsonObject userData = new JsonParser().parse(data).getAsJsonObject();
		long courseId = Long.parseLong(userData.get("courseId").getAsString());
		String name = userData.get("name").getAsString();
		String task = userData.get("task").getAsString();
		String endDateStr = userData.get("endDate").getAsString();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = null;
		try {
			endDate = dateFormat.parse(endDateStr);
		} catch (ParseException e) {
			LOGGER.error(ERROR_PARSING_DATE_MESSAGE ,e);
		}
		
		Course course = courseDAO.getById(Course.class, courseId);
		Lecture newLecture = new Lecture(name, task, endDate, course);
		lectureDAO.save(newLecture);
		return Response.ok().build();
	}
}
