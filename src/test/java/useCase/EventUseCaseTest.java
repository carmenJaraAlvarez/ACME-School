
package useCase;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.EventService;
import services.ParentService;
import services.ParentsGroupService;
import utilities.AbstractTest;
import domain.Event;
import domain.Parent;
import domain.ParentsGroup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class EventUseCaseTest extends AbstractTest {

	//Services used
	@Autowired
	private EventService		eventService;
	@Autowired
	private ParentService		parentService;
	@Autowired
	private ParentsGroupService	parentsGroupService;


	//Use case 16: Crear eventos de un grupo al que pertenece
	protected void templateCreateSaveEvent(final String username, final String parentsGroup, final String description, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Parent p = this.parentService.findByPrincipal();
			final ParentsGroup pg = this.parentsGroupService.findOneToDisplay(this.getEntityId(parentsGroup));
			pg.getEvents();
			final Event newEvent = this.eventService.create();
			newEvent.setDescription(description);
			final long fecha = new Long(System.currentTimeMillis() + 10 ^ 8);
			newEvent.setMoment(new Date(fecha));
			newEvent.setParentsGroup(pg);

			final Event recon = this.eventService.reconstruct(newEvent, null);
			this.eventService.save(recon);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	@Test
	public void driverCreateSaveEvent() {
		final Object testingData[][] = {
			//Positive test
			{
				"parent1", "parentsGroup1", "Test description", null
			},
			//Negative test
			{
				"teacher1", "parentsGroup1", "Test description", IllegalArgumentException.class
			},
			//Negative test
			{
				"parent1", "parentsGroup19", "Test description", AssertionError.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSaveEvent((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	//Use case 17: Editar eventos de un grupo al que pertenece
	protected void templateEditEvent(final String username, final String event, final String parentsGroup, final String description, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Parent p = this.parentService.findByPrincipal();
			final ParentsGroup pg = this.parentsGroupService.findOneToDisplay(this.getEntityId(parentsGroup));
			pg.getEvents();
			final Event newEvent = this.eventService.findOneToEdit(this.getEntityId(event));
			newEvent.setDescription(description);
			final long fecha = new Long(System.currentTimeMillis() + 10 ^ 8);
			newEvent.setMoment(new Date(fecha));
			newEvent.setParentsGroup(pg);

			final Event recon = this.eventService.reconstruct(newEvent, null);
			this.eventService.save(recon);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	@Test
	public void driverEditEvent() {
		final Object testingData[][] = {
			//Positive test
			{
				"parent1", "event1", "parentsGroup1", "Test description", null
			},
			//Negative test
			{
				"teacher1", "event1", "parentsGroup1", "Test description", IllegalArgumentException.class
			},
			//Negative test
			{
				"parent1", "event26", "parentsGroup1", "Test description", AssertionError.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditEvent((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
}
