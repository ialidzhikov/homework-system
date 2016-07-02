package bg.uni.sofia.fmi.homeworksystem.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQueries({ @NamedQuery(name = "getAllLectures", query = "SELECT l FROM Lecture l") })
public class Lecture implements Serializable {

	private static final long serialVersionUID = 3562220133819544012L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private Boolean isFavouriteToTrainer;

	@ManyToOne
	private Trainer trainer;

	@OneToMany(mappedBy = "lecture", fetch = FetchType.EAGER)
	private List<Submission> submissions = new LinkedList<>();

	public Lecture() {
		super();
	}

	public Lecture(String name, Boolean isFavouriteToTrainer, Trainer trainer) {
		super();
		this.name = name;
		this.isFavouriteToTrainer = isFavouriteToTrainer;
		this.trainer = trainer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsFavouriteToTrainer() {
		return isFavouriteToTrainer;
	}

	public void setIsFavouriteToTrainer(Boolean isFavouriteToTrainer) {
		this.isFavouriteToTrainer = isFavouriteToTrainer;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer teacher) {
		this.trainer = teacher;
	}

	public List<Submission> getSubmissions() {
		return submissions;
	}

	public void setSubmissions(List<Submission> homeworks) {
		this.submissions = homeworks;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isFavouriteToTrainer == null) ? 0 : isFavouriteToTrainer.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((trainer == null) ? 0 : trainer.hashCode());
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
		Lecture other = (Lecture) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isFavouriteToTrainer == null) {
			if (other.isFavouriteToTrainer != null)
				return false;
		} else if (!isFavouriteToTrainer.equals(other.isFavouriteToTrainer))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (trainer == null) {
			if (other.trainer != null)
				return false;
		} else if (!trainer.equals(other.trainer))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Subject [id=" + id + ", name=" + name + ", isFavouriteToTeacher=" + isFavouriteToTrainer + ", trainer="
				+ trainer + "]";
	}

}
