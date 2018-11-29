
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SchoolCalendar extends DomainEntity {

	@SafeHtml
	private String	course;
	@SafeHtml
	private String	region;
	@SafeHtml
	private String	country;
	@SafeHtml
	private String	image;


	@NotBlank
	public String getCourse() {
		return this.course;
	}

	public void setCourse(final String course) {
		this.course = course;
	}

	@NotBlank
	public String getRegion() {
		return this.region;
	}

	public void setRegion(final String region) {
		this.region = region;
	}

	@NotBlank
	public String getCountry() {
		return this.country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	@NotBlank
	@URL
	public String getImage() {
		return this.image;
	}

	public void setImage(final String image) {
		this.image = image;
	}

}
