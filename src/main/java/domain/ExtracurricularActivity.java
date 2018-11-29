
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class ExtracurricularActivity extends DomainEntity {

	@SafeHtml
	private String	title;
	@SafeHtml
	private String	description;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}


	private ParentsGroup	parentsGroup;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public ParentsGroup getParentsGroup() {
		return this.parentsGroup;
	}

	public void setParentsGroup(final ParentsGroup parentsGroup) {
		this.parentsGroup = parentsGroup;
	}

}
