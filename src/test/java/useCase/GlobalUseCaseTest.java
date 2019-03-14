
package useCase;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.AdministratorService;
import services.GlobalService;
import utilities.AbstractTest;
import domain.Administrator;
import domain.Global;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class GlobalUseCaseTest extends AbstractTest {

	//Services used
	@Autowired
	private GlobalService			globalService;
	@Autowired
	private AdministratorService	administratorService;


	//Use case 24: Gestionar palabras spam.
	protected void templateEditTabooWords(final String username, final String taboo, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Administrator logued = this.administratorService.findByPrincipal();
			final Global global = this.globalService.getGlobal();
			final Collection<String> spamwords = this.globalService.getGlobal().getSpamWords();

			spamwords.add(taboo);
			global.setSpamWords(spamwords);

			this.globalService.save(global);
			this.globalService.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.authenticate(null);
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverEditTabooWords() {
		final Object testingData[][] = {
			//POSITIVE
			{
				"admin", "tabooWord", null
			},
			//NEGATIVE
			{
				"user1", "newspaper", IllegalArgumentException.class
			},
			//NEGATIVE
			{
				null, "newspaper", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditTabooWords((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	//Use case 23: Gestionar las palabras inapropiadas.
	protected void templateEditDangerousWords(final String username, final String dangWord, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Administrator logued = this.administratorService.findByPrincipal();
			final Global global = this.globalService.getGlobal();
			final Collection<String> dangWords = this.globalService.getGlobal().getDangerousWords();

			dangWords.add(dangWord);
			global.setDangerousWords(dangWords);

			this.globalService.save(global);
			this.globalService.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.authenticate(null);
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverEditDangerousWords() {
		final Object testingData[][] = {
			//POSITIVE
			{
				"admin", "dangWrod", null
			},
			//NEGATIVE
			{
				"user1", "newspaper", IllegalArgumentException.class
			},
			//NEGATIVE
			{
				null, "newspaper", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditDangerousWords((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
}
