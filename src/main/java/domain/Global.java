
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Global extends DomainEntity {

	private Collection<String>	spamWords;
	private Collection<String>	dangerousWords;
	private int					wordsLimit;
	private Double				price;
	private String				payPalEmail;


	@NotNull
	@ElementCollection
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@NotNull
	@ElementCollection
	public Collection<String> getDangerousWords() {
		return this.dangerousWords;
	}

	public void setDangerousWords(final Collection<String> dangerousWords) {
		this.dangerousWords = dangerousWords;
	}

	public int getWordsLimit() {
		return this.wordsLimit;
	}

	public void setWordsLimit(final int wordsLimit) {
		this.wordsLimit = wordsLimit;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	@NotNull
	public String getPayPalEmail() {
		return this.payPalEmail;
	}

	public void setPayPalEmail(final String payPalEmail) {
		this.payPalEmail = payPalEmail;
	}

}
