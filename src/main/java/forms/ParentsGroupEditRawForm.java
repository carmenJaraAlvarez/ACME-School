
package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import domain.ParentsGroup;

public class ParentsGroupEditRawForm {

	private int		id;
	private int		version;

	@SafeHtml
	private String	name;
	@SafeHtml
	private String	url;
	@SafeHtml
	private String	description;


	// Form
	public ParentsGroupEditRawForm() {
		super();
	}
	public ParentsGroupEditRawForm(ParentsGroup parentsGroup) {
		super();
		this.id = parentsGroup.getId();
		this.version = parentsGroup.getVersion();
		this.description = parentsGroup.getDescription();
		this.name = parentsGroup.getName();
		if (parentsGroup.getImage() != null) {
			this.url = parentsGroup.getImage();
		}
	}

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@URL
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}

}
