package bg.uni.sofia.fmi.homeworksystem.dao;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

import bg.uni.sofia.fmi.homeworksystem.model.Lecture;
import bg.uni.sofia.fmi.homeworksystem.model.Trainee;
import bg.uni.sofia.fmi.homeworksystem.model.UploadedSubmission;


@Singleton
public class UploadedSubmissionDAO {
	@Inject
	private EntityManager em;

	public void addUploadedSubmission(UploadedSubmission uploadedSubmission){
		em.persist(uploadedSubmission);
		Trainee trainee = uploadedSubmission.getTrainee();
		trainee.getUploadedSubmissions().add(uploadedSubmission);
		em.merge(trainee);
		Lecture lecture = uploadedSubmission.getLecture();
		lecture.getUploadedSubmissions().add(uploadedSubmission);
		em.merge(lecture);
	}
}