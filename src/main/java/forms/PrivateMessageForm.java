
package forms;

import org.hibernate.validator.constraints.NotBlank;

import domain.ParentsGroup;
import domain.PrivateMessage;

public class PrivateMessageForm {

	private Integer	id;
	private Integer	version;
	private String	sendTo;
	private String	subject;
	private String	priority;
	private String	body;


	public PrivateMessageForm() {
		this.id = 0;
		this.version = 0;

	}

	public PrivateMessageForm(ParentsGroup parentsGroup, boolean language) {
		this.id = 0;
		this.version = 0;
		if (language) {
			this.subject = "Acme-School | Parents group invitation";
			this.body = "The code is: '" + parentsGroup.getCode() + "'.<br/><br/> You can access to the parents group " + "<a href='inscription/teacher/createInscription.do?parentsGroupId=" + parentsGroup.getId() + "'>clicking here</a> and put the code.";
		} else {
			this.subject = "Acme-School | Invitación a grupo de padres";
			this.body = "El código es: '" + parentsGroup.getCode() + "'.<br/><br/> Puedes acceder al grupo " + "<a href='inscription/teacher/createInscription.do?parentsGroupId=" + parentsGroup.getId() + "'>pinchando aquí</a> y poniendo el código.";
		}
		this.priority = "NEUTRAL";

	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@NotBlank
	public String getSendTo() {
		return this.sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public PrivateMessage getMessage() {
		final PrivateMessage privateMessage = new PrivateMessage();
		privateMessage.setId(this.id);
		privateMessage.setVersion(this.version);
		privateMessage.setBody(this.body);
		privateMessage.setSubject(this.subject);
		privateMessage.setPriority(this.priority);
		return privateMessage;
	}

}
