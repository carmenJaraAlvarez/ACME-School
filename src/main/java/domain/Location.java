
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class Location {

	private String	NorthCoordinate;
	private String	EastCoordinate;


	@NotBlank
	@Pattern(regexp = "")
	public String getNorthCoordinate() {
		return this.NorthCoordinate;
	}

	public void setNorthCoordinate(final String northCoordinate) {
		this.NorthCoordinate = northCoordinate;
	}

	@NotBlank
	@Pattern(regexp = "")
	public String getEastCoordinate() {
		return this.EastCoordinate;
	}

	public void setEastCoordinate(final String eastCoordinate) {
		this.EastCoordinate = eastCoordinate;
	}

}
