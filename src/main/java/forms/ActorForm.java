
package forms;

import domain.Administrator;
import domain.Agent;
import domain.Parent;
import domain.Student;
import domain.Teacher;

public class ActorForm {

	private Integer	id;
	private Integer	version;
	private String	name;
	private String	surname;
	private String	address;
	private String	phoneNumber;
	private String	email;


	// Form

	public ActorForm() {

	}

	public int getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final Integer version) {
		this.version = version;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public ActorForm(final Administrator admin) {
		this();
		this.id = admin.getId();
		this.version = admin.getVersion();
		this.name = admin.getName();
		this.surname = admin.getSurname();
		this.address = admin.getAddress();
		this.email = admin.getEmail();
		this.phoneNumber = admin.getPhoneNumber();

	}

	public ActorForm(final Agent agent) {
		this();
		this.id = agent.getId();
		this.version = agent.getVersion();
		this.name = agent.getName();
		this.surname = agent.getSurname();
		this.address = agent.getAddress();
		this.email = agent.getEmail();
		this.phoneNumber = agent.getPhoneNumber();

	}

	public Administrator getAdministrator() {
		final Administrator admin = new Administrator();
		admin.setId(this.id);
		admin.setVersion(this.version);
		admin.setName(this.name);
		admin.setSurname(this.surname);
		admin.setAddress(this.address);
		admin.setEmail(this.email);
		admin.setPhoneNumber(this.phoneNumber);
		return admin;
	}

	public Agent getAgent() {
		final Agent agent = new Agent();
		agent.setId(this.id);
		agent.setVersion(this.version);
		agent.setName(this.name);
		agent.setSurname(this.surname);
		agent.setAddress(this.address);
		agent.setEmail(this.email);
		agent.setPhoneNumber(this.phoneNumber);
		return agent;
	}
	
	
	public Parent getParent() {
		final Parent parent = new Parent();
		parent.setId(this.id);
		parent.setVersion(this.version);
		parent.setName(this.name);
		parent.setSurname(this.surname);
		parent.setAddress(this.address);
		parent.setEmail(this.email);
		parent.setPhoneNumber(this.phoneNumber);
		return parent;
	}

	public Teacher getTeacher() {
		final Teacher teacher = new Teacher();
		teacher.setId(this.id);
		teacher.setVersion(this.version);
		teacher.setName(this.name);
		teacher.setSurname(this.surname);
		teacher.setAddress(this.address);
		teacher.setEmail(this.email);
		teacher.setPhoneNumber(this.phoneNumber);
		return teacher;
	}

	public Student getStudent() {
		final Student student = new Student();
		student.setId(this.id);
		student.setVersion(this.version);
		student.setName(this.name);
		student.setSurname(this.surname);
		student.setAddress(this.address);
		student.setEmail(this.email);
		student.setPhoneNumber(this.phoneNumber);
		return student;
	}

}
