
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

import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Student extends Actor {

	@SafeHtml
	private String	comment;


	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}


	private Collection<Mark>		marks;
	private Parent					parent;
	private Collection<ChatRoom>	chatRooms;
	private ClassGroup				classGroup;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "student")
	public Collection<Mark> getMarks() {
		return this.marks;
	}

	public void setMarks(final Collection<Mark> marks) {
		this.marks = marks;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Parent getParent() {
		return this.parent;
	}

	public void setParent(final Parent parent) {
		this.parent = parent;
	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "students")
	public Collection<ChatRoom> getChatRooms() {
		return this.chatRooms;
	}

	public void setChatRooms(final Collection<ChatRoom> chatRooms) {
		this.chatRooms = chatRooms;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public ClassGroup getClassGroup() {
		return classGroup;
	}

	public void setClassGroup(ClassGroup classGroup) {
		this.classGroup = classGroup;
	}

}
