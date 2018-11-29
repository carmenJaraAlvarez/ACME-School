
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	private Date	moment;
	private String	body;
	private String	Attachments;


	@NotNull
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	public String getAttachments() {
		return this.Attachments;
	}

	public void setAttachments(final String attachments) {
		this.Attachments = attachments;
	}


	private Parent	parent;


	@ManyToOne(optional = false)
	public Parent getParent() {
		return this.parent;
	}

	public void setParent(final Parent parent) {
		this.parent = parent;
	}

}
