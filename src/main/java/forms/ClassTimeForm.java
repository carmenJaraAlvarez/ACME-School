
package forms;

import org.hibernate.validator.constraints.NotBlank;

import domain.ClassGroup;
import domain.ClassTime;
import domain.Subject;

public class ClassTimeForm {

	private Integer		id;
	private Integer		version;
	private Subject		subject;
	private ClassGroup	classGroup;
	private String		hour;
	private String		day;
	private Integer		parentsGroupId;


	// Form

	public ClassTimeForm() {
		super();
		this.id = 0;
		this.version = 0;
	}

	public ClassTimeForm(ClassTime classTime) {
		super();
		this.id = classTime.getId();
		this.version = classTime.getVersion();
		this.day = classTime.getDay();
		this.hour = classTime.getHour();
		this.classGroup = classTime.getClassGroup();
		this.subject = classTime.getSubject();

	}
	@NotBlank
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public ClassGroup getClassGroup() {
		return classGroup;
	}

	public void setClassGroup(ClassGroup classGroup) {
		this.classGroup = classGroup;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentsGroupId() {
		return parentsGroupId;
	}

	public void setParentsGroupId(Integer parentsGroupId) {
		this.parentsGroupId = parentsGroupId;
	}

}
