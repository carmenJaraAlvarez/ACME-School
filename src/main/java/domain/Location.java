
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

@Embeddable
@Access(AccessType.PROPERTY)
public class Location {

	private String	NorthCoordinate;
	private String	EastCoordinate;


	@Pattern(regexp = "^(\\-?([0-9]|[1-7][0-9]|[8][0-4])\\.\\d+)$|^(\\-?[8][5]\\.[0]+)$|^$")
	public String getNorthCoordinate() {
		return this.NorthCoordinate;
	}

	public void setNorthCoordinate(final String northCoordinate) {
		this.NorthCoordinate = northCoordinate;
	}

	@Pattern(regexp = "^(\\-?([0-9]|[1-9][0-9]|[1][0-7][0-9])\\.\\d+)$|^(\\-?[1][8][0]\\.[0]+)$|^$")
	public String getEastCoordinate() {
		return this.EastCoordinate;
	}

	public void setEastCoordinate(final String eastCoordinate) {
		this.EastCoordinate = eastCoordinate;
	}

}
