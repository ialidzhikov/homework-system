package bg.uni.sofia.fmi.homeworksystem.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bg.uni.sofia.fmi.homeworksystem.dao.LectureDAO;
import bg.uni.sofia.fmi.homeworksystem.dao.UploadedSubmissionDAO;
import bg.uni.sofia.fmi.homeworksystem.model.Lecture;
import bg.uni.sofia.fmi.homeworksystem.model.Trainee;
import bg.uni.sofia.fmi.homeworksystem.model.UploadedSubmission;

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
	
	@POST
	@Path("/{lectureId}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addUsersFromFile(@FormDataParam("submission") final InputStream submissionInpStream,
            @FormDataParam("submission") final FormDataContentDisposition fileDetail, @PathParam("lectureId") String lectureIdStr) {
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
