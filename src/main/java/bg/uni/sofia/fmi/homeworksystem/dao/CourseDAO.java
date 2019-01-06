package bg.uni.sofia.fmi.homeworksystem.dao;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import bg.uni.sofia.fmi.homeworksystem.model.Course;
import bg.uni.sofia.fmi.homeworksystem.model.Trainer;


@Singleton
public class CourseDAO extends AbstractDAO<Course>{
	
	@Override
	public boolean save(final Course course){
		EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(course);
        Trainer trainer = course.getTrainer();
		trainer.getCourses().add(course);
		em.merge(trainer);
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
	
	public List<Course> getAllCourses (){
		return em.createNamedQuery("getAllCourses", Course.class).getResultList();
	}
	
	public List<Course> getCoursesForTrainer(Long id) {
		return em.createNamedQuery("getCoursesForTrainer", Course.class)
				.setParameter("id", id).getResultList();
	}
	
	@Override
	public boolean delete(final Course course) {
    	EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Trainer trainer = course.getTrainer();
		trainer.getCourses().remove(course);
		em.merge(trainer);
        em.remove(course);
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

	public void deleteAll() {
		// TODO Auto-generated method stub
		em.getTransaction().begin();
		em.createQuery("DELETE FROM Course").executeUpdate();
		em.getTransaction().commit();
	}
}
