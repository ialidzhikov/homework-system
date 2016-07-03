package bg.uni.sofia.fmi.homeworksystem.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({ @NamedQuery(name = "getAllCourses", query = "SELECT c FROM Course c") })
public class Course implements Serializable {

	private static final long serialVersionUID = 4419124196015509182L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private Boolean isFavouriteToTrainer;

	@ManyToOne
	private Trainer trainer;

	@OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
	private List<Lecture> lectures = new LinkedList<>();

	public Course() {
		super();
	}

	public List<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(List<Lecture> lectures) {
		this.lectures = lectures;
	}

	public Course(String name, Boolean isFavouriteToTrainer, Trainer trainer) {
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

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
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
		Course other = (Course) obj;
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
		return "Course [id=" + id + ", name=" + name + ", isFavouriteToTrainer=" + isFavouriteToTrainer + ", trainer="
				+ trainer + "]";
	}

}