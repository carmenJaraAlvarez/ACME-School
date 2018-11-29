
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Parent extends Actor {

	private Collection<Student>			students;
	private Collection<ParentsGroup>	parentsGroupsCreated;
	private Collection<ParentsGroup>	parentsGroupsManaged;
	private Collection<ParentsGroup>	parentsGroupsMemberOf;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "parent")
	public Collection<Student> getStudents() {
		return this.students;
	}

	public void setStudents(final Collection<Student> students) {
		this.students = students;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "creator")
	public Collection<ParentsGroup> getParentsGroupsCreated() {
		return this.parentsGroupsCreated;
	}

	public void setParentsGroupsCreated(final Collection<ParentsGroup> parentsGroupsCreated) {
		this.parentsGroupsCreated = parentsGroupsCreated;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<ParentsGroup> getParentsGroupsManaged() {
		return this.parentsGroupsManaged;
	}

	public void setParentsGroupsManaged(final Collection<ParentsGroup> parentsGroupsManaged) {
		this.parentsGroupsManaged = parentsGroupsManaged;
	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "members")
	public Collection<ParentsGroup> getParentsGroupsMemberOf() {
		return this.parentsGroupsMemberOf;
	}

	public void setParentsGroupsMemberOf(final Collection<ParentsGroup> parentsGroupsMemberOf) {
		this.parentsGroupsMemberOf = parentsGroupsMemberOf;
	}

}
