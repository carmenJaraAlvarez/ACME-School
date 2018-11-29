
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Embeddable
@Access(AccessType.PROPERTY)
public class CreditCard {

	private String	number;
	private Integer	cvv;				//no int para placeholder
	private Integer	expirationMonth;
	private Integer	expirationYear;
	@SafeHtml
	private String	holderName;
	@SafeHtml
	private String	brand;


	@NotBlank
	@CreditCardNumber
	@Pattern(regexp = "^\\d*$")
	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	@Range(min = 100, max = 999)
	public Integer getCvv() {
		return this.cvv;
	}

	public void setCvv(final Integer cvv) {
		this.cvv = cvv;
	}

	@Range(min = 1, max = 12)
	public Integer getExpirationMonth() {
		return this.expirationMonth;
	}

	public void setExpirationMonth(final Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	@Range(min = 1, max = 99)
	public Integer getExpirationYear() {
		return this.expirationYear;
	}

	public void setExpirationYear(final Integer expirationYear) {
		this.expirationYear = expirationYear;
	}

	@NotBlank
	public String getHolderName() {
		return this.holderName;
	}

	public void setHolderName(final String holderName) {
		this.holderName = holderName;
	}

	@NotBlank
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

}
