
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class ChatRoom extends DomainEntity {

	@SafeHtml
	private String	name;
	private int counter;


	@NotBlank
	@Column(unique=true)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}


	
	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}



	private Collection<Student>			students;
	private Collection<StudentMessage>	studentMessages;


	@Valid
	@NotEmpty
	@ManyToMany
	public Collection<Student> getStudents() {
		return this.students;
	}

	public void setStudents(final Collection<Student> students) {
		this.students = students;
	}

	@Valid
	@NotNull
	@OneToMany
	public Collection<StudentMessage> getStudentMessages() {
		return this.studentMessages;
	}

	public void setStudentMessages(final Collection<StudentMessage> studentMessages) {
		this.studentMessages = studentMessages;
	}

}
