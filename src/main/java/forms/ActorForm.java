
package forms;

import domain.Administrator;
import domain.Agent;
import domain.ClassGroup;
import domain.Parent;
import domain.School;
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
	
	//Agent
	private String	taxCode;
	
	//Teacher
	private School  school;
	
	//Student
	private ClassGroup classGroup;
	private String comment;


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

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public ClassGroup getClassGroup() {
		return classGroup;
	}

	public void setClassGroup(ClassGroup classGroup) {
		this.classGroup = classGroup;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
		this.taxCode = agent.getTaxCode();

	}
	
	public ActorForm(final Student student) {
		this();
		this.id = student.getId();
		this.version = student.getVersion();
		this.name = student.getName();
		this.surname = student.getSurname();
		this.address = student.getAddress();
		this.email = student.getEmail();
		this.phoneNumber = student.getPhoneNumber();
		this.classGroup = student.getClassGroup();
		this.comment = student.getComment();

	}
	
	public ActorForm(final Parent parent) {
		this();
		this.id = parent.getId();
		this.version = parent.getVersion();
		this.name = parent.getName();
		this.surname = parent.getSurname();
		this.address = parent.getAddress();
		this.email = parent.getEmail();
		this.phoneNumber = parent.getPhoneNumber();

	}
	
	public ActorForm(final Teacher teacher) {
		this();
		this.id = teacher.getId();
		this.version = teacher.getVersion();
		this.name = teacher.getName();
		this.surname = teacher.getSurname();
		this.address = teacher.getAddress();
		this.email = teacher.getEmail();
		this.phoneNumber = teacher.getPhoneNumber();
		this.school = teacher.getSchool();
		
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
		agent.setTaxCode(this.taxCode);
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
		teacher.setSchool(this.school);
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
		student.setClassGroup(this.classGroup);
		student.setComment(this.comment);
		return student;
	}

}
