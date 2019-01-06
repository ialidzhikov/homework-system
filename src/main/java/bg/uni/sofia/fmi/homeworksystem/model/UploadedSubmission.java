package bg.uni.sofia.fmi.homeworksystem.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import bg.uni.sofia.fmi.homeworksystem.contracts.EntityObject;

@Entity
@NamedQueries({ @NamedQuery(name = "getAllUploadedSubmissions", query = "SELECT u FROM UploadedSubmission u"),
		@NamedQuery(name = "getAllUploadedSubmissionsForTrainer", query = "SELECT u FROM Trainer t INNER JOIN t.courses c INNER JOIN c.lectures l INNER JOIN l.uploadedSubmissions u WHERE t.id=:id") })
public class UploadedSubmission implements Serializable, EntityObject {

	private static final long serialVersionUID = -6894627518598913945L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Lecture lecture;

	@ManyToOne
	private Trainee trainee;

	@JsonFormat(shape = Shape.STRING, pattern = "dd-MMM-yyyy HH:mm")
	@Temporal(TemporalType.DATE)
	private Date uploadDate = new Date();

	private Double mark;

	@Lob
	private byte[] file;

	public UploadedSubmission() {
		super();
	}

	public UploadedSubmission(Lecture lecture, Trainee trainee, Date uploadDate, byte[] file) {
		super();
		this.lecture = lecture;
		this.trainee = trainee;
		this.uploadDate = uploadDate;
		this.file = file;
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

	public Trainee getTrainee() {
		return trainee;
	}

	public void setTrainee(Trainee trainee) {
		this.trainee = trainee;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Double getMark() {
		return mark;
	}

	public void setMark(Double mark) {
		this.mark = mark;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(file);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mark == null) ? 0 : mark.hashCode());
		result = prime * result + ((lecture == null) ? 0 : lecture.hashCode());
		result = prime * result + ((trainee == null) ? 0 : trainee.hashCode());
		result = prime * result + ((uploadDate == null) ? 0 : uploadDate.hashCode());
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
		UploadedSubmission other = (UploadedSubmission) obj;
		if (!Arrays.equals(file, other.file))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mark == null) {
			if (other.mark != null)
				return false;
		} else if (!mark.equals(other.mark))
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
		if (uploadDate == null) {
			if (other.uploadDate != null)
				return false;
		} else if (!uploadDate.equals(other.uploadDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UploadedSubmission [id=" + id + ", lecture=" + lecture.getTask() + ", trainee=" + trainee.getName()
				+ ", uploadDate=" + uploadDate + ", mark=" + mark + "]";
	}
}
