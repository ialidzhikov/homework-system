package bg.uni.sofia.fmi.homeworksystem.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import bg.uni.sofia.fmi.homeworksystem.model.Lecture;
import bg.uni.sofia.fmi.homeworksystem.model.Submission;
import bg.uni.sofia.fmi.homeworksystem.model.Trainer;
import bg.uni.sofia.fmi.homeworksystem.model.UploadedSubmission;


@Singleton
public class TrainerDAO {
	
	@Inject
	private EntityManager em;

	public void addTrainer(Trainer trainer) {
		trainer.setPassword(getHashedPassword(trainer.getPassword()));
		em.persist(trainer);
	}

	public Trainer validateTrainerCredentials(String userName, String password) {
		String txtQuery = "SELECT t FROM Trainer t WHERE t.userName=:userName AND t.password=:password";
		TypedQuery<Trainer> query = em.createQuery(txtQuery, Trainer.class);
		query.setParameter("userName", userName);
		query.setParameter("password", getHashedPassword(password));
		return queryTrainer(query);
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

	public List<UploadedSubmission> getAllUploadedSubmisiions(Trainer trainer) {
		List<UploadedSubmission> uploadedSubmissions = new ArrayList<>();
		Set<Lecture> lectures = trainer.getLectures();
		List<Submission> submissions = new LinkedList<>();
		for (Lecture lecture : lectures) {
			submissions.addAll(lecture.getSubmissions());
		}
		for (Submission submission : submissions) {
			uploadedSubmissions.addAll(submission.getUploadedSubmissions());
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
