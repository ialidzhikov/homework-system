package bg.uni.sofia.fmi.homeworksystem.services;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import bg.uni.sofia.fmi.homeworksystem.contracts.User;
import bg.uni.sofia.fmi.homeworksystem.model.Trainer;
import bg.uni.sofia.fmi.homeworksystem.utils.ServiceNullException;

@SessionScoped
public class CurrentTrainerContext implements Serializable{
	private static final long serialVersionUID = 9112465614104368287L;
	private static final String TRAINER_NULL_MESSAGE = "Trainer can not be null.";
	private Trainer trainer;

	public User getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		if(trainer == null) {
			throw new ServiceNullException(TRAINER_NULL_MESSAGE);
		}
		this.trainer = trainer;
	}

}
