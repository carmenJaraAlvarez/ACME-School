
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	private Date		moment;
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.BASIC)
	private String		body;
	private FileUpload	Attachment;


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
	@Lob
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@Valid
	public FileUpload getAttachment() {
		return this.Attachment;
	}

	public void setAttachment(final FileUpload attachment) {
		this.Attachment = attachment;
	}


	private Parent	parent;


	@ManyToOne(optional = true)
	public Parent getParent() {
		return this.parent;
	}

	public void setParent(final Parent parent) {
		this.parent = parent;
	}

}
