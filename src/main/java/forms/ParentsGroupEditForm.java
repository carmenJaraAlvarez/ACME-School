
package forms;

import domain.ClassGroup;
import domain.Level;
import domain.School;

public class ParentsGroupEditForm {

	private School		school;
	private Level		level;
	private ClassGroup	classGroup;


	// Form

	public ParentsGroupEditForm() {
		super();
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public ClassGroup getClassGroup() {
		return classGroup;
	}

	public void setClassGroup(ClassGroup classGroup) {
		this.classGroup = classGroup;
	}

}
