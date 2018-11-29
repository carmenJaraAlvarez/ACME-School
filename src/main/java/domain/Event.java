
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
public class Event extends DomainEntity {

	private Date		moment;
	private Location	location;
	@SafeHtml
	private String		description;


	@NotNull
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(final Location location) {
		this.location = location;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}


	private ParentsGroup	parentsGroup;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public ParentsGroup getParentsGroup() {
		return this.parentsGroup;
	}

	public void setParentsGroup(final ParentsGroup parentsGroup) {
		this.parentsGroup = parentsGroup;
	}

}
