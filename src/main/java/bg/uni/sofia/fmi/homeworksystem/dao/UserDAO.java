package bg.uni.sofia.fmi.homeworksystem.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import bg.uni.sofia.fmi.homeworksystem.contracts.User;
import bg.uni.sofia.fmi.homeworksystem.model.Trainee;
import bg.uni.sofia.fmi.homeworksystem.model.Trainer;

@Singleton
public class UserDAO {
	@Inject
	private EntityManager em;
	
	public User validateCredentials(String userName, String password) {
		String hashedPassword = getHashedPassword(password);
		User user = lookInTrainer(userName, hashedPassword);
	
		if (user == null){
			user = lookInTrainee(userName, hashedPassword);
		}
		return user;
	}
	
	private User lookInTrainer (String userName, String hashedPassword){
		String txtQuery = "SELECT t FROM Trainer t WHERE t.userName=:userName AND t.password=:password";
		TypedQuery<Trainer> queryTrainer = em.createQuery(txtQuery, Trainer.class);
		queryTrainer.setParameter("userName", userName);
		queryTrainer.setParameter("password", hashedPassword);
		try{
			return queryTrainer.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	private User lookInTrainee (String userName, String hashedPassword){
		String txtQuery = "SELECT t FROM Trainee t WHERE t.facultyNumber=:userName AND t.password=:password";
		TypedQuery<Trainee> queryTrainee = em.createQuery(txtQuery, Trainee.class);
		queryTrainee.setParameter("userName", userName);
		queryTrainee.setParameter("password", hashedPassword);
		try{
			return queryTrainee.getSingleResult();
		} catch (Exception e){
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
