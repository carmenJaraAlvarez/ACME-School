
package useCase;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.HomeworkService;
import services.ParentService;
import services.ParentsGroupService;
import services.SubjectService;
import utilities.AbstractTest;
import domain.Homework;
import domain.Parent;
import domain.ParentsGroup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class HomeworkUseCaseTest extends AbstractTest {

	//Services used
	@Autowired
	private HomeworkService		homeworkService;
	@Autowired
	private ParentService		parentService;
	@Autowired
	private ParentsGroupService	parentsGroupService;
	@Autowired
	private SubjectService		subjectService;


	//Use case 19: Crear tareas y asignarlas a asignaturas.
	protected void templateCreateSaveHomework(final String username, final String parentsGroup, final String subjectBean, final String title, final String description, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Parent p = this.parentService.findByPrincipal();
			final ParentsGroup pg = this.parentsGroupService.findOneToDisplay(this.getEntityId(parentsGroup));
			pg.getHomeworks();

			final Homework sub = this.homeworkService.create(pg.getId());
			sub.setDescription(description);
			sub.setMoment(new Date(System.currentTimeMillis() + 10 ^ 8));
			sub.setParentsGroup(pg);
			sub.setSubject(this.subjectService.findOneToEdit(this.getEntityId(subjectBean)));
			sub.setTitle(title);

			final Homework recon = this.homeworkService.reconstruct(sub, null);
			this.homeworkService.saveForParent(recon);

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
				"parent1", "parentsGroup1", "subject1", "Test title", "Test homework description", null
			},
			//Negative test
			{
				"teacher1", "parentsGroup1", "subject1", "Test title", "Test homework description", IllegalArgumentException.class
			},
			//Negative test
			{
				"parent1", "parentsGroup1", "subject99", "Test title", "Test homework description", AssertionError.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSaveHomework((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}
}
