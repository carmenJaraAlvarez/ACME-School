
package forms;

import org.hibernate.validator.constraints.NotBlank;

import domain.Level;

public class CreateGroupFormClass {

	private Level	level;
	private String	className;


	// Form

	public CreateGroupFormClass() {
		super();
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	@NotBlank
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
