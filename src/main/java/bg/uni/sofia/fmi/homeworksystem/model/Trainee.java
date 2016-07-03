package bg.uni.sofia.fmi.homeworksystem.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import bg.uni.sofia.fmi.homeworksystem.contracts.Jsonable;
import bg.uni.sofia.fmi.homeworksystem.contracts.User;
import bg.uni.sofia.fmi.homeworksystem.utils.Role;


@Entity
@NamedQueries({ @NamedQuery(name = "getAllTrainees", query = "SELECT t FROM Trainee t"),
@NamedQuery(name = "getTraineeByUsernameAndPass", query = "SELECT t FROM Trainer t WHERE t.userName = :username AND t.password = :password")})
public class Trainee implements Serializable, User, Jsonable {

	private static final long serialVersionUID = -4509815250067041676L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String facultyNumber;

	private String password;

	private String name;

	private String email;

	private String fieldOfStudy;

	@OneToMany(mappedBy = "trainee", fetch = FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<UploadedSubmission> uploadedSubmissions = new LinkedList<>();

	@ManyToMany(targetEntity=Course.class, mappedBy="trainees", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List <Course> courses = new LinkedList<>();
	
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public Trainee() {
		super();
	}

	public Trainee(String facultyNumber, String password, String name, String email, String fieldOfStudy) {
		super();
		this.facultyNumber = facultyNumber;
		this.password = password;
		this.name = name;
		this.email = email;
		this.fieldOfStudy = fieldOfStudy;
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

	public String getFieldOfStudy() {
		return fieldOfStudy;
	}

	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
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
		result = prime * result + ((fieldOfStudy == null) ? 0 : fieldOfStudy.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (fieldOfStudy == null) {
			if (other.fieldOfStudy != null)
				return false;
		} else if (!fieldOfStudy.equals(other.fieldOfStudy))
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
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Trainee [id=" + id + ", facultyNumber=" + facultyNumber + ", password=" + password + ", name=" + name
				+ ", email=" + email + ", fieldOfStudy=" + fieldOfStudy + "]";
	}

	@Override
	public Role getUserRole() {
		return Role.TRAINEE;
	}
	
	@Override
	public String getUsername() {
		return this.getFacultyNumber();
	}
	
	@Override
	public JsonObject toJson() {
		final JsonObject trainee = new JsonObject();
		trainee.addProperty("id", this.getId());
		trainee.addProperty("facultyNumber", this.getFacultyNumber());
		trainee.addProperty("name", this.getName());
		trainee.addProperty("email", this.getEmail());
		trainee.addProperty("fieldOfStudy", this.getFieldOfStudy());
		
		JsonArray uploadedSubmissions = new JsonArray();
		for (UploadedSubmission uploadedSubmission : this.getUploadedSubmissions()) {
			uploadedSubmissions.add(uploadedSubmission.toJson());
		}
		trainee.add("uploadedSubmissions", uploadedSubmissions);
		
		return trainee;
	}
}
