
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

@Entity
@Access(AccessType.PROPERTY)
public class Teacher extends Actor {

	private Collection<Entry>			entries;
	private Collection<ClassGroup>		classGroups;
	private Collection<ParentsGroup>	parentsGroups;
	private Collection<Mark>			marks;
	private School						school;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "teacher")
	public Collection<Entry> getEntries() {
		return this.entries;
	}

	public void setEntries(final Collection<Entry> entries) {
		this.entries = entries;
	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "teachers")
	public Collection<ClassGroup> getClassGroups() {
		return this.classGroups;
	}

	public void setClassGroups(final Collection<ClassGroup> classGroups) {
		this.classGroups = classGroups;
	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "teachers")
	public Collection<ParentsGroup> getParentsGroups() {
		return this.parentsGroups;
	}

	public void setParentsGroups(final Collection<ParentsGroup> parentsGroups) {
		this.parentsGroups = parentsGroups;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "teacher")
	public Collection<Mark> getMarks() {
		return this.marks;
	}

	public void setMarks(final Collection<Mark> marks) {
		this.marks = marks;
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
