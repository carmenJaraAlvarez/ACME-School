
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Level extends DomainEntity {

	private int	level;


	@Range(min = 1, max = 6)
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}


	private Collection<ClassGroup>	classGroups;
	private Collection<Subject>		subjects;
	private School					school;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "level")
	public Collection<ClassGroup> getClassGroups() {
		return classGroups;
	}

	public void setClassGroups(Collection<ClassGroup> classGroups) {
		this.classGroups = classGroups;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "level")
	public Collection<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Collection<Subject> subjects) {
		this.subjects = subjects;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public School getSchool() {
		return this.school;
	}

	public void setSchool(final School school) {
		this.school = school;
	}

}
