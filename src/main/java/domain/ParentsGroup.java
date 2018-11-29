
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
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class ParentsGroup extends DomainEntity {

	@SafeHtml
	private String	name;
	@SafeHtml
	private String	image;
	@SafeHtml
	private String	code;
	@SafeHtml
	private String	description;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@URL
	public String getImage() {
		return this.image;
	}

	public void setImage(final String image) {
		this.image = image;
	}

	@NotBlank
	@Pattern(regexp = "\\d{5}\\w{2}")
	public String getCode() {
		return this.code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}


	private Parent								creator;
	private Collection<Parent>					groupAdmins;
	private Collection<Parent>					members;
	private Collection<Event>					events;
	private Collection<Teacher>					teachers;
	private Collection<ExtracurricularActivity>	extracurricularActivities;
	private SchoolCalendar						schoolCalendar;
	private Collection<Message>					messages;
	private ClassGroup							classGroup;
	private Collection<Homework>				homeworks;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Parent getCreator() {
		return this.creator;
	}

	public void setCreator(final Parent creator) {
		this.creator = creator;
	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "parentsGroupsManaged")
	public Collection<Parent> getGroupAdmins() {
		return this.groupAdmins;
	}

	public void setGroupAdmins(final Collection<Parent> groupAdmins) {
		this.groupAdmins = groupAdmins;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<Parent> getMembers() {
		return this.members;
	}

	public void setMembers(final Collection<Parent> members) {
		this.members = members;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "parentsGroup")
	public Collection<Event> getEvents() {
		return this.events;
	}

	public void setEvents(final Collection<Event> events) {
		this.events = events;
	}

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
	@OneToMany(mappedBy = "parentsGroup")
	public Collection<ExtracurricularActivity> getExtracurricularActivities() {
		return this.extracurricularActivities;
	}

	public void setExtracurricularActivities(final Collection<ExtracurricularActivity> extracurricularActivities) {
		this.extracurricularActivities = extracurricularActivities;
	}

	@Valid
	@ManyToOne(optional = true)
	public SchoolCalendar getSchoolCalendar() {
		return this.schoolCalendar;
	}

	public void setSchoolCalendar(final SchoolCalendar schoolCalendar) {
		this.schoolCalendar = schoolCalendar;
	}

	@Valid
	@NotNull
	@OneToMany
	public Collection<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
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

	@Valid
	@NotNull
	@OneToMany(mappedBy = "parentsGroup")
	public Collection<Homework> getHomeworks() {
		return homeworks;
	}

	public void setHomeworks(Collection<Homework> homeworks) {
		this.homeworks = homeworks;
	}

}
