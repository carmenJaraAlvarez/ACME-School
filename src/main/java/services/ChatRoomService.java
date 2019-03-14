
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ChatRoomRepository;
import domain.ChatRoom;
import domain.Student;
import domain.StudentMessage;

@Service
@Transactional
public class ChatRoomService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ChatRoomRepository	chatRoomRepository;

	@Autowired
	private StudentService		studentService;

	@Autowired
	private Validator			validator;


	// Constructors -----------------------------------------------------------

	public ChatRoomService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public ChatRoom create() {
		final ChatRoom res = new ChatRoom();
		res.setStudents(new ArrayList<Student>());
		res.getStudents().add(this.studentService.findByPrincipal());
		res.setStudentMessages(new ArrayList<StudentMessage>());
		return res;
	}
	public ChatRoom save(final ChatRoom chatRoom) {
		this.checkConcurrency(chatRoom);
		return this.chatRoomRepository.save(chatRoom);
	}

	public ChatRoom saveForStudent(final ChatRoom chatRoom) {
		this.checkPrincipalStudent();
		return this.save(chatRoom);
	}

	public ChatRoom findOne(final Integer chatRoomId) {
		return this.chatRoomRepository.findOne(chatRoomId);
	}

	public ChatRoom findOneToEdit(final Integer chatRoomId) {
		this.checkPrincipalStudent();
		return this.findOne(chatRoomId);
	}

	public Collection<ChatRoom> findAll() {
		return this.chatRoomRepository.findAll();
	}

	// Other business methods -------------------------------------------------

	public ChatRoom reconstruct(final ChatRoom chatRoom, final BindingResult binding) {
		ChatRoom res;
		if (chatRoom.getId() == 0)
			res = chatRoom;
		else {
			res = this.findOne(chatRoom.getId());
			res.setName(chatRoom.getName());
		}

		if (binding != null)
			this.validator.validate(res, binding);

		return res;
	}

	private void checkConcurrency(final ChatRoom chatRoom) {
		if (chatRoom.getId() != 0) {
			final ChatRoom c = this.findOne(chatRoom.getId());
			Assert.isTrue(chatRoom.getVersion() == c.getVersion());
		}
	}

	private void checkPrincipalStudent() {
		try {
			final Student logged = this.studentService.findByPrincipal();
			Assert.notNull(logged);
		} catch (final Exception e) {
		}
	}

	public boolean checkStudentInGroup(final ChatRoom chatRoom) {
		final Student student = this.studentService.findByPrincipal();
		final boolean result = chatRoom.getStudents().contains(student);
		return result;
	}

	public Collection<StudentMessage> findGroupStudentMessages(final int chatRoomId, final Integer messagesNumber) {
		Collection<StudentMessage> result;
		final ArrayList<StudentMessage> messages = this.chatRoomRepository.findGroupStudentMessages(chatRoomId);
		if (messagesNumber < messages.size()) {
			Collections.reverse(messages);
			final ArrayList<StudentMessage> selected = new ArrayList<StudentMessage>();
			Integer index = 0;
			for (final StudentMessage m : messages)
				if (messagesNumber > index) {
					selected.add(m);
					index++;
				} else
					break;
			Collections.reverse(selected);
			result = selected;
		} else
			result = messages;
		return result;
	}

	public void flush() {
		this.chatRoomRepository.flush();
	}
}
