
package forms;



import javax.persistence.Column;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;
import domain.Agent;
import domain.ClassGroup;
import domain.Parent;
import domain.School;
import domain.Student;
import domain.Teacher;

public class CreateActorForm {

	private Integer	id;
	private Integer	version;
	private String	name;
	private String	surname;
	private String	email;
	private String	username;
	private String	password;
	private String	password2;
	private Boolean	valida;
	
	private String	taxCode;
	
	private School  school;

	private ClassGroup classGroup;
	private String comment;


	// Form

	public CreateActorForm() {
		this.id = 0;
		this.version = 0;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(final Integer version) {
		this.version = version;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	
	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@NotBlank
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@Size(min = 5, max = 32)
	@Column(unique = true)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@Size(min = 5, max = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@Size(min = 5, max = 32)
	public String getPassword2() {
		return this.password2;
	}

	public void setPassword2(final String password2) {
		this.password2 = password2;
	}

	public Boolean getValida() {
		return this.valida;
	}
	
	public void setValida(final Boolean valida) {
		this.valida = valida;
	}
	
	//Para hacer la creacion de agente
	
	public String getTaxCode() {
		return this.taxCode;
	}

	public void setTaxCode(final String taxCode) {
		this.taxCode = taxCode;
	}

	
	
	//Para la creacion de profesor
	
	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}
	
	
	
	//Para la creacion de estudiante

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

	
	
	public Agent getAgent() {
		final Agent agent = new Agent();
		agent.setId(this.id);
		agent.setVersion(this.version);
		agent.setName(this.name);
		agent.setSurname(this.surname);
		agent.setEmail(this.email);
		agent.setTaxCode(this.taxCode);

		return agent;
	}
	


	public Parent getParent() {
		final Parent parent = new Parent();
		parent.setId(this.id);
		parent.setVersion(this.version);
		parent.setName(this.name);
		parent.setSurname(this.surname);
		parent.setEmail(this.email);

		return parent;
	}

	public Teacher getTeacher() {
		final Teacher teacher = new Teacher();
		teacher.setId(this.id);
		teacher.setVersion(this.version);
		teacher.setName(this.name);
		teacher.setSurname(this.surname);
		teacher.setEmail(this.email);
		teacher.setSchool(this.school);
		
		return teacher;
	}

	public Student getStudent() {
		final Student student = new Student();
		student.setId(this.id);
		student.setVersion(this.version);
		student.setName(this.name);
		student.setSurname(this.surname);
		student.setEmail(this.email);
		student.setClassGroup(this.classGroup);
		student.setComment(this.comment);
		
		return student;
	}

	public UserAccount getUserAccount() {
		final UserAccount userAccount = new UserAccount();
		userAccount.setUsername(this.username);
		userAccount.setPassword(this.password);
		return userAccount;
	}
}
