package bg.uni.sofia.fmi.homeworksystem.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@NamedQueries({ @NamedQuery(name = "getAllSubmissions", query = "SELECT s FROM Submission s") })
public class Submission implements Serializable {

	private static final long serialVersionUID = -4918417868982503112L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Lecture lecture;

	private String task;

	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@OneToMany(mappedBy = "submission", fetch = FetchType.EAGER)
	private Set<UploadedSubmission> uploadedSubmissions = new HashSet<>();
	

	public Set<UploadedSubmission> getUploadedSubmissions() {
		return uploadedSubmissions;
	}


	public void setUploadedSubmissions(Set<UploadedSubmission> uploadedSubmissions) {
		this.uploadedSubmissions = uploadedSubmissions;
	}


	public Submission() {
		super();
	}


	public Submission(String task, Date endDate, Lecture lecture) {
		super();
		this.task = task;
		this.endDate = endDate;
		this.lecture = lecture;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Lecture getLecture() {
		return lecture;
	}


	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
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
		result = prime * result + ((lecture == null) ? 0 : lecture.hashCode());
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
		Submission other = (Submission) obj;
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
		if (lecture == null) {
			if (other.lecture != null)
				return false;
		} else if (!lecture.equals(other.lecture))
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
		return "Submission [id=" + id + ", lecture=" + lecture + ", task=" + task + ", endDate=" + endDate + "]";
	}
   
	
}
