package bg.uni.sofia.fmi.homeworksystem.dao;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bg.uni.sofia.fmi.homeworksystem.model.Lecture;
import bg.uni.sofia.fmi.homeworksystem.model.Submission;


@Singleton
public class SubmissionDAO {
	@PersistenceContext
	private EntityManager em;
	
	public void addSubmission (Submission submission){
		em.persist(submission);
		Lecture lecture = submission.getLecture();
		lecture.getSubmissions().add(submission);
		em.merge(lecture);
	}
	
	public List<Submission> getAllSubmissions (){
		return em.createNamedQuery("getAllSubmissions", Submission.class).getResultList();
	}
	
	public void deleteAll() {
		// TODO Auto-generated method stub
		em.createQuery("DELETE FROM Submission").executeUpdate();
	}
}
