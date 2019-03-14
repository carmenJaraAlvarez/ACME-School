
package forms;

public class LinkForm {

	private Integer	idCalendar;
	private Integer	idGroup;


	public LinkForm() {

	}
	public LinkForm(Integer idCalendar) {
		this();
		this.idCalendar = idCalendar;

	}

	public Integer getIdCalendar() {
		return idCalendar;
	}

	public void setIdCalendar(Integer idCalendar) {
		this.idCalendar = idCalendar;
	}

	public Integer getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(Integer idGroup) {
		this.idGroup = idGroup;
	}

}
