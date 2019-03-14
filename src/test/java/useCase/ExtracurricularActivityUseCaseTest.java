
package useCase;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ExtracurricularActivityService;
import services.ParentService;
import services.ParentsGroupService;
import utilities.AbstractTest;
import domain.ExtracurricularActivity;
import domain.Parent;
import domain.ParentsGroup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ExtracurricularActivityUseCaseTest extends AbstractTest {

	//Services used
	@Autowired
	private ExtracurricularActivityService	extracurricularActivityService;
	@Autowired
	private ParentService					parentService;
	@Autowired
	private ParentsGroupService				parentsGroupService;


	//Use case 22: Informar de actividades extraescolares en los grupos a los que pertenezca.
	protected void templateCreateSaveActivity(final String username, final String parentsGroup, final String title, final String description, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Parent p = this.parentService.findByPrincipal();
			final ParentsGroup pg = this.parentsGroupService.findOneToDisplay(this.getEntityId(parentsGroup));
			pg.getExtracurricularActivities();

			final ExtracurricularActivity sub = this.extracurricularActivityService.create(pg.getId());
			sub.setTitle(title);
			sub.setDescription(description);

			final ExtracurricularActivity recon = this.extracurricularActivityService.reconstruct(sub, null);
			this.extracurricularActivityService.saveForParent(recon);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverCreateSaveActivity() {
		final Object testingData[][] = {
			//Positive test
			{
				"parent1", "parentsGroup1", "activity1", "Test title", "Test activity description", null
			},
			//Negative test
			{
				"teacher1", "parentsGroup1", "activity1", "Test title", "Test activity description", IllegalArgumentException.class
			},
			//Negative test
			{
				"parent1", "parentsGroup99", "activity1", "Test title", "Test activity description", AssertionError.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSaveActivity((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][5]);
	}

	//Use case 35: Editar actividades extraescolares de los grupos a los que pertenezca
	protected void templateEditActivity(final String username, final String parentsGroup, final String activityBean, final String title, final String description, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Parent p = this.parentService.findByPrincipal();
			final ParentsGroup pg = this.parentsGroupService.findOneToDisplay(this.getEntityId(parentsGroup));
			pg.getExtracurricularActivities();

			final ExtracurricularActivity sub = this.extracurricularActivityService.findOneToEdit(this.getEntityId(activityBean));
			sub.setTitle(title);
			sub.setDescription(description);

			final ExtracurricularActivity recon = this.extracurricularActivityService.reconstruct(sub, null);
			this.extracurricularActivityService.saveForParent(recon);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverEditActivity() {
		final Object testingData[][] = {
			//Positive test
			{
				"parent1", "parentsGroup1", "extracurricularActivity1", "Test title", "Test description", null
			},
			//Negative test
			{
				"teacher1", "parentsGroup1", "extracurricularActivity1", "Test title", "Test description", IllegalArgumentException.class
			},
			//Negative test
			{
				"parent1", "parentsGroup1", "extracurricularActivity99", "Test title", "Test description", AssertionError.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditActivity((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	protected void templateDeleteActivity(final String username, final String parentsGroup, final String activityBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Parent p = this.parentService.findByPrincipal();
			final ParentsGroup pg = this.parentsGroupService.findOneToDisplay(this.getEntityId(parentsGroup));
			pg.getExtracurricularActivities();

			final ExtracurricularActivity sub = this.extracurricularActivityService.findOneToEdit(this.getEntityId(activityBean));
			this.extracurricularActivityService.deleteForParents(sub);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverDeleteActivity() {
		final Object testingData[][] = {
			//Positive test
			{
				"parent1", "parentsGroup1", "extracurricularActivity1", null
			},
			//Negative test
			{
				"teacher1", "parentsGroup1", "extracurricularActivity1", IllegalArgumentException.class
			},
			//Negative test
			{
				"parent1", "parentsGroup1", "extracurricularActivity99", AssertionError.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteActivity((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
}
