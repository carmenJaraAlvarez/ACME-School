
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Subject extends DomainEntity {

	@SafeHtml
	public String	name;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}


	private Collection<Homework>	homeworks;
	private Level					level;

	private Collection<ClassTime>	classTimes;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "subject")
	public Collection<Homework> getHomeworks() {
		return this.homeworks;
	}

	public void setHomeworks(final Collection<Homework> homeworks) {
		this.homeworks = homeworks;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Level getLevel() {
		return this.level;
	}

	public void setLevel(final Level level) {
		this.level = level;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "subject")
	public Collection<ClassTime> getClassTimes() {
		return this.classTimes;
	}

	public void setClassTimes(final Collection<ClassTime> classTimes) {
		this.classTimes = classTimes;
	}

}
