
package useCase;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ChatRoomService;
import services.StudentService;
import services.SubjectService;
import utilities.AbstractTest;
import domain.ChatRoom;
import domain.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ChatRoomUseCaseTest extends AbstractTest {

	//Services used
	@Autowired
	private ChatRoomService	chatRoomService;
	@Autowired
	private StudentService	studentService;
	@Autowired
	private SubjectService	subjectService;


	//Use case 27: Crear grupos de alumnos.
	protected void templateCreateSaveChatRoom(final String username, final String name, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Student p = this.studentService.findByPrincipal();
			p.getChatRooms();
			final ChatRoom cr = this.chatRoomService.create();
			cr.setName(name);

			final ChatRoom recon = this.chatRoomService.reconstruct(cr, null);
			this.chatRoomService.saveForStudent(recon);
			this.chatRoomService.flush();

			this.unauthenticate();
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
				"student1", "studentsGroup1", null
			},
			//Negative test
			{
				"teacher1", "studentsGroup1", IllegalArgumentException.class
			},
			//Negative test
			{
				"student1", "", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSaveChatRoom((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
}
