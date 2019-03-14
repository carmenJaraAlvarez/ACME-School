
package useCase;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.MarkService;
import services.SubjectService;
import services.TeacherService;
import utilities.AbstractTest;
import domain.ClassGroup;
import domain.Mark;
import domain.Student;
import domain.Teacher;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MarkUseCaseTest extends AbstractTest {

	//Services used
	@Autowired
	private MarkService		markService;
	@Autowired
	private TeacherService	teacherService;
	@Autowired
	private SubjectService	subjectService;


	//Use case 30: Crear entradas de su tablón de anuncios.
	protected void templateCreateSaveMark(final String username, final double value, final String comment, final String subjectBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Teacher t = this.teacherService.findByPrincipal();
			final ArrayList<ClassGroup> classes = new ArrayList<>(t.getClassGroups());
			final ArrayList<Student> students = new ArrayList<>(classes.get(0).getStudents());
			students.get(0).getMarks();

			final Mark m = this.markService.create(students.get(0).getId());
			m.setValue(value);
			m.setComment(comment);
			m.setSubject(this.subjectService.findOne(this.getEntityId(subjectBean)));

			this.markService.reconstruct(m, null);
			this.markService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverCreateSaveMark() {
		final Object testingData[][] = {
			//Positive test
			{
				"teacher1", 5.0, "", "subject1", null
			},
			//Negative test
			{
				"parent1", 10.0, "", "subject1", IllegalArgumentException.class
			},
			//Negative test
			{
				"teacher1", 5.0, "", "", AssertionError.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSaveMark((String) testingData[i][0], (double) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	//Use case 31: Editar entradas de su tablón de anuncios.
	protected void templateEditMark(final String username, final String markBean, final double value, final String comment, final String subjectBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Teacher t = this.teacherService.findByPrincipal();
			final ArrayList<ClassGroup> classes = new ArrayList<>(t.getClassGroups());
			final ArrayList<Student> students = new ArrayList<>(classes.get(0).getStudents());
			students.get(0).getMarks();

			final Mark m = this.markService.create(students.get(0).getId());
			m.setValue(value);
			m.setComment(comment);
			m.setSubject(this.subjectService.findOne(this.getEntityId(subjectBean)));

			this.markService.reconstruct(m, null);
			this.markService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverEditMark() {
		final Object testingData[][] = {
			//Positive test
			{
				"teacher1", "mark1", 5.0, "", "subject1", null
			},
			//Negative test
			{
				"parent1", "mark1", 10.0, "", "subject1", IllegalArgumentException.class
			},
			//Negative test
			{
				"teacher1", "mark1", 5.0, "", "", AssertionError.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditMark((String) testingData[i][0], (String) testingData[i][1], (double) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}
}
