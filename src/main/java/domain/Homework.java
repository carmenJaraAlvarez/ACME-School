
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

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Homework extends DomainEntity {

	@SafeHtml
	private String	title;
	@SafeHtml
	private String	description;
	private Date	moment;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@Temporal(TemporalType.DATE)
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}


	private Subject	subject;
	private ParentsGroup parentsGroup;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(final Subject subject) {
		this.subject = subject;
	}

	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public ParentsGroup getParentsGroup() {
		return parentsGroup;
	}

	public void setParentsGroup(ParentsGroup parentsGroup) {
		this.parentsGroup = parentsGroup;
	}

	
}
