package bg.uni.sofia.fmi.homeworksystem.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import bg.uni.sofia.fmi.homeworksystem.model.Course;
import bg.uni.sofia.fmi.homeworksystem.model.Trainee;


@ApplicationScoped
public class TraineeDAO extends AbstractDAO<Trainee>{
	
	@Override
	public boolean save(final Trainee trainee) {
    	EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        trainee.setPassword(getHashedPassword(trainee.getPassword()));
        em.persist(trainee);
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

	public Trainee findStudentByFacultyNumber(String facultyNumber) {
		String txtQuery = "SELECT t FROM Trainee t WHERE t.facultyNumber=:facultyNumber";
		TypedQuery<Trainee> query = em.createQuery(txtQuery, Trainee.class);
		query.setParameter("facultyNumber", facultyNumber);
		return queryTrainee(query);
	}

	public List<Trainee> getAllTrainees() {
		String txtQuery = "SELECT t FROM Trainee t";
		TypedQuery<Trainee> query = em.createQuery(txtQuery, Trainee.class);
		return query.getResultList();
	}

	private Trainee queryTrainee(TypedQuery<Trainee> query) {
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	private String getHashedPassword(String password) {
		MessageDigest mda;
		try {
			mda = MessageDigest.getInstance("SHA-512");
			password = new String(mda.digest(password.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return password;
	}
	
	public void addCourse (Trainee trainee, Course course){
		em.getTransaction().begin();
		trainee.getCourses().add(course);
		em.merge(trainee);
		course.getTrainees().add(trainee);
		em.merge(course);
		em.getTransaction().commit();
	}
}
