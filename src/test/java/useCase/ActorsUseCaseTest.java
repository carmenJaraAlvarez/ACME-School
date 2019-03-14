
package useCase;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.AgentService;
import services.ClassGroupService;
import services.ParentService;
import services.SchoolService;
import services.StudentService;
import services.TeacherService;
import utilities.AbstractTest;
import domain.Agent;
import domain.Parent;
import domain.Student;
import domain.Teacher;
import forms.CreateActorForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ActorsUseCaseTest extends AbstractTest {

	@Autowired
	private AgentService		agentService;
	@Autowired
	private ParentService		parentService;
	@Autowired
	private TeacherService		teacherService;
	@Autowired
	private StudentService		studentService;
	@Autowired
	private SchoolService		schoolService;
	@Autowired
	private ClassGroupService	classGroupService;


	//Use case 01: Registrarse en el sistema como tutor
	protected void templateSignInParent(final String username, final String password, final String password2, final String name, final String surname, final String email, final boolean accept, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(null);
			final CreateActorForm reg = new CreateActorForm();
			reg.setUsername(username);
			reg.setPassword(password);
			reg.setPassword2(password2);
			reg.setEmail(email);
			reg.setName(name);
			reg.setSurname(surname);
			reg.setValida(accept);
			//simulación del binding @unique de form
			Assert.isTrue(username != "");

			final Parent p = this.parentService.reconstruct(reg, null);
			this.parentService.flush();
			this.parentService.save(p);
			this.parentService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverSignInParent() {
		final Object testingData[][] = {
			//Positive
			{
				"parent200", "parent200", "parent200", "parent200", "parent200 surname", "parent200@gmail.com", true, null
			},

			//Negative
			{
				"", "parent202", "parent202", "parent202", "surname", "111@h.com", true, IllegalArgumentException.class

			},
			//Negative
			{
				"", "", "", "", "", "", true, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSignInParent((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Boolean) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}

	//Use Case 02: Registrarse en el sistema como agente
	protected void templateSignIn(final String username, final String password, final String password2, final String name, final String surname, final String email, final String taxCode, final boolean accept, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(null);
			final CreateActorForm reg = new CreateActorForm();
			reg.setUsername(username);
			reg.setPassword(password);
			reg.setPassword2(password2);
			reg.setEmail(email);
			reg.setName(name);
			reg.setSurname(surname);
			reg.setTaxCode(taxCode);
			reg.setValida(accept);
			//simulación del binding @unique de form
			Assert.isTrue(username != "");

			final Agent agent = this.agentService.reconstruct(reg, null);
			this.agentService.flush();
			this.agentService.save(agent);
			this.agentService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverSignInAgent() {
		final Object testingData[][] = {
			//Positive
			{
				"agent200", "agent200", "agent200", "agent200", "agent200 surname", "agent200@gmail.com", "132456789", true, null
			},

			//Negative
			{
				"", "agent202", "agent202", "agent202", "surname", "111@h.com", "132456789", true, IllegalArgumentException.class

			},
			//Negative
			{
				"", "", "", "", "", "", "", true, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSignIn((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Boolean) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	//Use case 03: Registrarse en el sistema como teacher
	protected void templateSignInTeacher(final String username, final String password, final String password2, final String name, final String surname, final String email, final boolean accept, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(null);
			final CreateActorForm reg = new CreateActorForm();
			reg.setUsername(username);
			reg.setPassword(password);
			reg.setPassword2(password2);
			reg.setEmail(email);
			reg.setName(name);
			reg.setSurname(surname);
			reg.setValida(accept);
			//simulación del binding @unique de form
			Assert.isTrue(username != "");

			final Teacher t = this.teacherService.reconstruct(reg, null);
			t.setSchool(this.schoolService.findOne(this.getEntityId("school1")));
			this.teacherService.flush();
			this.teacherService.save(t);
			this.teacherService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverSignInTeacher() {
		final Object testingData[][] = {
			//Positive
			{
				"teacher200", "teacher200", "teacher200", "teacher200", "teacher200 surname", "teacher200@gmail.com", true, null
			},

			//Negative
			{
				"", "teacher202", "teacher202", "teacher202", "surname", "111@h.com", true, IllegalArgumentException.class

			},
			//Negative
			{
				"", "", "", "", "", "", true, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSignInTeacher((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Boolean) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}

	//Use case 03: Registrarse en el sistema como teacher
	protected void templateSignInStudent(final String username, final String password, final String password2, final String name, final String surname, final String email, final boolean accept, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate("parent1");
			final CreateActorForm reg = new CreateActorForm();
			reg.setUsername(username);
			reg.setPassword(password);
			reg.setPassword2(password2);
			reg.setEmail(email);
			reg.setName(name);
			reg.setSurname(surname);
			reg.setValida(accept);
			//simulación del binding @unique de form
			Assert.isTrue(username != "");

			final Student s = this.studentService.reconstruct(reg, null);
			s.setClassGroup(this.classGroupService.findOne(this.getEntityId("classGroup1")));

			this.studentService.flush();
			this.studentService.saveEdit(s);
			this.studentService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	@Test
	public void driverSignInStudent() {
		final Object testingData[][] = {
			//Positive
			{
				"student200", "student200", "student200", "student200", "student200 surname", "student200@gmail.com", true, null
			},

			//Negative
			{
				"", "student202", "student202", "student202", "surname", "111@h.com", true, IllegalArgumentException.class

			},
			//Negative
			{
				"", "", "", "", "", "", true, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSignInStudent((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Boolean) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}
}
