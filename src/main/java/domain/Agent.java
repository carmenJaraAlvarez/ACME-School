
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Agent extends Actor {

	@SafeHtml
	private String	taxCode;


	@NotBlank
	public String getTaxCode() {
		return this.taxCode;
	}

	public void setTaxCode(final String taxCode) {
		this.taxCode = taxCode;
	}


	/*-- Relaciones --*/
	private Collection<Advertisement>	advertisements;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "agent", cascade = CascadeType.ALL)
	public Collection<Advertisement> getAdvertisements() {
		return this.advertisements;
	}

	public void setAdvertisements(final Collection<Advertisement> advertisements) {
		this.advertisements = advertisements;
	}

}
