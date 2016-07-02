package bg.uni.sofia.fmi.homeworksystem.dao;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bg.uni.sofia.fmi.homeworksystem.model.Submission;
import bg.uni.sofia.fmi.homeworksystem.model.Trainee;
import bg.uni.sofia.fmi.homeworksystem.model.UploadedSubmission;


@Singleton
public class UploadedSubmissionDAO {
	@PersistenceContext
	private EntityManager em;

	public void addUploadedSubmission(UploadedSubmission uploadedSubmission){
		em.persist(uploadedSubmission);
		Trainee trainee = uploadedSubmission.getTrainee();
		trainee.getUploadedSubmissions().add(uploadedSubmission);
		em.merge(trainee);
		Submission submission = uploadedSubmission.getSubmission();
		submission.getUploadedSubmissions().add(uploadedSubmission);
		em.merge(submission);
	}
}