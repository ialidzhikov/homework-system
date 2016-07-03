package bg.uni.sofia.fmi.homeworksystem.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: TrainerEmailScheduler
 *
 */
@Entity
public class TrainerEmailScheduler implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Trainee trainee;
	
	private Lecture lecture;

	public TrainerEmailScheduler() {
		super();
	}

	public TrainerEmailScheduler(Trainee trainee, Lecture lecture) {
		super();
		this.trainee = trainee;
		this.lecture = lecture;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Trainee getTrainee() {
		return trainee;
	}

	public void setTrainee(Trainee trainee) {
		this.trainee = trainee;
	}

	public Lecture getLecture() {
		return lecture;
	}

	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lecture == null) ? 0 : lecture.hashCode());
		result = prime * result + ((trainee == null) ? 0 : trainee.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrainerEmailScheduler other = (TrainerEmailScheduler) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lecture == null) {
			if (other.lecture != null)
				return false;
		} else if (!lecture.equals(other.lecture))
			return false;
		if (trainee == null) {
			if (other.trainee != null)
				return false;
		} else if (!trainee.equals(other.trainee))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TrainerEmailScheduler [id=" + id + ", trainee=" + trainee + ", lecture=" + lecture + "]";
	}

}
