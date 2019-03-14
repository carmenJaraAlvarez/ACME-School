
package useCase;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ClassGroupService;
import services.LevelService;
import services.MessageService;
import services.ParentService;
import services.ParentsGroupService;
import services.SchoolService;
import utilities.AbstractTest;
import domain.ClassGroup;
import domain.Message;
import domain.Parent;
import domain.ParentsGroup;
import domain.School;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ParentsGroupUseCaseTest extends AbstractTest {

	//Services used
	@Autowired
	private ParentsGroupService	parentsGroupService;
	@Autowired
	private ParentService		parentService;
	@Autowired
	private ClassGroupService	classGroupService;
	@Autowired
	private SchoolService		schoolService;
	@Autowired
	private LevelService		levelService;
	@Autowired
	private MessageService		messageService;


	//Use case 11: Crear y editar un grupo de tutores y relacionarlo con una clase de un colegio.
	protected void templateCreateEditSaveParentsGroup(final String username, final String schoolBean, final String levelBean, final String classGroupBean, final String nameParentsGroup, final String description, final String image, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			this.parentsGroupService.findAll();
			final School sc = this.schoolService.findOne(this.getEntityId(schoolBean));
			Assert.isTrue(sc.getLevels().contains(this.levelService.findOne(this.getEntityId(levelBean))));
			final ClassGroup cg = this.classGroupService.findOne(this.getEntityId(classGroupBean));
			Assert.isTrue(cg.getLevel().getClassGroups().contains(this.classGroupService.findOne(this.getEntityId(classGroupBean))));

			final ParentsGroup pg = this.parentsGroupService.create();
			pg.setClassGroup(cg);
			pg.setName(nameParentsGroup);
			pg.setDescription(description);
			pg.setImage(image);
			this.parentsGroupService.save(pg);
			this.parentsGroupService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverCreateEditSaveParentsGroup() {
		final Object testingData[][] = {
			//Positive test 0
			{
				"parent1", "school1", "level1", "classGroup1", "test name", "test description", "http://testimage.com", null
			},
			//Negative test 1: Unauthorized actor
			{
				"teacher1", "school1", "level1", "classGroup1", "test name 2", "test description", "http://testimage.com", IllegalArgumentException.class
			},
			//Negative test 2: Wrong school
			{
				"parent1", "school2", "level1", "classGroup1", "test name", "test description", "correo", IllegalArgumentException.class
			},
			//Negative test 3: Level doesn't exists
			{
				"parent1", "school1", "level24", "classGroup1", "test name", "test description", "correo", AssertionError.class
			},
			//Negative test 4: classGroup doesn't exists
			{
				"parent1", "school1", "level1", "classGroup35", "test name", "test description", "correo", AssertionError.class
			},
			//Negative test 5: School doesn't exists
			{
				"parent1", "school19", "level1", "classGroup35", "test name", "test description", "correo", AssertionError.class
			},
			//Negative test 6: blank name
			{
				"parent1", "school1", "level1", "classGroup1", "", "test description", "http://testimage.com", ConstraintViolationException.class
			},
			//Negative test 7: blank description
			{
				"parent1", "school1", "level1", "classGroup1", "test name", "", "http://testimage.com", ConstraintViolationException.class
			},
			//Negative test 8: blank image
			{
				"parent1", "school1", "level1", "classGroup1", "test name", "test description", "", ConstraintViolationException.class
			},
			//Negative test 9: Wrong image format
			{
				"parent1", "school1", "level1", "classGroup1", "test name", "test description", "correo", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateEditSaveParentsGroup((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}

	//Use case 15: Escribir mensajes en el chat de un grupo al que pertenezca.
	protected void templateWriteMessageInParentGroup(final String username, final String parentGroup, final String message, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final ParentsGroup p = this.parentsGroupService.findOne(this.getEntityId(parentGroup));
			this.parentsGroupService.checkParentInGroup(p);

			final Message mes = this.messageService.create();
			mes.setParent(this.parentService.findByPrincipal());
			mes.setBody(message);
			this.messageService.save(mes, p.getId());

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverWriteMessageInParentGroup() {
		final Object testingData[][] = {
			//Positive
			{
				"parent1", "parentsGroup1", "Hola que tal", null
			},
			//Negative
			{
				"student1", "parentsGroup1", "Hola que tal", IllegalArgumentException.class
			},
			//Negative
			{
				"parent1", "parentsGroup19", "Hola que tal", AssertionError.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateWriteMessageInParentGroup((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	//Use case 12: Gestionar administradores de los grupos creado por él.
	protected void templateManageAdminsGroup(final String username, final String newAdmin, final String oldAdmin, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Parent p = this.parentService.findByPrincipal();
			p.getParentsGroupsCreated();
			final ParentsGroup pg = this.parentsGroupService.findOne(this.getEntityId("parentsGroup1"));
			final Parent newAdmin1 = this.parentService.findOne(this.getEntityId(newAdmin));
			final Parent oldAdmin1 = this.parentService.findOne(this.getEntityId(oldAdmin));
			this.parentsGroupService.addAdmin(newAdmin1, pg);
			this.parentsGroupService.removeAdminOfGroup(oldAdmin1, pg);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverManageAdminsGroup() {
		final Object testingData[][] = {
			//Positive
			{
				"parent1", "parent3", "parent2", null
			},
			//Negative
			{
				"student1", "parent3", "parent2", IllegalArgumentException.class
			},
			//Negative
			{
				null, "parent3", "parent2", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateManageAdminsGroup((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	//Use case 13: Dar de baja a miembros de grupos que haya creado él.
	protected void templateRejectMember(final String username, final String rejectedParent, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Parent p = this.parentService.findByPrincipal();
			p.getParentsGroupsCreated();
			final ParentsGroup pg = this.parentsGroupService.findOne(this.getEntityId("parentsGroup1"));
			final Parent rejected = this.parentService.findOne(this.getEntityId(rejectedParent));

			this.parentsGroupService.removeMemberOfGroup(rejected, pg);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverRejectMember() {
		final Object testingData[][] = {
			//Positive
			{
				"parent1", "parent2", null
			},
			//Negative
			{
				"student1", "parent3", IllegalArgumentException.class
			},
			//Negative
			{
				null, "parent2", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateRejectMember((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	//Use case 14: Unirse a un grupo.
	protected void templateJoinGroup(final String username, final String code, final String parentGroupBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Parent p = this.parentService.findByPrincipal();
			this.parentsGroupService.findAll();
			final ParentsGroup pg = this.parentsGroupService.findOne(this.getEntityId(parentGroupBean));
			final boolean res = pg.getCode().equals(code);
			Assert.isTrue(res);
			this.parentsGroupService.addMember(p, pg);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverJoinGroup() {
		final Object testingData[][] = {
			//Positive
			{
				"parent2", "33333CC", "parentsGroup3", null
			},
			//Negative
			{
				"student1", "12345CD", "parentsGroup2", IllegalArgumentException.class
			},
			//Negative
			{
				null, "11111BB", "parentsGroup1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateJoinGroup((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
}
