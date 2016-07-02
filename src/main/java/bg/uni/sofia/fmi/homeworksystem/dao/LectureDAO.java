package bg.uni.sofia.fmi.homeworksystem.dao;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bg.uni.sofia.fmi.homeworksystem.model.Lecture;
import bg.uni.sofia.fmi.homeworksystem.model.Trainer;


@Singleton
public class LectureDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public void addLecture (Lecture lecture){		
		em.persist(lecture);
		Trainer trainer = lecture.getTrainer();
		trainer.getLectures().add(lecture);
		em.merge(trainer);
	}
	
	public List<Lecture> getAllLectures (){
		return em.createNamedQuery("getAllLectures", Lecture.class).getResultList();
	}

	public void deleteAll() {
		// TODO Auto-generated method stub
		em.createQuery("DELETE FROM Lecture").executeUpdate();
	}
}
