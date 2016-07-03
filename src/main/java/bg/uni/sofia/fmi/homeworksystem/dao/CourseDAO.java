package bg.uni.sofia.fmi.homeworksystem.dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

import bg.uni.sofia.fmi.homeworksystem.model.Course;
import bg.uni.sofia.fmi.homeworksystem.model.Trainer;


@Singleton
public class CourseDAO {
	
	@Inject
	private EntityManager em;
	
	public void addCourse (Course course){		
		em.persist(course);
		Trainer trainer = course.getTrainer();
		trainer.getCourses().add(course);
		em.merge(trainer);
	}
	
	public List<Course> getAllCourses (){
		return em.createNamedQuery("getAllCourses", Course.class).getResultList();
	}

	public void deleteAll() {
		// TODO Auto-generated method stub
		em.createQuery("DELETE FROM Course").executeUpdate();
	}
}
