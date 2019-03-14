
package useCase;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.AdvertisementService;
import services.AgentService;
import utilities.AbstractTest;
import domain.Advertisement;
import domain.Agent;
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdvertisementUseCaseTest extends AbstractTest {

	//Services used
	@Autowired
	private AdvertisementService	advertisementService;
	@Autowired
	private AgentService			agentService;


	//Use case 33: Contratar y gestionar la publicación de un anuncio.
	protected void templateCreatesSaveAdvertisement(final String agentname, final String title, final String banner, final String web, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(agentname);
			final Agent loged = this.agentService.findByPrincipal();
			final Collection<Advertisement> advertisements = loged.getAdvertisements();
			final Advertisement advertisement = this.advertisementService.create();
			advertisement.setTitle(title);
			advertisement.setBanner(banner);
			advertisement.setWeb(web);
			this.advertisementService.flush();

			final Advertisement saved = this.advertisementService.save(this.advertisementService.reconstruct(advertisement, null));
			this.advertisementService.flush();

			final CreditCard c = new CreditCard();
			c.setBrand("Visa");
			c.setCvv(999);
			c.setExpirationMonth(12);
			c.setExpirationYear(25);
			c.setHolderName("Alberto");
			c.setNumber("1111222233334444");
			this.advertisementService.checkTarjeta(c);
			this.advertisementService.payAdvertisement(saved);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
		this.authenticate(null);
	}
	@Test
	public void driverCreatesSaveAdvertisement() {
		final Object testingData[][] = {
			//Positive
			{
				"agent1", "title", "http://banner.com", "http://url.com", null
			},
			//Negative
			{
				null, "title", "http://banner.com", "http://url.com", IllegalArgumentException.class
			},
			//Negative
			{
				"agent1", "", "", "", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreatesSaveAdvertisement((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
}
