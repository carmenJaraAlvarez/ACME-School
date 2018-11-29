
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Mark extends DomainEntity {

	private Date	date;
	private String	comment;
	private double	value;


	@NotNull
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDate() {
		return this.date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	@Range(min = 0, max = 10)
	public double getValue() {
		return this.value;
	}

	public void setValue(final double value) {
		this.value = value;
	}


	private Student	student;
	private Teacher	teacher;
	private Subject	subject;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Student getStudent() {
		return this.student;
	}

	public void setStudent(final Student student) {
		this.student = student;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(final Teacher teacher) {
		this.teacher = teacher;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

}
