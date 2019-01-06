package bg.uni.sofia.fmi.homeworksystem.services;

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

import bg.uni.sofia.fmi.homeworksystem.dao.CourseDAO;
import bg.uni.sofia.fmi.homeworksystem.dao.LectureDAO;
import bg.uni.sofia.fmi.homeworksystem.model.Course;
import bg.uni.sofia.fmi.homeworksystem.model.Lecture;

@Path("hmwsrest/v1/courses")
public class LectureService {

	@Inject
	private CourseDAO courseDAO;

	@Inject
	private LectureDAO lectureDAO;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/lectures")
	public Response getLecturesByCourseId(@PathParam("id") Long id) {
		Course course = courseDAO.getById(Course.class, id);
		if (course == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.ok(course.getLectures()).build();
	}

	@POST
	@Path("{id}/lectures")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addLecture(@PathParam("id") Long id, Lecture lecture) {
		Course course = courseDAO.getById(Course.class, id);
		lecture.setCourse(course);
		lectureDAO.save(lecture);

		return Response.status(Status.CREATED).build();
	}
}
