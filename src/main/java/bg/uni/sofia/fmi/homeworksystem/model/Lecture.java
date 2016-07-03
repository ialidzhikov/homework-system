package bg.uni.sofia.fmi.homeworksystem.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@NamedQueries({ @NamedQuery(name = "getAllLectures", query = "SELECT l FROM Lecture l") })
public class Lecture implements Serializable {

	private static final long serialVersionUID = -4918417868982503112L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Course course;

	private String task;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	@OneToMany(mappedBy = "lecture", fetch = FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	private Set<UploadedSubmission> uploadedSubmissions = new HashSet<>();

	public Set<UploadedSubmission> getUploadedSubmissions() {
		return uploadedSubmissions;
	}

	public void setUploadedSubmissions(Set<UploadedSubmission> uploadedSubmissions) {
		this.uploadedSubmissions = uploadedSubmissions;
	}

	public Lecture() {
		super();
	}

	public Lecture(String task, Date endDate, Course course) {
		super();
		this.task = task;
		this.endDate = endDate;
		this.course = course;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + ((task == null) ? 0 : task.hashCode());
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
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (task == null) {
			if (other.task != null)
				return false;
		} else if (!task.equals(other.task))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lecture [id=" + id + ", course=" + course + ", task=" + task + ", endDate=" + endDate + "]";
	}

}
