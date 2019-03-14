
package useCase;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.EntryService;
import services.TeacherService;
import utilities.AbstractTest;
import domain.Entry;
import domain.Teacher;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class EntryUseCaseTest extends AbstractTest {

	//Services used
	@Autowired
	private EntryService	entryService;
	@Autowired
	private TeacherService	teacherService;


	//Use case 30: Crear entradas de su tablón de anuncios.
	protected void templateCreateSaveEntry(final String username, final String title, final String body, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Teacher t = this.teacherService.findByPrincipal();
			t.getEntries();
			final Entry e = this.entryService.create();
			e.setTitle(title);
			e.setBody(body);

			final Entry entry = this.entryService.reconstruct(e, null);
			this.entryService.save(entry);

			this.entryService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverCreateSaveEntry() {
		final Object testingData[][] = {
			//Positive test
			{
				"teacher1", "entry1 title", "entry1 body", null
			},
			//Negative test
			{
				"student1", "entry1 title", "entry1 body", IllegalArgumentException.class
			},
			//Negative test
			{
				"teacher1", "", "", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSaveEntry((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	//Use case 31: Editar entradas de su tablón de anuncios.
	protected void templateEditEntry(final String username, final String entryBean, final String title, final String body, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Teacher t = this.teacherService.findByPrincipal();
			t.getEntries();
			final Entry e = this.entryService.findOneToEdit(this.getEntityId(entryBean));
			e.setTitle(title);
			e.setBody(body);

			final Entry entry = this.entryService.reconstruct(e, null);
			this.entryService.save(entry);

			this.entryService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverEditEntry() {
		final Object testingData[][] = {
			//Positive test
			{
				"teacher1", "entry1", "entry1 title", "entry1 body", null
			},
			//Negative test
			{
				"teacher1", "entry99", "entry1 title", "entry1 body", AssertionError.class
			},
			//Negative test
			{
				"teacher1", "entry1", "", "", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditEntry((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
}
