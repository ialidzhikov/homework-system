package bg.uni.sofia.fmi.homeworksystem.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import bg.uni.sofia.fmi.homeworksystem.model.Trainer;

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
		String txtQuery = "SELECT t FROM Trainer t WHERE t.username=:userName";
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
			return em.createNamedQuery("getTrainerByUsernameAndPass", Trainer.class).setParameter("username", username)
					.setParameter("password", password).getSingleResult();
		} catch (NoResultException e) {
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
