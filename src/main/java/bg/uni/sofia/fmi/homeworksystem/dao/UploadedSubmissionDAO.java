package bg.uni.sofia.fmi.homeworksystem.dao;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import bg.uni.sofia.fmi.homeworksystem.email.RecordMarks;
import bg.uni.sofia.fmi.homeworksystem.email.RecordUploadSubmission;
import bg.uni.sofia.fmi.homeworksystem.model.Lecture;
import bg.uni.sofia.fmi.homeworksystem.model.Trainee;
import bg.uni.sofia.fmi.homeworksystem.model.UploadedSubmission;

@Singleton
public class UploadedSubmissionDAO extends AbstractDAO<UploadedSubmission> {

	@Override
	@RecordUploadSubmission
	public boolean save(final UploadedSubmission uploadedSubmission) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(uploadedSubmission);
		Trainee trainee = uploadedSubmission.getTrainee();
		trainee.getUploadedSubmissions().add(uploadedSubmission);
		em.merge(trainee);
		Lecture lecture = uploadedSubmission.getLecture();
		lecture.getUploadedSubmissions().add(uploadedSubmission);
		em.merge(lecture);
		try {
			transaction.commit();
		} catch (final RollbackException re) {
			if (transaction.isActive()) {
				transaction.rollback();
			}

			return false;
		}

		return true;
	}

	public List<UploadedSubmission> getAllUploadedSubmissions() {
		return em.createNamedQuery("getAllUploadedSubmissions", UploadedSubmission.class).getResultList();
	}

	public List<UploadedSubmission> getAllUploadedSubmissionsForTrainer(Long id) {
		return em.createNamedQuery("getAllUploadedSubmissionsForTrainer", UploadedSubmission.class)
				.setParameter("id", id).getResultList();
	}

	@RecordMarks
	public void evaluateUploadedSubmission(UploadedSubmission uploadedSubmission, Double mark) {
		em.getTransaction().begin();
		uploadedSubmission.setMark(mark);
		em.merge(uploadedSubmission);
		em.getTransaction().commit();
	}
}
