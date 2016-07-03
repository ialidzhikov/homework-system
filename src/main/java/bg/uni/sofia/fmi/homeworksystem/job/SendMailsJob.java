package bg.uni.sofia.fmi.homeworksystem.job;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.TypedQuery;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bg.uni.sofia.fmi.homeworksystem.contracts.User;
import bg.uni.sofia.fmi.homeworksystem.email.EmailSender;
import bg.uni.sofia.fmi.homeworksystem.model.Lecture;
import bg.uni.sofia.fmi.homeworksystem.model.Trainee;
import bg.uni.sofia.fmi.homeworksystem.model.Trainer;
import bg.uni.sofia.fmi.homeworksystem.model.TrainerEmailScheduler;

public class SendMailsJob extends AbstractJob<User> {
	private final Logger LOGGER = LoggerFactory.getLogger(SendMailsJob.class);

	public void execute(JobExecutionContext jExeCtx) throws JobExecutionException {
		List<TrainerEmailScheduler> trainerEmailSchedulers = getAllTrainerEmailSchedulers();
		Map<Trainer, List<TrainerEmailScheduler>> emailRecievers = new HashMap<>();
		for (TrainerEmailScheduler trainerEmailScheduler : trainerEmailSchedulers) {
			Trainer trainer = trainerEmailScheduler.getLecture().getCourse().getTrainer();
			if (!emailRecievers.containsKey(trainer)) {
				emailRecievers.put(trainer, new LinkedList<TrainerEmailScheduler>());
			}
			emailRecievers.get(trainer).add(trainerEmailScheduler);
		}
		for (Entry<Trainer, List<TrainerEmailScheduler>> tupple : emailRecievers.entrySet()){
			String emailBody = "";
			Trainer trainer = tupple.getKey();
			List<TrainerEmailScheduler> emailSchedulers = tupple.getValue();
			for (TrainerEmailScheduler trainerEmailScheduler : emailSchedulers){
				Trainee trainee = trainerEmailScheduler.getTrainee();
				Lecture lecture = trainerEmailScheduler.getLecture();
				emailBody += trainee.getName() + " has submited submission for " + lecture.getTask() + ".\n";
			}
			EmailSender.sendEmail(trainer.getEmail(), "Daily submissions", emailBody);
		}
		deleteAll();
		trainerEmailSchedulers = getAllTrainerEmailSchedulers();
	}

	private List<TrainerEmailScheduler> getAllTrainerEmailSchedulers() {
		String txtQuery = "SELECT t FROM TrainerEmailScheduler t";
		TypedQuery<TrainerEmailScheduler> query = em.createQuery(txtQuery, TrainerEmailScheduler.class);
		try {
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	private void deleteAll() {
		em.getTransaction().begin();
		em.createQuery("DELETE FROM TrainerEmailScheduler").executeUpdate();
		em.getTransaction().commit();
	}
}
