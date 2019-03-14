
package useCase;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ParentService;
import services.ParentsGroupService;
import services.SubjectService;
import utilities.AbstractTest;
import domain.Parent;
import domain.ParentsGroup;
import domain.Subject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SubjectUseCaseTest extends AbstractTest {

	//Services used
	@Autowired
	private SubjectService		subjectService;
	@Autowired
	private ParentService		parentService;
	@Autowired
	private ParentsGroupService	parentsGroupService;


	//Use case 18: Añadir asignaturas a un colegio.
	protected void templateCreateSaveSubject(final String username, final String parentsGroup, final String subName, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Parent p = this.parentService.findByPrincipal();
			final ParentsGroup pg = this.parentsGroupService.findOneToDisplay(this.getEntityId(parentsGroup));
			pg.getClassGroup().getLevel().getSubjects();

			final Subject sub = this.subjectService.create(pg.getClassGroup().getLevel().getId());
			sub.setName(subName);
			sub.setLevel(pg.getClassGroup().getLevel());

			final Subject recon = this.subjectService.reconstruct(sub, null);
			this.subjectService.saveForParent(recon);

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
				"parent1", "parentsGroup1", "Test subject name", null
			},
			//Negative test
			{
				"teacher1", "parentsGroup1", "Test subject name", IllegalArgumentException.class
			},
			//Negative test
			{
				"parent1", "parentsGroup19", "Test subject name", AssertionError.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSaveSubject((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
}
