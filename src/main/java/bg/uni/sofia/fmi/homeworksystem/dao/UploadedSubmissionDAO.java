package bg.uni.sofia.fmi.homeworksystem.dao;

import javax.inject.Singleton;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import bg.uni.sofia.fmi.homeworksystem.model.Lecture;
import bg.uni.sofia.fmi.homeworksystem.model.Trainee;
import bg.uni.sofia.fmi.homeworksystem.model.UploadedSubmission;


@Singleton
public class UploadedSubmissionDAO extends AbstractDAO<UploadedSubmission>{
	
	@Override
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
}