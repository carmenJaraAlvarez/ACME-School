
package useCase;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ChatRoomService;
import services.StudentMessageService;
import services.StudentService;
import services.SubjectService;
import utilities.AbstractTest;
import domain.Student;
import domain.StudentMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class StudentMessageUseCaseTest extends AbstractTest {

	//Services used
	@Autowired
	private StudentMessageService	studentMessageService;
	@Autowired
	private StudentService			studentService;
	@Autowired
	private SubjectService			subjectService;
	@Autowired
	private ChatRoomService			chatRoomService;


	//Use case 28: Escribir mensajes en el chat.
	protected void templateCreateSaveStudentMessage(final String username, final String message, final String chatRoomBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Student p = this.studentService.findByPrincipal();
			final StudentMessage st = this.studentMessageService.create();
			st.setBody(message);

			final StudentMessage recon = this.studentMessageService.reconstruct(st, null);
			this.studentMessageService.save(recon, this.chatRoomService.findOne(this.getEntityId(chatRoomBean)).getId());
			this.studentMessageService.flush();

			super.unauthenticate();

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
				"student1", "Hola grupo", "chatRoom1", null
			},
			//Negative test
			{
				"teacher1", "Hola grupo", "chatRoom2", IllegalArgumentException.class
			},
			//Negative test
			{
				"student1", "", "chatRoom1", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSaveStudentMessage((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
}
