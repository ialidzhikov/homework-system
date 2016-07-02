package bg.uni.sofia.fmi.homeworksystem.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Trainee implements Serializable {

	private static final long serialVersionUID = -4509815250067041676L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String facultyNumber;

	private String password;

	private String name;

	private String email;

	private String fieldOfStudy;

	@OneToMany(orphanRemoval=true, cascade = CascadeType.ALL)
	@JoinColumn(name="TraineeID", referencedColumnName = "id")
	private Set<Lecture> assignedLectures = new HashSet<>();

	@OneToMany(mappedBy = "trainee", fetch = FetchType.EAGER)
	private List<UploadedSubmission> uploadedSubmissions = new LinkedList<>();

	public Trainee() {
		super();
	}

	public Trainee(String facultyNumber, String name, String password, String email, String objectOfStudy) {
		super();
		this.facultyNumber = facultyNumber;
		this.name = name;
		this.password = password;
		this.email = email;
		this.fieldOfStudy = objectOfStudy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFacultyNumber() {
		return facultyNumber;
	}

	public void setFacultyNumber(String facultyNumber) {
		this.facultyNumber = facultyNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getObjectOfStudy() {
		return fieldOfStudy;
	}

	public void setObjectOfStudy(String objectOfStudy) {
		this.fieldOfStudy = objectOfStudy;
	}

	public Set<Lecture> getAssignedLectures() {
		return assignedLectures;
	}

	public void setAssignedLectures(Set<Lecture> assignedLectures) {
		this.assignedLectures = assignedLectures;
	}

	public List<UploadedSubmission> getUploadedSubmissions() {
		return uploadedSubmissions;
	}

	public void setUploadedSubmissions(List<UploadedSubmission> uploadedSubmissions) {
		this.uploadedSubmissions = uploadedSubmissions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((facultyNumber == null) ? 0 : facultyNumber.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((fieldOfStudy == null) ? 0 : fieldOfStudy.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		Trainee other = (Trainee) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (facultyNumber == null) {
			if (other.facultyNumber != null)
				return false;
		} else if (!facultyNumber.equals(other.facultyNumber))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (fieldOfStudy == null) {
			if (other.fieldOfStudy != null)
				return false;
		} else if (!fieldOfStudy.equals(other.fieldOfStudy))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", facultyNumber=" + facultyNumber + ", name=" + name + ", email=" + email
				+ ", objectOfStudy=" + fieldOfStudy + "]";
	}

}
