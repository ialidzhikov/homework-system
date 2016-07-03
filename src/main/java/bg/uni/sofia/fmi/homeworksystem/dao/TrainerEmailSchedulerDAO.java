package bg.uni.sofia.fmi.homeworksystem.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import bg.uni.sofia.fmi.homeworksystem.model.TrainerEmailScheduler;

public class TrainerEmailSchedulerDAO extends AbstractDAO<TrainerEmailScheduler> {

	public List<TrainerEmailScheduler> getAllTrainerEmailSchedulers() {
		String txtQuery = "SELECT t FROM TrainerEmailScheduler t";
		TypedQuery<TrainerEmailScheduler> query = em.createQuery(txtQuery, TrainerEmailScheduler.class);
		try {
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	public void deleteAll() {
		em.getTransaction().begin();
		em.createQuery("DELETE FROM TrainerEmailScheduler").executeUpdate();
		em.getTransaction().commit();
	}
}
