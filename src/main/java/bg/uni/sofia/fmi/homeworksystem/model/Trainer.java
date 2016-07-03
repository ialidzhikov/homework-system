package bg.uni.sofia.fmi.homeworksystem.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import bg.uni.sofia.fmi.homeworksystem.contracts.User;
import bg.uni.sofia.fmi.homeworksystem.utils.Role;

@Entity
@NamedQueries({ @NamedQuery(name = "getAllTrainers", query = "SELECT t FROM Trainer t"),
	@NamedQuery(name = "getTrainerByUsernameAndPass", query = "SELECT t FROM Trainer t WHERE t.userName = :username AND t.password = :password")})
public class Trainer implements Serializable, User {

	private static final long serialVersionUID = 6925326038482471293L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String userName;

	private String password;

	private String degree;

	private String name;

	private String email;

	@OneToMany(mappedBy = "trainer", fetch = FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	private Set<Course> courses = new HashSet<>();

	public Trainer() {
		super();
	}

	public Trainer(String userName, String password, String degree, String name, String email) {
		super();
		this.userName = userName;
		this.password = password;
		this.degree = degree;
		this.name = name;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
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

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((degree == null) ? 0 : degree.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		Trainer other = (Trainer) obj;
		if (degree == null) {
			if (other.degree != null)
				return false;
		} else if (!degree.equals(other.degree))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
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
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Trainer [id=" + id + ", userName=" + userName + ", password=" + password + ", degree=" + degree
				+ ", name=" + name + ", email=" + email + "]";
	}

	@Override
	public Role getUserRole() {
		return Role.TRAINER;
	}

}