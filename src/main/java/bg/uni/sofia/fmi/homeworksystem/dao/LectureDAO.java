package bg.uni.sofia.fmi.homeworksystem.dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

import bg.uni.sofia.fmi.homeworksystem.model.Course;
import bg.uni.sofia.fmi.homeworksystem.model.Lecture;


@Singleton
public class LectureDAO {
	@Inject
	private EntityManager em;
	
	public void addLecture (Lecture lecture){
		em.persist(lecture);
		Course course = lecture.getCourse();
		course.getLectures().add(lecture);
		em.merge(course);
	}
	
	public List<Lecture> getAllLectures (){
		return em.createNamedQuery("getAllLectures", Lecture.class).getResultList();
	}
	
	public void deleteAll() {
		// TODO Auto-generated method stub
		em.createQuery("DELETE FROM Lecture").executeUpdate();
	}
}
