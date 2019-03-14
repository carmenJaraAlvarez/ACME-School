
package controllers.student;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChatRoomService;
import services.PrivateMessageService;
import services.StudentMessageService;
import services.StudentService;
import controllers.AbstractController;
import domain.Actor;
import domain.ChatRoom;
import domain.PrivateMessage;
import domain.Student;
import domain.StudentMessage;

@Controller
@RequestMapping("/chatRoom/student")
public class ChatRoomStudentController extends AbstractController {

	@Autowired
	private ChatRoomService			chatRoomService;

	@Autowired
	private StudentService			studentService;

	@Autowired
	private StudentMessageService	studentMessageService;

	@Autowired
	private PrivateMessageService	privateMessageService;


	// Panic handler ----------------------------------------------------------

	public ChatRoomStudentController() {
		super();
	}

	//  ---------------------------------------------------------------	

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		final Collection<ChatRoom> chatRooms = this.chatRoomService.findAll();
		try {
			final Student logged = this.studentService.findByPrincipal();
			chatRooms.removeAll(logged.getChatRooms());
		} catch (final Throwable oops) {
		}

		result = super.createModelAndView("chatRoom/list");
		result.addObject("chatRooms", chatRooms);
		result.addObject("showMoreButton", false);
		result.addObject("uri", "chatRoom/student/list.do");
		return result;
	}

	@RequestMapping("/myList")
	public ModelAndView myList() {
		ModelAndView result;
		Collection<ChatRoom> chatRooms = new ArrayList<ChatRoom>();
		try {
			final Student logged = this.studentService.findByPrincipal();
			chatRooms = logged.getChatRooms();
		} catch (final Throwable oops) {
		}

		result = super.createModelAndView("chatRoom/myList");
		result.addObject("chatRooms", chatRooms);
		result.addObject("showMoreButton", true);
		result.addObject("uri", "chatRoom/student/myList.do");
		return result;
	}

	@RequestMapping("/join")
	public ModelAndView join(@RequestParam final Integer chatRoomId) {
		final ModelAndView result;
		final ChatRoom chat = this.chatRoomService.findOne(chatRoomId);

		try {
			final Student logged = this.studentService.findByPrincipal();
			this.studentService.join(logged, chat);
		} catch (final Throwable oops) {
		}

		result = new ModelAndView("redirect:/chatRoom/student/myList.do");
		return result;
	}

	@RequestMapping("/exit")
	public ModelAndView exit(@RequestParam final Integer chatRoomId) {
		final ModelAndView result;
		final ChatRoom chat = this.chatRoomService.findOne(chatRoomId);

		try {
			final Student logged = this.studentService.findByPrincipal();
			this.studentService.exit(logged, chat);
		} catch (final Throwable oops) {
		}

		result = new ModelAndView("redirect:/chatRoom/student/myList.do");
		return result;
	}

	@RequestMapping(value = "/create")
	public ModelAndView create() {
		ModelAndView result;
		final ChatRoom chatRoom = this.chatRoomService.create();
		result = this.createEditModelAndView(chatRoom);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int chatRoomId) {
		ModelAndView result;
		final ChatRoom chatRoom;
		chatRoom = this.chatRoomService.findOneToEdit(chatRoomId);
		result = this.createEditModelAndView(chatRoom);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ChatRoom chatRoom, final BindingResult binding) {
		ModelAndView result;
		final ChatRoom reconstructed = this.chatRoomService.reconstruct(chatRoom, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(chatRoom);
		else
			try {
				this.chatRoomService.saveForStudent(chatRoom);
				result = new ModelAndView("redirect:/chatRoom/student/myList.do");
			} catch (final Throwable oops) {
				System.out.println(oops);
				result = this.createEditModelAndView(reconstructed, "chatRoom.commit.error");
				if (oops.getCause().getCause().toString().contains("Duplicate")) {
					result = this.createEditModelAndView(reconstructed, "chatRoom.duplicate");
				}

			}
		return result;
	}

	private ModelAndView createEditModelAndView(final ChatRoom chatRoom) {
		ModelAndView result;
		result = this.createEditModelAndView(chatRoom, null);
		return result;
	}
	private ModelAndView createEditModelAndView(final ChatRoom chatRoom, final String message) {
		ModelAndView result;
		result = super.createModelAndView("chatRoom/edit");
		result.addObject("chatRoom", chatRoom);
		result.addObject("message", message);

		return result;
	}

	//Métodos para realizar el display

	@RequestMapping(value = "/chat", method = RequestMethod.GET)
	public ModelAndView chat(@RequestParam final int chatRoomId, @RequestParam(required = false, defaultValue = "10") final Integer messagesNumber, @RequestParam(required = false) final String actionChatRoom) {
		ModelAndView result = super.createModelAndView("chatRoom/chat");
		try {
			final StudentMessage newMessage = this.studentMessageService.create();
			final Integer incremento = 4;
			result = this.createDisplayModelAndView(newMessage, chatRoomId, messagesNumber, actionChatRoom, incremento);
		} catch (final Throwable oops) {
			result.addObject("errorOccurred", true);
		}
		return result;
	}

	@RequestMapping(value = "/chat", method = RequestMethod.POST, params = "save")
	public ModelAndView addMessage(@ModelAttribute("newMessage") final StudentMessage newMessage, @RequestParam final int chatRoomId, @RequestParam(required = false, defaultValue = "10") final Integer messagesNumber,
		@RequestParam(required = false) final String actionChatRoom, final BindingResult binding) {
		ModelAndView result;
		final StudentMessage reconstructedMessage = this.studentMessageService.reconstruct(newMessage, binding);
		final Integer incremento = 0;
		ChatRoom chatRoom = this.chatRoomService.findOne(chatRoomId);
		Student logged = this.studentService.findByPrincipal();
		if (chatRoom == null || !logged.getChatRooms().contains(chatRoom)) {
			result = new ModelAndView("redirect:/");
		} else {
			if (binding.hasErrors()) {
				result = this.createDisplayModelAndView(newMessage, chatRoomId, messagesNumber, actionChatRoom, incremento);
			} else {
				try {

					this.studentMessageService.save(reconstructedMessage, chatRoomId);
					result = new ModelAndView("redirect:chat.do?chatRoomId=" + chatRoomId + "&messagesNumber=" + messagesNumber + "#FormularioChat");

				} catch (final Throwable oops) {
					result = this.createDisplayModelAndView(newMessage, chatRoomId, messagesNumber, actionChatRoom, incremento, "chatRoom.message.commit.error");
				}
			}
		}
		return result;
	}
	protected ModelAndView createDisplayModelAndView(final StudentMessage message, final int chatRoomId, final Integer messagesNumber, final String actionChatRoom, final Integer incremento) {
		ModelAndView result;
		result = this.createDisplayModelAndView(message, chatRoomId, messagesNumber, actionChatRoom, incremento, null);
		return result;

	}

	protected ModelAndView createDisplayModelAndView(final StudentMessage newMessage, final int chatRoomId, final Integer messagesNumber, final String actionChatRoom, final Integer incremento, final String message) {
		ModelAndView result = super.createModelAndView("chatRoom/chat");
		final ChatRoom chatRoom = this.chatRoomService.findOne(chatRoomId);
		if (this.chatRoomService.checkStudentInGroup(chatRoom)) {
			final Collection<StudentMessage> studentMessages = this.chatRoomService.findGroupStudentMessages(chatRoomId, messagesNumber);
			final boolean moreMessages = chatRoom.getStudentMessages().size() > messagesNumber;

			if (actionChatRoom != null) {
				if (actionChatRoom.equals("notificated")) {
					result.addObject("notificated", true);
				}
			}
			result.addObject("requestURI", "chatRoom/student/chat.do?chatRoomId=" + chatRoomId);
			result.addObject("chatRoom", chatRoom);
			result.addObject("messages", studentMessages);
			result.addObject("moreMessages", moreMessages);
			result.addObject("newMessage", newMessage);
			result.addObject("messagesNumber", messagesNumber + incremento);
			result.addObject("incremento", incremento);
			result.addObject("studentLogged", this.studentService.findByPrincipal());
			result.addObject("message", message);
		} else
			result = new ModelAndView("redirect:/chatRoom/student/myList.do");
		return result;
	}

	//Fin del display

	@RequestMapping(value = "/help", method = RequestMethod.GET)
	protected ModelAndView help(@RequestParam final int chatRoomId, @RequestParam(required = false, defaultValue = "true") final Boolean english) {
		ModelAndView result;
		try {
			final PrivateMessage privateMessage = this.privateMessageService.create();
			final Student student = this.studentService.findByPrincipal();
			final ChatRoom chatRoom = this.chatRoomService.findOne(chatRoomId);
			if (english) {
				privateMessage.setSubject("They are doing bullying me");
				privateMessage.setBody("They are doing bullying me in the chat: '" + chatRoom.getName() + "'.");
			} else {
				privateMessage.setSubject("Me están haciendo bullying");
				privateMessage.setBody("Me están haciendo bullying en el grupo de chat '" + chatRoom.getName() + "'.");
			}
			privateMessage.setPriority("HIGH");
			Collection<Actor> actores = new ArrayList<Actor>();
			actores.add(student.getParent());

			this.privateMessageService.saveNewMessage(privateMessage, actores);
			result = new ModelAndView("redirect:/chatRoom/student/chat.do?chatRoomId=" + chatRoomId + "&actionChatRoom=notificated");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/chatRoom/student/myList.do");
		}
		return result;
	}
}
