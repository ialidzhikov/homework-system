package bg.uni.sofia.fmi.homeworksystem.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import bg.uni.sofia.fmi.homeworksystem.model.Trainee;


@Singleton
public class TraineeDAO {

	@Inject
	private EntityManager em;

	public void addTrainee(Trainee trainee) {
		trainee.setPassword(getHashedPassword(trainee.getPassword()));
		em.persist(trainee);
	}

	public Trainee validateTraineeCredentials(String facultyNumber, String password) {
		String txtQuery = "SELECT t FROM Trainee t WHERE t.facultyNumber=:facultyNumber AND t.password=:password";
		TypedQuery<Trainee> query = em.createQuery(txtQuery, Trainee.class);
		query.setParameter("facultyNumber", facultyNumber);
		query.setParameter("password", getHashedPassword(password));
		return queryTrainee(query);
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
}
