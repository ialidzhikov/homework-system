package bg.uni.sofia.fmi.homeworksystem.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bg.uni.sofia.fmi.homeworksystem.contracts.User;
import bg.uni.sofia.fmi.homeworksystem.dao.LectureDAO;
import bg.uni.sofia.fmi.homeworksystem.dao.TrainerDAO;
import bg.uni.sofia.fmi.homeworksystem.dao.UploadedSubmissionDAO;
import bg.uni.sofia.fmi.homeworksystem.model.Lecture;
import bg.uni.sofia.fmi.homeworksystem.model.Trainee;
import bg.uni.sofia.fmi.homeworksystem.model.Trainer;
import bg.uni.sofia.fmi.homeworksystem.model.UploadedSubmission;
import bg.uni.sofia.fmi.homeworksystem.utils.Role;

@RequestScoped
@Path("hmwsrest/v1/submission")
public class SubmissionService {

	private final Logger LOGGER = LoggerFactory.getLogger(SubmissionService.class);
	private final String INCOMPATIBLE_FILE_ERROR_TEXT = "INCOMPATIBLE_FILE";
	private final String TRAINER_CANNOT_UPLOAD_HOMEWORKS_ERROR_MESSAGE = "TRAINER CANNOT UPLOAD HOMEWORKS";
	
	@Inject
	private CurrentUserContext currentUserCtx;
	
	@Inject
	private UploadedSubmissionDAO upSDAO;
	
	@Inject
	private LectureDAO lectureDAO;
	
	@Inject
	private TrainerDAO trainerDAO;
	
	@POST
	@Path("/{lectureId}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addUsersFromFile(@FormDataParam("file") final InputStream submissionInpStream,
            @FormDataParam("file") final FormDataContentDisposition fileDetail, @PathParam("lectureId") String lectureIdStr) {
		byte[] upSubmissionAsByteArray = this.inputStreamToByteArray(submissionInpStream);
		if (upSubmissionAsByteArray == null) {
			return Response.status(400).entity(INCOMPATIBLE_FILE_ERROR_TEXT).build();
		}
		
		long lectureId = Long.parseLong(lectureIdStr);
		Lecture lecture = lectureDAO.getById(Lecture.class, lectureId);
		Trainee currentUser;
		
		try {
			currentUser = (Trainee)currentUserCtx.getUser();
		} catch (ClassCastException e) {
			LOGGER.error(TRAINER_CANNOT_UPLOAD_HOMEWORKS_ERROR_MESSAGE, e);
			return Response.status(Status.FORBIDDEN).entity(TRAINER_CANNOT_UPLOAD_HOMEWORKS_ERROR_MESSAGE).build();
		}
		
		UploadedSubmission upSubmission = new UploadedSubmission(lecture, currentUser, new Date(), upSubmissionAsByteArray);
		upSDAO.save(upSubmission);
		return Response.ok().build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSubmissions() {
		User user = currentUserCtx.getUser();
		List<UploadedSubmission> uploadedSubmissions = null;
		if (user.getUserRole().equals(Role.TRAINEE)) {
			Trainee trainee = (Trainee) user;
			
			uploadedSubmissions = trainee.getUploadedSubmissions();
			
		} else if (user.getUserRole().equals(Role.TRAINER)) {
			Trainer trainer = (Trainer) user;
			
			uploadedSubmissions = trainerDAO.getAllUploadedSubmisiions(trainer);
		}
		
		JsonArray uploadedSubmissionsJson = new JsonArray();
		for (UploadedSubmission uploadedSubmission : uploadedSubmissions) {
			uploadedSubmissionsJson.add(uploadedSubmission.toJson());
		}
		
		return Response.ok(uploadedSubmissionsJson.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSubmissionById(@PathParam("id") Long id) {
		UploadedSubmission uploadedSubmission = upSDAO.getById(UploadedSubmission.class, id);
		if (uploadedSubmission == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok(uploadedSubmission.toJson().toString(), MediaType.APPLICATION_JSON).build();
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putSubmissionMark(@PathParam("id") Long id, String data) {
		JsonObject submissionJson = new JsonParser().parse(data).getAsJsonObject();
		Double mark = submissionJson.get("mark").getAsDouble();
		
		UploadedSubmission uploadedSubmission = upSDAO.getById(UploadedSubmission.class, id);
		if (uploadedSubmission == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		upSDAO.evaluateUploadedSubmission(uploadedSubmission, mark);
		return Response.ok("{}").build();
	}
	
	@GET
	@Path("/{id}/file")
	public Response getSubmissionFile(@PathParam("id") Long id) {
		final UploadedSubmission uploadedSubmission = upSDAO.getById(UploadedSubmission.class, id);
		
		StreamingOutput fileStream = new StreamingOutput() {
			
			@Override
			public void write(OutputStream outputStream) throws IOException, WebApplicationException {
				byte[] fileBytes = uploadedSubmission.getFile();
				outputStream.write(fileBytes);
				outputStream.flush();
			}
		};
		
		return Response
				.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
				.header("content-disposition", "attachment; filename = homework.zip")
				.build();
	}
	
	private byte[] inputStreamToByteArray(InputStream is) {
		int nRead;
		byte[] data = new byte[16384];
		try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()){
			while ((nRead = is.read(data, 0, data.length)) != -1) {
			  buffer.write(data, 0, nRead);
			  buffer.flush();
			  buffer.close();
			}
			
			return buffer.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}
}
