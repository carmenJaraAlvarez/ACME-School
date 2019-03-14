
package useCase;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ClassGroupService;
import services.LevelService;
import services.ParentService;
import services.SchoolService;
import utilities.AbstractTest;
import domain.ClassGroup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ClassGroupUseCaseTest extends AbstractTest {

	@Autowired
	private ClassGroupService	classGroupService;
	@Autowired
	private ParentService		parentService;
	@Autowired
	private SchoolService		schoolService;
	@Autowired
	private LevelService		levelService;


	//Use case 09: Dar de altas las clases de un colegio
	protected void templateCreateSaveClassGroup(final String username, final String levelBean, final String nameClassGroup, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			this.schoolService.findAll();
			final Collection<ClassGroup> classes = this.schoolService.classGroupOfSchool(this.schoolService.findOne(super.getEntityId("school1")));
			final ClassGroup cg = this.classGroupService.create(this.getEntityId(levelBean));
			cg.setName(nameClassGroup);
			this.classGroupService.saveForParent(cg);
			this.classGroupService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverCreateSaveClassGroup() {
		final Object testingData[][] = {
			//Positive test
			{
				"parent1", "level1", "classGroupName", null
			},
			//Negative test
			{
				"parent1", "level14", "", AssertionError.class
			},
			//Negative test
			{
				"teacher1", "level1", "classGroupName", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSaveClassGroup((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	//Use case 10: Editar cualquier clase
	protected void templateEditClassGroup(final String username, final String classGroupBean, final String nameClassGroup, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			this.schoolService.findAll();
			final Collection<ClassGroup> classes = this.schoolService.classGroupOfSchool(this.schoolService.findOne(super.getEntityId("school1")));
			final ClassGroup cg = this.classGroupService.findOneToEdit(this.getEntityId(classGroupBean));
			cg.setName(nameClassGroup);
			this.classGroupService.saveForParent(cg);
			this.classGroupService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverEditClassGroup() {
		final Object testingData[][] = {
			//Positive test
			{
				"parent1", "classGroup1", "classGroupName", null
			},
			//Negative test
			{
				"student1", "classGroup1", "classGroupName", IllegalArgumentException.class
			},
			//Negative test
			{
				"parent1", "classGroup114", "classGroupName", AssertionError.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditClassGroup((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

}
