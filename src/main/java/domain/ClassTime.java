
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class ClassTime extends DomainEntity {

	@SafeHtml
	private String	day;
	@SafeHtml
	private String	hour;


	@NotBlank
	public String getDay() {
		return this.day;
	}

	public void setDay(final String day) {
		this.day = day;
	}

	//"HH:mm-HH:mm" 24 H format
	@NotBlank
	@Pattern(regexp = "[0-2][0-9]:[0-5][0-9]-[0-2][0-9]:[0-5][0-9]")
	public String getHour() {
		return this.hour;
	}

	public void setHour(final String hour) {
		this.hour = hour;
	}


	private ClassGroup	classGroup;
	private Subject		subject;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public ClassGroup getClassGroup() {
		return this.classGroup;
	}

	public void setClassGroup(final ClassGroup classGroup) {
		this.classGroup = classGroup;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(final Subject subject) {
		this.subject = subject;
	}

}
