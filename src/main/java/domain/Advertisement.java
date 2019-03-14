
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Advertisement extends DomainEntity {

	private CreditCard	creditCard;
	@SafeHtml
	private String		title;
	@SafeHtml
	private String		banner;
	@SafeHtml
	private String		web;
	private Boolean		paid;
	private Date		paidMoment;


	//@NotNull
	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@URL
	@NotBlank
	public String getWeb() {
		return this.web;
	}

	public void setWeb(final String web) {
		this.web = web;
	}
	public Boolean getPaid() {
		return this.paid;
	}

	public void setPaid(final Boolean paid) {
		this.paid = paid;
	}

	//@NotNull
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getPaidMoment() {
		return this.paidMoment;
	}

	public void setPaidMoment(final Date paidMoment) {
		this.paidMoment = paidMoment;
	}


	/*-- Relaciones --*/
	private Agent	agent;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Agent getAgent() {
		return this.agent;
	}

	public void setAgent(final Agent agent) {
		this.agent = agent;
	}

}
