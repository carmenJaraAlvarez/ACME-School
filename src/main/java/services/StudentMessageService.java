
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.StudentMessageRepository;
import domain.Actor;
import domain.ChatRoom;
import domain.PrivateMessage;
import domain.Student;
import domain.StudentMessage;

@Service
@Transactional
public class StudentMessageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private StudentMessageRepository	studentMessageRepository;
	@Autowired
	private StudentService				studentService;
	@Autowired
	private ChatRoomService				chatRoomService;
	@Autowired
	private GlobalService				globalService;
	@Autowired
	private PrivateMessageService		privateMessageService;

	@Autowired
	private Validator					validator;


	// Constructors -----------------------------------------------------------

	public StudentMessageService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public StudentMessage create() {
		final StudentMessage res = new StudentMessage();
		final Student logged = this.studentService.findByPrincipal();
		res.setStudent(logged);
		res.setMoment(new Date(System.currentTimeMillis() - 1000));
		return res;
	}

	public StudentMessage save(final StudentMessage studentMsg, final Integer chatRoomId) {
		final StudentMessage saved = this.studentMessageRepository.save(studentMsg);
		final ChatRoom chatRoom = this.chatRoomService.findOne(chatRoomId);
		chatRoom.getStudentMessages().add(saved);
		if (isTabooMessage(studentMsg.getBody())) {
			chatRoom.setCounter(chatRoom.getCounter() + 1);
			Integer contador = chatRoom.getCounter();
			Integer limite = this.globalService.getGlobal().getWordsLimit();
			if (limite > 0) {
				if (contador % limite == 0) {
					this.enviarNotificacion(chatRoom);
				}
			}
		}
		this.chatRoomService.save(chatRoom);
		return saved;
	}
	// Other business methods -------------------------------------------------

	//Método auxiliar para comprobar si el mensaje se considera inapropiado
	private boolean isTabooMessage(final String message) {
		List<String> spamwords = new ArrayList<String>(this.globalService.getGlobal().getDangerousWords());
		boolean isTaboo = false;

		for (final String s : spamwords) {
			if (message.contains(s)) {
				isTaboo = true;
				break;
			}
		}
		return isTaboo;
	}

	//Método auxiliar para guardar mensaje
	private void enviarNotificacion(final ChatRoom chatRoom) {
		PrivateMessage newNotification = this.privateMessageService.create();

		//Añadimos asunto, body y priority del formulario:
		newNotification.setSubject("Acme-School | Sistema de detección de mal comportamiento");
		newNotification.setBody("Este aviso se ha generado automáticamente debido a que se ha detectado una cantidad elevada de palabras malsonantes en el chat '" + chatRoom.getName() + "' de su hijo.<br/>"
			+ "Le rogamos que hable con él, con el fin de mantener un buen ambiente y hacer uso de un buen lenguaje en nuestros chats de estudiantes, los cuales son accesibles para todas las edades.<br/>" + "Gracias por su tiempo.<br/><br/>"
			+ "<em>Equipo directivo de Acme-School</em>");
		newNotification.setPriority("HIGH");

		Collection<Actor> parents = new ArrayList<Actor>();
		for (Student student : chatRoom.getStudents()) {
			parents.add(student.getParent());
		}

		this.privateMessageService.saveNotificationToParents(newNotification, parents);
	}

	public StudentMessage reconstruct(final StudentMessage newStudentMessage, final BindingResult binding) {
		final StudentMessage result = this.create();
		result.setBody(newStudentMessage.getBody());

		if (binding != null)
			this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.studentMessageRepository.flush();
	}
}
