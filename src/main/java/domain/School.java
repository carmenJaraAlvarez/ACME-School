
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class School extends DomainEntity {

	@SafeHtml
	private String	nameSchool;
	@SafeHtml
	private String	address;

	private String	phoneNumber;

	private String	emailSchool;

	private String	image;


	@NotBlank
	public String getNameSchool() {
		return this.nameSchool;
	}

	public void setNameSchool(final String nameSchool) {
		this.nameSchool = nameSchool;
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
	public String getEmailSchool() {
		return this.emailSchool;
	}

	public void setEmailSchool(final String emailSchool) {
		this.emailSchool = emailSchool;
	}


	@URL
	public String getImage() {
		return this.image;
	}

	public void setImage(final String image) {
		this.image = image;
	}


	private Collection<Teacher>	teachers;
	private Collection<Level>	levels;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "school"/* , fetch = FetchType.EAGER */)
	public Collection<Teacher> getTeachers() {
		return this.teachers;
	}

	public void setTeachers(final Collection<Teacher> teachers) {
		this.teachers = teachers;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "school"/* , fetch = FetchType.EAGER */)
	public Collection<Level> getLevels() {
		return this.levels;
	}

	public void setLevels(final Collection<Level> levels) {
		this.levels = levels;
	}

}
