package bg.uni.sofia.fmi.homeworksystem.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.inject.Singleton;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import bg.uni.sofia.fmi.homeworksystem.model.Course;
import bg.uni.sofia.fmi.homeworksystem.model.Lecture;
import bg.uni.sofia.fmi.homeworksystem.model.Trainee;
import bg.uni.sofia.fmi.homeworksystem.model.Trainer;
import bg.uni.sofia.fmi.homeworksystem.model.UploadedSubmission;


@Singleton
public class TrainerDAO extends AbstractDAO<Trainer> {
	
	@Override
	public boolean save(final Trainer trainer) {
    	EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        trainer.setPassword(getHashedPassword(trainer.getPassword()));
        em.persist(trainer);
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

	public Trainer findTrainerByUserName(String userName) {
		String txtQuery = "SELECT t FROM Trainer t WHERE t.userName=:userName";
		TypedQuery<Trainer> query = em.createQuery(txtQuery, Trainer.class);
		query.setParameter("userName", userName);
		return queryTrainer(query);
	}

	private Trainer queryTrainer(TypedQuery<Trainer> query) {
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public List<Trainer> getAllTrainers() {
		return em.createNamedQuery("getAllTrainers", Trainer.class).getResultList();
	}
	
	public Trainer getTrainerByUsernameAndPass(String username, String password) {
		try {
			return em.createNamedQuery("getTrainerByUsernameAndPass", Trainer.class).setParameter("username", username).setParameter("password", password).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<UploadedSubmission> getAllUploadedSubmisiions(Trainer trainer) {
		List<UploadedSubmission> uploadedSubmissions = new ArrayList<>();
		Set<Course> courses = trainer.getCourses();
		List<Lecture> lectures = new LinkedList<>();
		for (Course course : courses) {
			lectures.addAll(course.getLectures());
		}
		for (Lecture lecture : lectures) {
			uploadedSubmissions.addAll(lecture.getUploadedSubmissions());
		}
		Collections.sort(uploadedSubmissions, new Comparator<UploadedSubmission>() {

			@Override
			public int compare(UploadedSubmission o1, UploadedSubmission o2) {
				return o2.getUploadDate().compareTo(o1.getUploadDate());
			}
		});
		return uploadedSubmissions;
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

	/// TODO
	public void deleteAll() {
		em.createQuery("DELETE FROM Trainer").executeUpdate();

	}
}
