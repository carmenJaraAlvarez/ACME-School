
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class ClassGroup extends DomainEntity {

	@SafeHtml
	private String	name;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}


	private Collection<Teacher>			teachers;
	private Collection<Student>			students;
	private Collection<ParentsGroup>	parentsGroups;
	private Collection<ClassTime>		classTimes;
	private Level						level;


	@Valid
	@NotNull
	@ManyToMany
	public Collection<Teacher> getTeachers() {
		return this.teachers;
	}

	public void setTeachers(final Collection<Teacher> teachers) {
		this.teachers = teachers;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "classGroup")
	public Collection<Student> getStudents() {
		return this.students;
	}

	public void setStudents(final Collection<Student> students) {
		this.students = students;
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
	@OneToMany(mappedBy = "classGroup")
	public Collection<ParentsGroup> getParentsGroups() {
		return this.parentsGroups;
	}

	public void setParentsGroups(final Collection<ParentsGroup> parentsGroups) {
		this.parentsGroups = parentsGroups;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "classGroup")
	public Collection<ClassTime> getClassTimes() {
		return this.classTimes;
	}

	public void setClassTimes(final Collection<ClassTime> classTimes) {
		this.classTimes = classTimes;
	}

}
