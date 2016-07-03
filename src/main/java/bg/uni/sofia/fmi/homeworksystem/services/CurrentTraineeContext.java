package bg.uni.sofia.fmi.homeworksystem.services;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import bg.uni.sofia.fmi.homeworksystem.model.Trainee;
import bg.uni.sofia.fmi.homeworksystem.utils.ServiceNullException;


@SessionScoped
public class CurrentTraineeContext implements Serializable{

	private static final long serialVersionUID = 1132840468100353184L;
	private static final String TRAINEE_NULL_MESSAGE = "Trainee can not be null.";
	private Trainee trainee;
	
	public Trainee getTrainee(){
		return trainee;
	}
	
	public void setTrainee(Trainee trainee){
		if(trainee == null){
			throw new ServiceNullException(TRAINEE_NULL_MESSAGE);
		}
		
		this.trainee = trainee;
	}

}
