package bg.uni.sofia.fmi.homeworksystem.dao;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import bg.uni.sofia.fmi.homeworksystem.model.Course;
import bg.uni.sofia.fmi.homeworksystem.model.Lecture;


@Singleton
public class LectureDAO extends AbstractDAO<Lecture>{
	
	@Override
	public boolean save(final Lecture lecture){
		EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(lecture);
        Course course = lecture.getCourse();
		course.getLectures().add(lecture);
		em.merge(course);
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
	
	@Override
	public boolean delete(final Lecture lecture) {
    	EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Course course = lecture.getCourse();
		course.getLectures().remove(lecture);
		em.merge(course);
        em.remove(lecture);
        try {
            em.getTransaction().commit();
        } catch (final RollbackException re) {
        	if (transaction.isActive()) {
        		transaction.rollback();
        	}
        	
            return false;
        }
        
        return true;
    }
	
	public List<Lecture> getAllLectures (){
		return em.createNamedQuery("getAllLectures", Lecture.class).getResultList();
	}
}
