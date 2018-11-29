
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "userAccount_id")
})
public abstract class Actor extends DomainEntity {

	@SafeHtml
	private String	name;
	@SafeHtml
	private String	surname;
	@SafeHtml
	private String	address;
	private String	phoneNumber;
	private String	email;


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

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@Pattern(regexp = "^\\+?\\d+$||^$")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Email
	@NotBlank
	@Column(unique = true)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}


	/*-- Relaciones --*/

	private Collection<PrivateMessage>	messagesSent;
	private Collection<PrivateMessage>	messagesReceived;
	private UserAccount					userAccount;
	private Collection<Folder>			folders;


	@OneToMany(mappedBy = "actorSender")
	@Valid
	@NotNull
	public Collection<PrivateMessage> getMessagesSent() {
		return this.messagesSent;
	}

	public void setMessagesSent(final Collection<PrivateMessage> messagesSent) {
		this.messagesSent = messagesSent;
	}

	@ManyToMany
	@Valid
	@NotNull
	public Collection<PrivateMessage> getMessagesReceived() {
		return this.messagesReceived;
	}

	public void setMessagesReceived(final Collection<PrivateMessage> messagesReceived) {
		this.messagesReceived = messagesReceived;
	}

	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@Valid
	@NotNull
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@OneToMany(mappedBy = "actor")
	@Valid
	@NotNull
	public Collection<Folder> getFolders() {
		return this.folders;
	}

	public void setFolders(final Collection<Folder> folders) {
		this.folders = folders;
	}

}
